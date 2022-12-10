/**
 *
 */
package io.sinistral.proteus.server.handlers;

import io.undertow.Handlers;
import io.undertow.predicate.Predicate;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ResponseCodeHandler;
import io.undertow.server.handlers.cache.LRUCache;
import io.undertow.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jbauer
 *
 */
public class ProteusHandler implements HttpHandler
{
    private static final Logger log = LoggerFactory.getLogger(ProteusHandler.class.getName());

    private final PathMatcher<HttpHandler> pathMatcher = new PathMatcher<>();
    private final Map<HttpString, PathTemplateMatcher<RoutingMatch>> matches = new CopyOnWriteMap<>();

    // Matcher used to find if this instance contains matches for any http method for a path.
    // This matcher is used to report if this instance can match a path for one of the http methods.
    private final PathTemplateMatcher<RoutingMatch> allMethodsMatcher = new PathTemplateMatcher<>();

    // Handler called when no match was found and invalid method handler can't be invoked.
    private volatile HttpHandler fallbackHandler = ResponseCodeHandler.HANDLE_404;

    // Handler called when this instance can not match the http method but can match another http method.
    // For example: For an exchange the POST method is not matched by this instance but at least one http method is
    // matched for the same exchange.
    // If this handler is null the fallbackHandler will be used.
    private volatile HttpHandler invalidMethodHandler = ResponseCodeHandler.HANDLE_405;
    private final LRUCache<String, PathMatcher.PathMatch<HttpHandler>> cache;

    public ProteusHandler()
    {
        this(0);
    }

    // If this is true then path matches will be added to the query parameters for easy access by later handlers.
    public ProteusHandler(final HttpHandler defaultHandler)
    {
        this(0);

        pathMatcher.addPrefixPath("/", defaultHandler);
    }

    public ProteusHandler(int cacheSize)
    {
        if (cacheSize > 0) {
            cache = new LRUCache<>(cacheSize, -1, true);
        } else {
            cache = null;
        }
    }

    public ProteusHandler(final HttpHandler defaultHandler, int cacheSize)
    {
        this(cacheSize);

        pathMatcher.addPrefixPath("/", defaultHandler);
    }

    public synchronized ProteusHandler add(HttpString method, String template, HttpHandler handler)
    {
        PathTemplateMatcher<RoutingMatch> matcher = matches.get(method);

        if (matcher == null) {
            matches.put(method, matcher = new PathTemplateMatcher<>());
        }

        RoutingMatch res = matcher.get(template);

        if (res == null) {
            matcher.add(template, res = new RoutingMatch());
        }

        if (allMethodsMatcher.get(template) == null) {
            allMethodsMatcher.add(template, res);
        }

        res.defaultHandler = handler;

        return this;
    }

    public synchronized ProteusHandler add(final String method, final String template, HttpHandler handler)
    {
        return add(new HttpString(method), template, handler);
    }

    public synchronized ProteusHandler add(HttpString method, String template, Predicate predicate, HttpHandler handler)
    {
        PathTemplateMatcher<RoutingMatch> matcher = matches.get(method);

        if (matcher == null) {
            matches.put(method, matcher = new PathTemplateMatcher<>());
        }

        RoutingMatch res = matcher.get(template);

        if (res == null) {
            matcher.add(template, res = new RoutingMatch());
        }

        if (allMethodsMatcher.get(template) == null) {
            allMethodsMatcher.add(template, res);
        }

        res.predicatedHandlers.add(new HandlerHolder(predicate, handler));

        return this;
    }

    public synchronized ProteusHandler add(final String method, final String template, Predicate predicate, HttpHandler handler)
    {
        return add(new HttpString(method), template, predicate, handler);
    }

    public synchronized ProteusHandler addAll(ProteusHandler routingHandler)
    {
        for (Entry<HttpString, PathTemplateMatcher<RoutingMatch>> entry : routingHandler.getMatches().entrySet()) {
            HttpString method = entry.getKey();
            PathTemplateMatcher<RoutingMatch> matcher = matches.get(method);

            if (matcher == null) {
                matches.put(method, matcher = new PathTemplateMatcher<>());
            }

            matcher.addAll(entry.getValue());

            // If we use allMethodsMatcher.addAll() we can have duplicate
            // PathTemplates which we want to ignore here so it does not crash.
            for (PathTemplate template : entry.getValue().getPathTemplates()) {
                if (allMethodsMatcher.get(template.getTemplateString()) == null) {
                    allMethodsMatcher.add(template, new RoutingMatch());
                }
            }
        }

        return this;
    }

    /**
     * If the request path is exactly equal to the given path, run the handler.
     * <p>
     * Exact paths are prioritized higher than prefix paths.
     *
     * @param path If the request path is exactly this, run handler.
     * @param handler Handler run upon exact path match.
     * @return The resulting PathHandler after this path has been added to it.
     */
    public synchronized ProteusHandler addExactPath(final String path, final HttpHandler handler)
    {
        Handlers.handlerNotNull(handler);
        pathMatcher.addExactPath(path, handler);

        return this;
    }

    /**
     * Adds a path prefix and a handler for that path. If the path does not start
     * with a / then one will be prepended.
     * <p>
     * The match is done on a prefix bases, so registering /foo will also match /foo/bar.
     * Though exact path matches are taken into account before prefix path matches. So
     * if an exact path match exists it's  handler will be triggered.
     * <p>
     * If / is specified as the path then it will replace the default handler.
     *
     * @param path    If the request contains this prefix, run handler.
     * @param handler The handler which is activated upon match.
     * @return The resulting PathHandler after this path has been added to it.
     */
    public synchronized ProteusHandler addPrefixPath(final String path, final HttpHandler handler)
    {
        Handlers.handlerNotNull(handler);
        pathMatcher.addPrefixPath(path, handler);

        return this;
    }

    public synchronized ProteusHandler clearPaths()
    {
        pathMatcher.clearPaths();

        return this;
    }

    public synchronized ProteusHandler delete(final String template, HttpHandler handler)
    {
        return add(Methods.DELETE, template, handler);
    }

    public synchronized ProteusHandler delete(final String template, Predicate predicate, HttpHandler handler)
    {
        return add(Methods.DELETE, template, predicate, handler);
    }

    private void handleNoMatch(final HttpServerExchange exchange) throws Exception
    {
        // if invalidMethodHandler is null we fail fast without matching with allMethodsMatcher
        if ((invalidMethodHandler != null) && (allMethodsMatcher.match(exchange.getRelativePath()) != null)) {
            invalidMethodHandler.handleRequest(exchange);

            return;
        }

        fallbackHandler.handleRequest(exchange);
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception
    {
        PathMatcher.PathMatch<HttpHandler> match = null;
        boolean hit = false;

        if (cache != null) {
            match = cache.get(exchange.getRelativePath());
            hit = true;
        }

        if (match == null) {
            match = pathMatcher.match(exchange.getRelativePath());
        }

        if (match.getValue() == null) {
            handleRouterRequest(exchange);

            return;
        }

        if (hit) {
            cache.add(exchange.getRelativePath(), match);
        }

        exchange.setRelativePath(match.getRemaining());

        if (exchange.getResolvedPath().isEmpty()) {
            // first path handler, we can just use the matched part
            exchange.setResolvedPath(match.getMatched());
        } else {
            // already something in the resolved path

            String sb = exchange.getResolvedPath() +
                    match.getMatched();
            exchange.setResolvedPath(sb);
        }

        match.getValue().handleRequest(exchange);
    }

    public void handleRouterRequest(HttpServerExchange exchange) throws Exception
    {
        PathTemplateMatcher<RoutingMatch> matcher = matches.get(exchange.getRequestMethod());

        if (matcher == null) {
            handleNoMatch(exchange);

            return;
        }

        PathTemplateMatcher.PathMatchResult<RoutingMatch> match = matcher.match(exchange.getRelativePath());

        if (match == null) {
            handleNoMatch(exchange);

            return;
        }

        exchange.putAttachment(PathTemplateMatch.ATTACHMENT_KEY, match);

        for (Entry<String, String> entry : match.getParameters().entrySet()) {
            exchange.addQueryParam(entry.getKey(), entry.getValue());
        }

        for (HandlerHolder handler : match.getValue().predicatedHandlers) {
            if (handler.predicate.resolve(exchange)) {
                handler.handler.handleRequest(exchange);

                return;
            }
        }

        if (match.getValue().defaultHandler != null) {
            match.getValue().defaultHandler.handleRequest(exchange);
        } else {
            fallbackHandler.handleRequest(exchange);
        }
    }

    public synchronized ProteusHandler post(final String template, HttpHandler handler)
    {
        return add(Methods.POST, template, handler);
    }

    public synchronized ProteusHandler post(final String template, Predicate predicate, HttpHandler handler)
    {
        return add(Methods.POST, template, predicate, handler);
    }

    public synchronized ProteusHandler put(final String template, HttpHandler handler)
    {
        return add(Methods.PUT, template, handler);
    }

    public synchronized ProteusHandler put(final String template, Predicate predicate, HttpHandler handler)
    {
        return add(Methods.PUT, template, predicate, handler);
    }

    /**
     *
     * Removes the specified route from the handler
     *
     * @param path the path tempate to remove
     * @return this handler
     */
    public ProteusHandler remove(String path)
    {
        allMethodsMatcher.remove(path);

        return this;
    }

    /**
     *
     * Removes the specified route from the handler
     *
     * @param method The method to remove
     * @param path the path tempate to remove
     * @return this handler
     */
    public ProteusHandler remove(HttpString method, String path)
    {
        PathTemplateMatcher<RoutingMatch> handler = matches.get(method);

        if (handler != null) {
            handler.remove(path);
        }

        return this;
    }

    public synchronized ProteusHandler removeExactPath(final String path)
    {
        pathMatcher.removeExactPath(path);

        return this;
    }

    public synchronized ProteusHandler removePrefixPath(final String path)
    {
        pathMatcher.removePrefixPath(path);

        return this;
    }

    /**
     * @return Handler called when no match was found and invalid method handler can't be invoked.
     */
    public HttpHandler getFallbackHandler()
    {
        return fallbackHandler;
    }

    /**
     * @param fallbackHandler Handler that will be called when no match was found and invalid method handler can't be
     * invoked.
     * @return This instance.
     */
    public ProteusHandler setFallbackHandler(HttpHandler fallbackHandler)
    {
        this.fallbackHandler = fallbackHandler;

        return this;
    }

    public synchronized ProteusHandler get(final String template, HttpHandler handler)
    {
        return add(Methods.GET, template, handler);
    }

    public synchronized ProteusHandler get(final String template, Predicate predicate, HttpHandler handler)
    {
        return add(Methods.GET, template, predicate, handler);
    }

    /**
     * @return Handler called when this instance can not match the http method but can match another http method.
     */
    public HttpHandler getInvalidMethodHandler()
    {
        return invalidMethodHandler;
    }

    /**
     * Sets the handler called when this instance can not match the http method but can match another http method.
     * For example: For an exchange the POST method is not matched by this instance but at least one http method matched
     * for the exchange.
     * If this handler is null the fallbackHandler will be used.
     *
     * @param invalidMethodHandler Handler that will be called when this instance can not match the http method but can
     * match another http method.
     * @return This instance.
     */
    public ProteusHandler setInvalidMethodHandler(HttpHandler invalidMethodHandler)
    {
        this.invalidMethodHandler = invalidMethodHandler;

        return this;
    }

    Map<HttpString, PathTemplateMatcher<RoutingMatch>> getMatches()
    {
        return matches;
    }

    private static class HandlerHolder
    {
        final Predicate predicate;
        final HttpHandler handler;

        private HandlerHolder(Predicate predicate, HttpHandler handler)
        {
            this.predicate = predicate;
            this.handler = handler;
        }
    }


    private static class RoutingMatch
    {
        final List<HandlerHolder> predicatedHandlers = new CopyOnWriteArrayList<>();
        volatile HttpHandler defaultHandler;
    }
}

