package yi.master.coretest.message.test.mock;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import yi.master.constant.SystemConsts;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.net.InetSocketAddress;

/**
 *
 * webSocketMock服务
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/11/22 8:52
 */
public class MockWebSocketServer extends MockServer {
    private static final Logger logger = Logger.getLogger(MockWebSocketServer.class);
    private WebSocketServer webSocketServer;

    protected MockWebSocketServer(Integer mockId) throws Exception {
        super(mockId);
    }

    @Override
    public void start() {
        webSocketServer = new WebSocketServer(new InetSocketAddress(getUnUsePort())) {
            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                logger.debug(StrUtil.format("WebSocketMockServer {} on Open...", getMockUrl()));
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                logger.debug(StrUtil.format("WebSocketMockServer {} on Close:\ncode={}", getMockUrl(), code));
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                logger.info(StrUtil.format("WebSocketMockServer {} on Message:\n{}", getMockUrl(), message));
                updateCallCount();
                //处理消息
                String responseMsg = validateConfig.validate(null, message);
                if (SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(responseMsg)) {
                    responseMsg = mockConfig.generate(null, message);
                } else if (StringUtils.isNotBlank(mockConfig.getExampleErrorMsg())) {
                    responseMsg = PracticalUtils.replaceGlobalVariable(mockConfig.getExampleErrorMsg().replace("${errorMsg}", responseMsg), null);
                }
                //回复消息
                conn.send(responseMsg);
                logger.info(StrUtil.format("WebSocketMockServer {} Response :\n{}", getMockUrl(), responseMsg));
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                logger.debug(StrUtil.format("WebSocketMockServer {} on Error...", getMockUrl()));
            }

            @Override
            public void onStart() {
                logger.debug(StrUtil.format("WebSocketMockServer {} Start...", getMockUrl()));
            }
        };

        webSocketServer.start();
        CacheUtil.getMockServers().put(mockId, this);
    }

    @Override
    public void stop() {
        try {
            if (webSocketServer != null) {
                webSocketServer.stop();
            }
            CacheUtil.getMockServers().remove(mockId);
        } catch (Exception e) {
            logger.error("关闭WebSocket server 失败！", e);
        }
    }

    @Override
    public String getMockUrl() {
        if (StringUtils.isBlank(mockUrl)) {
            mockUrl = "ws://" + getHost() + ":" + this.webSocketServer.getPort();
        }
        return mockUrl;
    }

}
