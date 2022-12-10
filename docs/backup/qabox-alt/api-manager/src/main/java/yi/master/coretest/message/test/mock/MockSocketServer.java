package yi.master.coretest.message.test.mock;

import cn.hutool.core.thread.ThreadUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import yi.master.constant.SystemConsts;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * socket类型的mock服务
 * @author xuwangcheng
 * @version 20180731
 *
 */
public class MockSocketServer extends MockServer {
	
	private static final Logger logger = Logger.getLogger(MockSocketServer.class);
	
	private ServerSocket socket;

	protected MockSocketServer(Integer mockId) throws Exception {
		super(mockId);
	}

	@Override
	public void start() {
        boolean flag = true;
        while (flag) {
            try {
                socket = new ServerSocket(PracticalUtils.getRandomNum(65535, 10025));
                flag = false;
            } catch (Exception e) {
                logger.error("创建Socket server 出错, 等待重新创建!", e);
            }
        }

        ThreadUtil.execute(new Runnable() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    Socket connection =null;
                    try {
                        connection = socket.accept();
                        updateCallCount();
                        String requestMessage = readMessageFromClient(connection.getInputStream());
                        logger.debug("MockSocketServer " + getMockUrl() + " 接收到数据：" + requestMessage);
                        String responseMsg = validateConfig.validate(null, requestMessage);
                        if (SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(responseMsg)) {
                            responseMsg = mockConfig.generate(null, requestMessage);
                        } else if (StringUtils.isNotBlank(mockConfig.getExampleErrorMsg())) {
                            responseMsg = PracticalUtils.replaceGlobalVariable(mockConfig.getExampleErrorMsg().replace("${errorMsg}", responseMsg), null);
                        }

                        writeMsgToClient(connection.getOutputStream(), responseMsg);
                        logger.debug("MockSocketServer " + getMockUrl() + " 回复数据：" + responseMsg);
                        connection.close();
                    } catch (Exception e) {
                        logger.error("Socket Server " + getMockUrl() + " 处理socket请求出错!", e);
                    } finally {
                        if (connection != null) {
                            try {
                                connection.close();
                            } catch (IOException e) {
                                logger.warn("IOException", e);
                            }
                        }
                    }
                }
            }
        });

        CacheUtil.getMockServers().put(mockId, this);
	}

	@Override
    public String getMockUrl() {
	    if (StringUtils.isBlank(mockUrl)) {
	        mockUrl = getHost() + ":" + socket.getLocalPort();
        }
        return mockUrl;
    }

    @Override
	public void stop() {
		try {
			if (socket != null) {
				socket.close();
			}	
			CacheUtil.getMockServers().remove(mockId);
		} catch (IOException e) {
			logger.error("关闭Socket server 失败！", e);
		}
	}
	
	
	/** 
     * 读取客户端信息 
     * @param inputStream 
     */  
    private String readMessageFromClient(InputStream inputStream) throws IOException {  
        Reader reader = new InputStreamReader(inputStream);  
        BufferedReader br=new BufferedReader(reader);  
        String a = null;  
        StringBuilder requestMessage = new StringBuilder();
        while(StringUtils.isNotBlank((a = br.readLine()))){  
        	requestMessage.append(a);
        }
        
        return requestMessage.toString();
    }  
  
    /** 
     * 响应客户端信息 
     * @param outputStream 
     * @param string 
     */  
    private void writeMsgToClient(OutputStream outputStream, String string) throws IOException {  
        Writer writer = new OutputStreamWriter(outputStream);  
        writer.append(string);  
        writer.flush();  
        writer.close();  
    }
}
