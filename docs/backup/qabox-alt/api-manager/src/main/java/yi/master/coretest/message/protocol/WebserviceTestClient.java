package yi.master.coretest.message.protocol;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import yi.master.constant.MessageKeys;
import yi.master.coretest.message.protocol.entity.ClientTestResponseObject;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.util.PracticalUtils;

import javax.xml.namespace.QName;
import java.util.Map;

public class WebserviceTestClient extends TestClient {
	private static WebserviceTestClient webserviceTestClient;

	private WebserviceTestClient () {}

	public static WebserviceTestClient getInstance () {
		if (webserviceTestClient == null) {
			webserviceTestClient = new WebserviceTestClient();
		}

		return webserviceTestClient;
	}

	@Override
	public ClientTestResponseObject sendRequest(TestMessageScene scene, Object client) {
        String requestUrl = scene.getRequestUrl();
        String requestMessage = scene.getRequestMessage();
        Map<String, Object> callParameter = scene.getCallParameter();

		String username = null;
		String password = null;
		String namespace = "";
		String method = "";
		int connectTimeOut = 5000;
		
		if (callParameter != null) {
			username = (String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_USERNAME);
			password = (String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_PASSWORD);
			namespace = (String) callParameter.get(MessageKeys.WEB_SERVICE_PARAMETER_NAMESPACE);
			method = (String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_METHOD);
            if (PracticalUtils.isNumeric(callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT))) {
                connectTimeOut = Integer.parseInt((String) callParameter.get(MessageKeys.PUBLIC_PARAMETER_CONNECT_TIMEOUT));
            }
		}
		
		String responseMessage = "";
		Long useTime = 0L;
		String statusCode = "200";
		String mark = "";
		
		try {
			long beginTime = System.currentTimeMillis();
			responseMessage = callService(requestUrl, requestMessage, namespace, method, connectTimeOut, username, password);
			long endTime = System.currentTimeMillis();
			useTime = endTime - beginTime;
		} catch (Exception e) {
			statusCode = "false";
			mark = "Fail to call web-service url=" + requestUrl + ",namespace=" + namespace + ",method=" + method + "!";
		}
		ClientTestResponseObject returnMap = new ClientTestResponseObject();

		returnMap.setMark(mark);
		returnMap.setUseTime(useTime);
		returnMap.setResponseMessage(responseMessage);
		returnMap.setStatusCode(statusCode);
		return returnMap;
	}

	@Override
	public boolean testInterface(String requestUrl) {
		
		return true;
	}
	
	@Override
	public Object getTestClient() {
		
		return null;
	}

	@Override
	public void putBackTestClient(Object procotolClient) {
		
		
	}
	
	/****************************************************************************************************/
	/**
	 * 使用Axis2调用webservice<br>
	 * 使用RPC方式调用
	 * @param requestUrl
	 * @param request
	 * @param namespace
	 * @param method
	 * @param connectTimeOut
	 * @return
	 * @throws Exception 
	 */
	private String callService (String requestUrl, String request, String namespace, String method, long connectTimeOut, String username, String password) throws Exception {
		try {
			RPCServiceClient client = new RPCServiceClient();
			Options option = client.getOptions();

			// 指定调用的的wsdl地址
			//例如：http://ws.webxml.com.cn/WebServices/ChinaOpenFundWS.asmx?wsdl
			EndpointReference reference = new EndpointReference(requestUrl);
			option.setTo(reference);
			option.setTimeOutInMilliSeconds(connectTimeOut);
			
			if (PracticalUtils.isNormalString(username) && PracticalUtils.isNormalString(password)) {
				option.setUserName(username);
				option.setPassword(password);
			}

			/*
			 * 设置要调用的方法 
			 * http://ws.apache.org/axis2 为默认的（无package的情况）命名空间，
			 * 如果有包名，则为 http://axis2.webservice.elgin.com ,包名倒过来即可 
			 * method为方法名称
			 */
			QName qname = new QName(namespace, method);

			// 调用远程方法,并指定方法参数以及返回值类型
			Object[] result = client.invokeBlocking(qname,
					new Object[] { request }, new Class[] { String.class });
			return result[0].toString();
		} catch (Exception e) {
			LOGGER.error("Fail to call web-service url=" + requestUrl + ",namespace=" + namespace + ",method=" + method + "!", e);
			throw e;
		}
				          
	}	
}
