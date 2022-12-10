package yi.master.business.advanced.service.impl;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.advanced.bean.InterfaceMock;
import yi.master.business.advanced.bean.config.mock.MockRequestValidateConfig;
import yi.master.business.advanced.bean.config.mock.MockResponseConfig;
import yi.master.business.advanced.bean.config.mock.MockValidateRuleConfig;
import yi.master.business.advanced.dao.InterfaceMockDao;
import yi.master.business.advanced.enums.InterfaceMockStatus;
import yi.master.business.advanced.service.InterfaceMockService;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Message;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.message.dao.MessageSceneDao;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.parse.MessageParse;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service("interfaceMockService")
public class InterfaceMockServiceImpl extends BaseServiceImpl<InterfaceMock> implements InterfaceMockService {
    private InterfaceMockDao interfaceMockDao;

    @Autowired
    private MessageSceneDao messageSceneDao;

	@Autowired
	public void setInterfaceMockDao(InterfaceMockDao interfaceMockDao) {
		super.setBaseDao(interfaceMockDao);
		this.interfaceMockDao = interfaceMockDao;
	}

	@Override
	public InterfaceMock findByMockUrl(String mockUrl, String protocolType) {
		return interfaceMockDao.findByMockUrl(mockUrl, protocolType);
	}

	@Override
	public void updateStatus(Integer mockId, String status) {
		
		interfaceMockDao.updateStatus(mockId, status);
	}

	@Override
	public void updateSetting(Integer mockId, String settingType,
			String configJson) {
		
		if (StringUtils.isBlank(settingType) || StringUtils.isBlank(configJson)) {
            return;
        }
		interfaceMockDao.updateSetting(mockId, settingType, configJson);
	}

	@Override
	public List<InterfaceMock> getEnableMockServer() {
		return interfaceMockDao.getEnableMockServer();
	}

    @Override
    public void updateCallCount(Integer mockId) {
        interfaceMockDao.updateCallCount(mockId);
    }

    @Override
    public boolean parseSceneToMock(Integer sceneId, Integer projectId) {
        MessageScene messageScene = messageSceneDao.get(sceneId);
        if (messageScene == null) {
            throw new YiException(AppErrorCode.SCENE_INFO_NOT_EXIST);
        }

		Message message = messageScene.getMessage();
        if (message == null) {
        	throw new YiException(AppErrorCode.MESSAGE_INFO_NOT_EXITS);
		}

		InterfaceInfo interfaceInfo = message.getInterfaceInfo();
        if (interfaceInfo == null) {
        	throw new YiException(AppErrorCode.INTERFACE_INFO_NOT_EXIST);
		}

        if (MessageKeys.ProtocolType.https.name().equalsIgnoreCase(interfaceInfo.getInterfaceProtocol())) {
            interfaceInfo.setInterfaceProtocol(MessageKeys.ProtocolType.http.name().toUpperCase());
        }

		//http/socket/websocket才支持场景转mock
		boolean flag = !MessageKeys.ProtocolType.http.name().equalsIgnoreCase(interfaceInfo.getInterfaceProtocol())
				&& !MessageKeys.ProtocolType.socket.name().equalsIgnoreCase(interfaceInfo.getInterfaceProtocol())
				&& !MessageKeys.ProtocolType.websocket.name().equalsIgnoreCase(interfaceInfo.getInterfaceProtocol());
		if (flag) {
        	throw new YiException(AppErrorCode.MOCK_PROTOCOL_NOT_SUPPORT);
		}


		InterfaceMock mockInfo = new InterfaceMock();
        mockInfo.setCallCount(0);
        mockInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        mockInfo.setMark("由接口场景自动生成");
		mockInfo.setMockName(interfaceInfo.getInterfaceName());
		mockInfo.setProjectInfo(new ProjectInfo(projectId == null ? SystemConsts.DefaultObjectId.DEFAULT_PROJECT_ID.getId() : projectId));

		//获取mockUrl地址，按照优先级获取
		String requestUrl = "";
		if (StringUtils.isNotBlank(messageScene.getRequestUrl())) {
			requestUrl = messageScene.getRequestUrl();
		}
		if (StringUtils.isBlank(requestUrl) && StringUtils.isNotBlank(message.getRequestUrl())) {
			requestUrl = message.getRequestUrl();
		}
		if (StringUtils.isBlank(requestUrl)) {
			requestUrl = interfaceInfo.getRequestUrlReal();
		}

		//判断url是否重复
        if (StringUtils.isNotBlank(requestUrl)) {
            InterfaceMock im = findByMockUrl(requestUrl, interfaceInfo.getInterfaceProtocol());
            if (im != null) {
                throw new YiException(AppErrorCode.MOCK_URL_EXIST, requestUrl);
            }
        }

		mockInfo.setMockUrl(requestUrl);
		mockInfo.setProtocolType(interfaceInfo.getInterfaceProtocol());
		mockInfo.setStatus(InterfaceMockStatus.DISABLED.getStatus());
		mockInfo.setUser(FrameworkUtil.getLoginUser());

		MockRequestValidateConfig requestValidateConfig = new MockRequestValidateConfig();
		//接口Mock的msgType只支持xml/json/url
        if (message.getMessageType().equalsIgnoreCase(MessageKeys.MessageType.OPT.name())
                || message.getMessageType().equalsIgnoreCase(MessageKeys.MessageType.FIXED.name())) {
            requestValidateConfig.setMessageType("");
        } else {
            requestValidateConfig.setMessageType(message.getMessageType());
        }

		MessageParse parseUtil = MessageParse.getParseInstance(message.getMessageType());
		String requestMsg = parseUtil.depacketizeMessageToString(message.getComplexParameter(), null);

		if (StringUtils.isNotBlank(requestMsg)) {
			requestValidateConfig.setParameters(MockValidateRuleConfig.parseValidateRuleList(requestMsg, message.getMessageType()));
		}
		if (MessageKeys.ProtocolType.http.name().equalsIgnoreCase(interfaceInfo.getInterfaceProtocol())) {
			String callParameter = message.getCallParameter();
			Map<String, Object> parameters = PracticalUtils.jsonToMap(callParameter);
			if (parameters != null) {
				Object headers =  parameters.get(MessageKeys.HTTP_PARAMETER_HEADER);
				Object querys = parameters.get(MessageKeys.HTTP_PARAMETER_QUERYS);
				Object method = parameters.get(MessageKeys.PUBLIC_PARAMETER_METHOD);

				if (headers != null) {
					requestValidateConfig.setHeaders(MockValidateRuleConfig.parseValidateRuleList(JSONObject.fromObject(headers).toString()
							, MessageKeys.MessageType.JSON.name()));
				}

				if (querys != null) {
					requestValidateConfig.setQuerys(MockValidateRuleConfig.parseValidateRuleList(JSONObject.fromObject(querys).toString()
							, MessageKeys.MessageType.JSON.name()));
				}

				if (method != null) {
					requestValidateConfig.setMethod(method.toString());
				}

			}
		}

		mockInfo.setRequestValidate(JSONObject.fromObject(requestValidateConfig).toString());

		String responseMsg = messageScene.getResponseExample();
		MockResponseConfig responseConfig = new MockResponseConfig();
		if (StringUtils.isNotBlank(responseMsg)) {
			responseConfig.setExampleResponseMsg(responseMsg);
            responseConfig.setMessageType("text");
		}

		mockInfo.setResponseMock(JSONObject.fromObject(responseConfig).toString());

		edit(mockInfo);
        return true;
    }
}
