package io.fluentqa.alt.proxyee;

import io.fluentqa.alt.proxyee.server.HttpProxyServer;
import io.fluentqa.alt.proxyee.server.HttpProxyServerConfig;

public class HandelSslHttpProxyServer {

    public static void main(String[] args) throws Exception {
        HttpProxyServerConfig config = new HttpProxyServerConfig();
        config.setHandleSsl(true);
        config.setMaxHeaderSize(8192 * 2);
        new HttpProxyServer()
                .serverConfig(config)
                .start(9999);
    }
}
