package io.fluentqa.alt.proxyee.handler;

import io.fluentqa.alt.proxyee.server.HttpProxyServerConfig;
import io.fluentqa.alt.proxyee.util.ProtoUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.proxy.ProxyHandler;

/**
 * HTTP代理，转发解码后的HTTP报文
 */
public class HttpProxyInitializer extends ChannelInitializer {

    private Channel clientChannel;
    private ProtoUtil.RequestProto requestProto;
    private ProxyHandler proxyHandler;

    public HttpProxyInitializer(Channel clientChannel, ProtoUtil.RequestProto requestProto,
                                ProxyHandler proxyHandler) {
        this.clientChannel = clientChannel;
        this.requestProto = requestProto;
        this.proxyHandler = proxyHandler;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        if (proxyHandler != null) {
            ch.pipeline().addLast(proxyHandler);
        }
        HttpProxyServerConfig serverConfig = ((HttpProxyServerHandler) clientChannel.pipeline().get("serverHandle")).getServerConfig();
        if (requestProto.getSsl()) {
            ch.pipeline().addLast(serverConfig.getClientSslCtx().newHandler(ch.alloc(), requestProto.getHost(), requestProto.getPort()));
        }
        ch.pipeline().addLast("httpCodec", new HttpClientCodec(
                serverConfig.getMaxInitialLineLength(),
                serverConfig.getMaxHeaderSize(),
                serverConfig.getMaxChunkSize()));
        ch.pipeline().addLast("proxyClientHandle", new HttpProxyClientHandler(clientChannel));
    }
}
