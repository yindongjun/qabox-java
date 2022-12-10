package yi.master.coretest.message.test.mock;

import cn.hutool.core.thread.ThreadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.advanced.bean.config.mock.MockRequestValidateConfig;
import yi.master.business.advanced.bean.config.mock.MockResponseConfig;
import yi.master.business.advanced.enums.InterfaceMockStatus;
import yi.master.business.advanced.enums.MockConfigSettingType;
import yi.master.business.advanced.service.InterfaceMockService;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * mock服务抽象类
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/11/21 19:02
 */
public abstract class MockServer {
    private static final Logger logger = LoggerFactory.getLogger(MockServer.class);

    protected static final InterfaceMockService mockService = (InterfaceMockService) FrameworkUtil.getSpringBean(InterfaceMockService.class);
    protected Integer mockId;
    protected MockRequestValidateConfig validateConfig;
    protected MockResponseConfig mockConfig;
    protected String errorMsg;
    protected String mockUrl;

    protected MockServer(Integer mockId) throws Exception {
        this.mockId = mockId;
        InterfaceMock interfaceMock = mockService.get(mockId);
        if (interfaceMock == null) {
            throw new Exception("Mock信息不存在!");
        }

        validateConfig = MockRequestValidateConfig.getInstance(interfaceMock.getRequestValidate());
        mockConfig = MockResponseConfig.getInstance(interfaceMock.getResponseMock());
    }

    /**
     * 实例化Mock服务
     * @author xuwangcheng
     * @date 2019/11/22 10:02
     * @param protocolType protocolType
     * @param mockId mockId
     * @return {@link MockServer}
     */
    public static MockServer getMockServerInstance (String protocolType, Integer mockId) throws Exception {
        if (MessageKeys.ProtocolType.socket.name().equalsIgnoreCase(protocolType)) {
            return new MockSocketServer(mockId);
        }
        if (MessageKeys.ProtocolType.websocket.name().equalsIgnoreCase(protocolType)) {
            return new MockWebSocketServer(mockId);
        }


        return null;
    }

    /**
     *  通过mockId开启或者关闭mock服务
     * @author xuwangcheng
     * @date 2019/11/22 16:38
     * @param mockId mockId
     * @return
     */
    public static void handleMockServer (Integer mockId) {
        InterfaceMock model = mockService.get(mockId);
        if (model == null) {
            return;
        }

        MockServer server = CacheUtil.getMockServers().get(model.getMockId());
        //关闭
        if (server != null && (InterfaceMockStatus.DISABLED.getStatus().equals(model.getStatus()))) {
            server.stop();
        }
        //开启
        if (server == null && InterfaceMockStatus.ENABLED.getStatus().equals(model.getStatus())) {
            try {
                MockServer mockServer = MockServer.getMockServerInstance(model.getProtocolType(), model.getMockId());
                if (mockServer != null) {
                    mockServer.start();
                }
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
        }
    }

    /**
     * 是否已经开启运行
     * @author xuwangcheng
     * @date 2019/11/22 16:51
     * @param mockId mockId
     * @return {@link boolean}
     */
    public static boolean isOpen (Integer mockId) {
        return CacheUtil.getMockServers().get(mockId) != null;
    }

    /**
     *  启动Mock服务
     * @author xuwangcheng
     * @date 2019/11/22 10:22
     * @param
     * @return
     */
    public abstract void start();
    /**
     * 停止Mock服务
     * @author xuwangcheng
     * @date 2019/11/22 10:23
     * @param
     * @return
     */
    public abstract void stop();
    /**
     *  获取请求地址，不包含路径
     * @author xuwangcheng
     * @date 2019/11/22 10:23
     * @param
     * @return {@link String}
     */
    public abstract String getMockUrl();

    /**
     * 更新Mock配置
     * @author xuwangcheng
     * @date 2019/11/22 10:25
     * @param settingType settingType
     * @param settingValue settingValue
     * @return
     */
    public void updateConfig(String settingType, String settingValue) {
        if (MockConfigSettingType.responseMock.name().equals(settingType)) {
            this.mockConfig = MockResponseConfig.getInstance(settingValue);
        }
        if (MockConfigSettingType.requestValidate.name().equals(settingType)) {
            this.validateConfig = MockRequestValidateConfig.getInstance(settingValue);
        }
    }

    protected String getHost () {
        String ip = "";
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (Exception e) {
            logger.error("获取服务器本地地址失败,使用配置选项!", e);
            String homeUrl = CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_HOME);
            ip = homeUrl.substring(homeUrl.indexOf("/") + 2, homeUrl.lastIndexOf(":"));
        }

        return ip;
    }

    /**
     * 测试本机端口是否被使用
     * @param port
     * @return
     */
    protected boolean isLocalPortUsing(int port){
        boolean flag = true;
        try {
            //如果该端口还在使用则返回true,否则返回false,127.0.0.1代表本机
            flag = isPortUsing("127.0.0.1", port);
        } catch (Exception e) {
        }
        return flag;
    }

    /**
     * 获取空闲可使用端口
     * @author xuwangcheng
     * @date 2019/11/26 9:40
     * @param
     * @return {@link int}
     */
    protected int getUnUsePort () {
        int randomPort = PracticalUtils.getRandomNum(65535, 1024);
        while (isLocalPortUsing(randomPort)) {
            randomPort = PracticalUtils.getRandomNum(65535, 1024);
        }

        return randomPort;
    }

    /***
     * 测试主机Host的port端口是否被使用
     * @param host
     * @param port
     * @throws UnknownHostException
     */
    private boolean isPortUsing(String host, int port) throws UnknownHostException {
        boolean flag = false;
        InetAddress Address = InetAddress.getByName(host);
        Socket socket = null;
        try {
            //建立一个Socket连接
            socket = new Socket(Address, port);
            flag = true;
        } catch (IOException e) {
            logger.error("IOException", e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    logger.error("IOException", e);
                }
            }
        }
        return flag;
    }


    /**
     * 更新调用次数
     * @author xuwangcheng
     * @date 2019/11/22 16:16
     * @param
     * @return
     */
    protected void updateCallCount () {
        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                mockService.updateCallCount(mockId);
            }
        });
    }

}
