package yi.master.coretest.message.protocol;

import org.apache.log4j.Logger;
import yi.master.constant.MessageKeys;
import yi.master.coretest.message.protocol.entity.ClientTestResponseObject;
import yi.master.coretest.message.test.TestMessageScene;

/**
 * 
 * 测试客户端，不同协议的请求通过工厂方法获取
 * @author xuwangcheng
 * @version 1.0.0.0,2017.4.24
 *
 */
public abstract class TestClient {
	
	public static final Logger LOGGER = Logger.getLogger(TestClient.class.getName());

	/**
	 *  具体发送请求的方法，各协议不相同
	 * @author xuwangcheng
	 * @date 2021/1/30 16:38
	 * @param scene scene
	 * @param client client
	 * @return {@link ClientTestResponseObject}
	 */
	public abstract ClientTestResponseObject  sendRequest(TestMessageScene scene, Object client);
	
	/**
	 * 测试该接口地址是否可通
	 * @param requestUrl
	 * @return
	 */
	public abstract boolean testInterface(String requestUrl);
	
	/**
	 * 获取一个全新的测试客户端
	 */
	public abstract Object getTestClient();
	
	/**
	 * 将测试客户端放回池子中
	 */
	public abstract void putBackTestClient(Object protocolClient);
	
	/**
	 * 根据协议类型返回指定的测试客户端
	 * 
	 * @param type 协议类型 目前支持的：http/https webservice socket
	 * @return
	 */
	public static TestClient getTestClientInstance(String type) {
		return MessageKeys.ProtocolType.getClientByType(type);
	}
	
}
