package io.fluentqa.alt.proxyee.server.auth;

import io.fluentqa.alt.proxyee.server.auth.model.HttpToken;
import io.fluentqa.alt.proxyee.server.context.HttpContext;
import io.netty.channel.Channel;

/**
 * @Author
 * @Description
 * @Date 2021/8/3 11:37
 */
public class HttpAuthContext {

    private static final String AUTH_KEY = "http_auth";

    public static HttpToken getToken(Channel clientChanel) {
        return HttpContext.get(clientChanel, AUTH_KEY);
    }

    public static void setToken(Channel clientChanel, HttpToken httpToken) {
        HttpContext.set(clientChanel, AUTH_KEY, httpToken);
    }
}
