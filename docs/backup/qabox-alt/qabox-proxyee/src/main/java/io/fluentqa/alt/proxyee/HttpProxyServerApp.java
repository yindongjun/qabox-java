package io.fluentqa.alt.proxyee;

import io.fluentqa.alt.proxyee.server.HttpProxyServer;

/**
 * @Author
 * @Description
 * @Date 2019/9/23 17:30
 */
public class HttpProxyServerApp {
    public static void main(String[] args) {
        System.out.println("start proxy server");
        int port = 9999;
        if (args.length > 0) {
            port = Integer.valueOf(args[0]);
        }
        new HttpProxyServer().start(port);
    }
}
