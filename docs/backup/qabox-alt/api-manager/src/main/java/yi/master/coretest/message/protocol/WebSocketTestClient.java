package yi.master.coretest.message.protocol;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import yi.master.business.testconfig.bean.TestConfig;
import yi.master.constant.MessageKeys;
import yi.master.coretest.message.protocol.entity.ClientTestResponseObject;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.util.PracticalUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/12 8:59
 */
public class WebSocketTestClient extends TestClient {
    private static final Logger logger = Logger.getLogger(WebSocketTestClient.class);
    private static WebSocketTestClient webSocketTestClient;
    private WebSocketTestClient () {}

    public static WebSocketTestClient getInstance () {
        if (webSocketTestClient == null) {
            webSocketTestClient = new WebSocketTestClient();
        }

        return webSocketTestClient;
    }

    @Override
    public ClientTestResponseObject sendRequest(TestMessageScene scene, Object client) {
        String requestUrl = scene.getRequestUrl();
        String requestMessage = scene.getRequestMessage();
        Map<String, Object> callParameter = scene.getCallParameter();
        TestConfig config = scene.getConfig();

        int connectTimeOut = config.getConnectTimeOut();
        int readTimeOut = config.getReadTimeOut();
        if (callParameter != null) {
            try {
                if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT))) {
                    connectTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT));
                }
                if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT))) {
                    readTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT));
                }
            } catch (Exception e) {
                LOGGER.info("报文附加参数获取出错:" + callParameter.toString(), e);
            }
        }
        final ClientTestResponseObject responseObject = new ClientTestResponseObject();
        WebSocketClient webSocketClient = null;
        try {
            webSocketClient = createClient(requestUrl, responseObject);
            webSocketClient.connectBlocking(connectTimeOut, TimeUnit.MILLISECONDS);
            webSocketClient.getSocket().setSoTimeout(readTimeOut);

            long startTime = System.currentTimeMillis();
            webSocketClient.send(requestMessage);
            while (StringUtils.isBlank(responseObject.getStatusCode()) && (System.currentTimeMillis() - startTime) <= readTimeOut) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    LOGGER.warn("InterruptedException", e);
                }
            }

            if (StringUtils.isBlank(responseObject.getStatusCode())) {
                responseObject.addMark("请求超时");
                responseObject.setStatusCode("false");
            }

            if (responseObject.getUseTime() != 0) {
                responseObject.setUseTime(responseObject.getUseTime() - startTime);
            }

            webSocketClient.close();
        } catch (Exception e) {
            logger.info(requestUrl + ",websocket error!", e);
            responseObject.setStatusCode("false");
            responseObject.addMark(PracticalUtils.getExceptionAllinformation(e));
        } finally {
            if (webSocketClient != null && webSocketClient.isOpen()) {
                webSocketClient.close();
            }
        }
        return responseObject;
    }

    @Override
    public boolean testInterface(String requestUrl) {
        return false;
    }

    @Override
    public Object getTestClient() {
        return null;
    }

    @Override
    public void putBackTestClient(Object protocolClient) {
    }

    private WebSocketClient createClient(String url,final ClientTestResponseObject responseObject) throws URISyntaxException {
        WebSocketClient client = new WebSocketClient(new URI( url)) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                logger.debug(StrUtil.format("[{}]WebSocket open...", url));
            }

            @Override
            public void onMessage(String s) {
                logger.info(StrUtil.format("[{}]WebSocket receive msg:{}", url, s));
                responseObject.setUseTime(System.currentTimeMillis());
                responseObject.setResponseMessage(s);
                responseObject.setStatusCode("200");
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                logger.debug(StrUtil.format("[{}]WebSocket close...", url));

            }

            @Override
            public void onError(Exception e) {
                logger.info(StrUtil.format("[{}]WebSocket error:{}", url, PracticalUtils.getExceptionAllinformation(e)));
                if (StringUtils.isBlank(responseObject.getStatusCode())) {
                    responseObject.addMark(PracticalUtils.getExceptionAllinformation(e));
                    responseObject.setResponseMessage("");
                    responseObject.setStatusCode("false");
                }
            }
        };


        return client;
    }
}
