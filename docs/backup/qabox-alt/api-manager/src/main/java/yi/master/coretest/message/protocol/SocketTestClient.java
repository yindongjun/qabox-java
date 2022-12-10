package yi.master.coretest.message.protocol;

import org.apache.log4j.Logger;
import yi.master.business.testconfig.bean.TestConfig;
import yi.master.constant.MessageKeys;
import yi.master.coretest.message.protocol.entity.ClientTestResponseObject;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.util.PracticalUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Map;

public class SocketTestClient extends TestClient {
	private static final Logger LOGGER = Logger.getLogger(SocketTestClient.class);
	private static SocketTestClient socketTestClient;
	private String DEFAULT_ENC_CHARSET = "utf-8";
	private String DEFAULT_REC_ENC_CHARSET = "utf-8";

	private SocketTestClient () {}

	public static SocketTestClient getInstance () {
		if (socketTestClient == null) {
			socketTestClient = new SocketTestClient();
		}

		return socketTestClient;
	}

	@Override
	public ClientTestResponseObject sendRequest(TestMessageScene scene, Object client) {
        String requestUrl = scene.getRequestUrl();
        String requestMessage = scene.getRequestMessage();
        Map<String, Object> callParameter = scene.getCallParameter();
        TestConfig config = scene.getConfig();

		ClientTestResponseObject returnMap = new ClientTestResponseObject();
		
		int connectTimeOut = config.getConnectTimeOut();
		int soTimeOut = config.getReadTimeOut();
		String encCharset = DEFAULT_ENC_CHARSET;
		String recEncCharset = DEFAULT_REC_ENC_CHARSET;
		
		if (callParameter != null ) {
			if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT))) {
				connectTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT));
			}
			
			if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT))) {
				soTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_READ_TIMEOUT));
			}

			if (callParameter.get(MessageKeys.HTTP_PARAMETER_ENC_TYPE) != null) {
			    encCharset = callParameter.get(MessageKeys.HTTP_PARAMETER_ENC_TYPE).toString();
            }

            if (callParameter.get(MessageKeys.HTTP_PARAMETER_REC_ENC_TYPE) != null) {
                recEncCharset = callParameter.get(MessageKeys.HTTP_PARAMETER_REC_ENC_TYPE).toString();
            }
		}
		
		String[] ipPort = requestUrl.split(":");
		
		long startTime = System.currentTimeMillis();
		String responseMsg = sendSocketMsg(ipPort[0], Integer.parseInt(ipPort[1]), requestMessage, connectTimeOut, soTimeOut, encCharset, recEncCharset);
        ;
		long endTime = System.currentTimeMillis();
		
		long useTime = endTime - startTime;

		returnMap.setResponseMessage(responseMsg);
		returnMap.setUseTime(useTime);
		returnMap.setStatusCode("200");
		returnMap.setMark("");
		
		if (responseMsg.startsWith("Send")) {
			returnMap.setResponseMessage("");
			returnMap.setMark(responseMsg);
			returnMap.setStatusCode("false");
		}		
		return returnMap;
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
	
	/************************************************************************************************/

    /**
     * 打开socket连接
     * @author xuwangcheng
     * @date 2020/11/25 10:25
     * @param ip ip
     * @param port port
     * @param connectimeOut connectimeOut
     * @param soTimeOut soTimeOut
     * @return {@link Socket}
     */
	private Socket open (String ip, int port, int connectimeOut, int soTimeOut) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port), connectimeOut);
        socket.setSoTimeout(soTimeOut);

        return socket;
    }

    /**
     * 已字符串的方式发送
     * @param ip
     * @param port
     * @param request
     * @param connectTimeOut
     * @param soTimeOut
     * @param encCharset
     * @param recEncCharset
     * @return
     */
	private String sendSocketMsg(String ip, int port, String request, int connectTimeOut, int soTimeOut, String encCharset, String recEncCharset) {
		Socket socket = null;
		StringBuilder responseMsg = new StringBuilder("");
		PrintWriter pw = null;
		BufferedReader br = null;
		
		try {
			socket = open(ip, port, connectTimeOut, soTimeOut);
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			//输出流 指定编码
	        pw = new PrintWriter(new OutputStreamWriter(os, encCharset));
	        //输入流  指定编码
	        br = new BufferedReader(new InputStreamReader(is, recEncCharset));

            pw.write(request);
            pw.flush();

            socket.shutdownOutput();

            String reply;
            while (((reply = br.readLine()) != null)) {
                responseMsg.append(reply);
            }
		} catch (Exception e) {
			LOGGER.debug("Send Socket msg to [" + ip + ":" + port + "] Fail！", e);
			responseMsg = new StringBuilder("Send Socket msg to [" + ip + ":" + port + "] Fail:" + e.getMessage());
		} finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                LOGGER.warn("IOException", e);
            }
		}		
		return responseMsg.toString();
	}



    private static int byteArrayToInt(byte[] data, int length, int alignment)
            throws Exception
    {
        int value = 0;
        switch (alignment)
        {
            case 0:
                for (int i = 0; i < length; i++)
                {
                    value <<= 8;
                    value += toInt(data[i]);
                }
                break;
            case 1:
                for (int i = 0; i < length; i++)
                {
                    value <<= 8;
                    value += toInt(data[(length - i - 1)]);
                }
                break;
            default:
                throw new Exception("数据对齐方式非法！");
        }
        return value;
    }

    private static int toInt(byte b)
    {
        if (b >= 0) {
            return b;
        }
        return b + 256;
    }

    private byte[] intToByteArray(int value, int length, int alignment)
            throws Exception
    {
        byte[] data = new byte[length];
        switch (alignment)
        {
            case 0:
                for (int i = 0; i < length; i++)
                {
                    data[(length - i - 1)] = ((byte)(value & 0xFF));
                    value >>>= 8;
                }
                break;
            case 1:
                for (int i = 0; i < length; i++)
                {
                    data[i] = ((byte)(value & 0xFF));
                    value >>>= 8;
                }
                break;
            default:
                throw new Exception("数据对齐方式非法！");
        }
        return data;
    }
}
