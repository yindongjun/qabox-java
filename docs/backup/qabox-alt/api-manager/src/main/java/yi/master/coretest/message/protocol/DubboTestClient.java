package yi.master.coretest.message.protocol;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.log4j.Logger;
import yi.master.business.testconfig.bean.TestConfig;
import yi.master.constant.MessageKeys;
import yi.master.coretest.message.protocol.entity.ClientTestResponseObject;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.util.PracticalUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;

/**
 * 基于telnet客户端的invoke方式来调用dubbo接口
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/5 19:41
 */
public class DubboTestClient extends TestClient {
    private static final Logger logger = Logger.getLogger(DubboTestClient.class);

    private static DubboTestClient dubboTestClient;

    public static final Character lastChar = '>';
    public static final String endingTag = "dubbo>";

    private DubboTestClient () {}

    public static DubboTestClient getInstance () {
        if (dubboTestClient == null) {
            dubboTestClient = new DubboTestClient();
        }

        return dubboTestClient;
    }

    @Override
    public ClientTestResponseObject sendRequest(TestMessageScene scene, Object client) {
        String requestUrl = scene.getRequestUrl();
        String requestMessage = scene.getRequestMessage();
        Map<String, Object> callParameter = scene.getCallParameter();
        TestConfig config = scene.getConfig();

        ClientTestResponseObject responseObject = new ClientTestResponseObject();
        responseObject.setStatusCode("false");

        int connectTimeOut = config.getConnectTimeOut();
        int readTimeOut = config.getReadTimeOut();
        if (callParameter != null) {
            try {
                if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT))) {
                    readTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT));
                }
                if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT))) {
                    connectTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT));
                }
            } catch (Exception e) {
                LOGGER.info("报文附加参数获取出错:" + callParameter.toString(), e);
            }
        }
        String[] urls = requestUrl.split(":");
        if (3 != urls.length) {
            responseObject.addMark("配置出错,请检查！");
            return responseObject;
        }

        String host = urls[0];
        Integer port = 20880;
        if (NumberUtil.isInteger(urls[1])) {
            port = Integer.valueOf(urls[1]);
        }
        String method = urls[2];

        TelnetClient telnetClient = null;
        try {
            telnetClient = createClient(host, port,connectTimeOut, readTimeOut);
        } catch (IOException e) {
            responseObject.addMark("Telnet无法连接到:" + host + ":" + port);
        }

        if (telnetClient != null && telnetClient.isConnected()) {
            PrintStream out = new PrintStream(telnetClient.getOutputStream());
            InputStream in = telnetClient.getInputStream();

            try {
                long start = System.currentTimeMillis();
                responseObject.addMark(StrUtil.format("Telent成功连接到{}:{}", host, port));
                responseObject.addMark(StrUtil.format("执行命令： invoke {}({})", method, requestMessage));
                String responseMsg = sendMsg("invoke " + method + "(" + requestMessage + ")", out, in);
                long end = System.currentTimeMillis();
                if (responseMsg != null) {
                    logger.info(StrUtil.format("[{}:{}]Dubbo请求方法{},返回内容:\n{}", host, port, method, responseMsg));
                    responseObject.addMark(StrUtil.format("返回内容：\n{}", responseMsg));
                    if (responseMsg.indexOf("syntax error") > -1) {
                        responseObject.addMark("请求参数格式错误(注意单个字符串请用双引号括起来)");
                        return responseObject;
                    }
                    if (responseMsg.indexOf("Invalid parameters") > -1) {
                        responseObject.addMark("缺少方法参数");
                        return responseObject;
                    }
                    if (responseMsg.indexOf("No such service") > -1
                            || responseMsg.indexOf("No such method") > -1) {
                        responseObject.addMark("无此方法：" + method);
                        return responseObject;
                    }

                    responseMsg = responseMsg.replaceAll("(elapsed:.*\\d+.*ms\\.)", "")
                            .replaceAll(endingTag, "");

                    //去除左右引号
                    if (responseMsg.lastIndexOf("\"") > 0) {
                        responseMsg = responseMsg.substring(1, responseMsg.lastIndexOf("\""));
                    }
                }

                responseObject.setStatusCode("200");
                responseObject.setUseTime(end - start);
                responseObject.setResponseMessage(responseMsg);
            } catch (IOException e) {
                responseObject.addMark("调用方法 " + method + " 失败:\n" + PracticalUtils.getExceptionAllinformation(e));
            } finally {
                if (telnetClient != null && telnetClient.isConnected()) {
                    try {
                        telnetClient.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
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
    public void putBackTestClient(Object procotolClient) {
    }


    /**
     * 通过telnet发送信息
     * @param requestMsg
     * @param out
     * @param in
     * @return
     * @throws IOException
     */
    private String sendMsg (String requestMsg, PrintStream out, InputStream in) throws IOException {
        StringBuilder replyMessage = new StringBuilder("");
        out.println(requestMsg);
        out.flush();

        char ch = (char) in.read();
        while (true) {
            replyMessage.append(ch);
            if (ch == lastChar) {
                if (replyMessage.toString().endsWith(endingTag)) {
                    return replyMessage.toString();
                }
            }
            ch = (char) in.read();
        }
    }

    /**
     * 创建telnet客户端
     * @param host
     * @param port
     * @param connectTimeout
     * @param soTimeout
     * @return
     * @throws IOException
     */
    private TelnetClient createClient (String host, int port, int connectTimeout, int soTimeout) throws IOException {
        TelnetClient client = new TelnetClient();
        client.connect(host, port);
        client.setConnectTimeout(connectTimeout);
        client.setSoTimeout(soTimeout);

        return client;
    }
}
