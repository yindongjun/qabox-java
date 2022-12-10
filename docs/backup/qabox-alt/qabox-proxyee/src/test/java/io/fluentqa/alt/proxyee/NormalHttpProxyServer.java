package io.fluentqa.alt.proxyee;

import io.fluentqa.alt.proxyee.intercept.HttpProxyInterceptInitializer;
import io.fluentqa.alt.proxyee.intercept.HttpProxyInterceptPipeline;
import io.fluentqa.alt.proxyee.intercept.common.FullRequestIntercept;
import io.fluentqa.alt.proxyee.server.HttpProxyServer;
import io.fluentqa.alt.proxyee.server.HttpProxyServerConfig;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;

import java.nio.charset.Charset;

public class NormalHttpProxyServer {

    public static void main(String[] args) throws Exception {
        //new HttpProxyServer().start(9998);

        HttpProxyServerConfig config = new HttpProxyServerConfig();
        config.setBossGroupThreads(1);
        config.setWorkerGroupThreads(1);
        config.setProxyGroupThreads(1);
        new HttpProxyServer()
                .serverConfig(config)
                .proxyInterceptInitializer(new HttpProxyInterceptInitializer() {
                    @Override
                    public void init(HttpProxyInterceptPipeline pipeline) {
                        pipeline.addLast(new FullRequestIntercept() {

                            @Override
                            public boolean match(HttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
                                return true;
                            }

                            @Override
                            public void handleRequest(FullHttpRequest httpRequest, HttpProxyInterceptPipeline pipeline) {
                                ByteBuf content = httpRequest.content();
                                //打印请求信息
                                System.out.println(httpRequest.toString());
                                System.out.println(content.toString(Charset.defaultCharset()));
                            }

                        });
                    }
                })
                .start(9999);
    }
}
