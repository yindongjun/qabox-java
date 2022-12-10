package yi.master.business.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.*;
import yi.master.business.message.dao.MessageSceneDao;
import yi.master.business.message.dao.SceneValidateRuleDao;
import yi.master.business.message.dao.TestDataDao;
import yi.master.business.message.enums.CommonStatus;
import yi.master.business.message.enums.TestDataStatus;
import yi.master.business.message.service.MessageSceneService;
import yi.master.business.testconfig.bean.GlobalVariable;
import yi.master.business.testconfig.dao.GlobalVariableDao;

import java.sql.Timestamp;
import java.util.List;

/**
 * 报文场景service实现
 * @author xuwangcheng
 * @version 1.0.0,2017.3.6
 */
@Service("messageSceneService")
public class MessageSceneServiceImpl extends BaseServiceImpl<MessageScene> implements MessageSceneService{

	private MessageSceneDao messageSceneDao;

	@Autowired
	private TestDataDao testDataDao;

	@Autowired
	private GlobalVariableDao globalVariableDao;

	@Autowired
	private SceneValidateRuleDao sceneValidateRuleDao;
	
	@Autowired
	public void setMessageSceneDao(MessageSceneDao messageSceneDao) {
		super.setBaseDao(messageSceneDao);
		this.messageSceneDao = messageSceneDao;
	}
	
	@Override
	public void updateValidateFlag(Integer messageSceneId, String validateRuleFlag) {
		
		messageSceneDao.updateValidateFlag(messageSceneId, validateRuleFlag);
	}

	@Override
	public List<MessageScene> getBySetId(Integer setId) {
		
		return messageSceneDao.getBySetId(setId);
	}

	@Override
	public InterfaceInfo getInterfaceOfScene(Integer messageSceneId) {
		
		return messageSceneDao.getInterfaceOfScene(messageSceneId);
	}

	@Override
	public Message getMessageOfScene(Integer messageSceneId) {
		
		return messageSceneDao.getMessageOfScene(messageSceneId);
	}

	@Override
	public void updateResponseExample(Integer messageSceneId, String response) {
		
		messageSceneDao.updateResponseExample(messageSceneId, response);
	}

    @Override
    public Integer save(MessageScene messageScene, Boolean createDefaultData, Integer variableId) {
	    if (messageScene.getMessageSceneId() != null) {
	        messageScene.setMessageSceneId(null);
        }

        messageScene.setCreateTime(new Timestamp(System.currentTimeMillis()));

	    messageSceneDao.getSession().clear();
        messageScene.setMessageSceneId(messageSceneDao.save(messageScene));

        if (createDefaultData) {
            //新增时默认该该场景添加一条默认数据
            TestData defaultData = new TestData();
            defaultData.setDataDiscr("默认数据");
            defaultData.setStatus(TestDataStatus.AVAILABLE.getStatus());
            defaultData.setMessageScene(messageScene);
            defaultData.setParamsData("");
            defaultData.setDefaultData(CommonStatus.ENABLED.getStatus());
            testDataDao.save(defaultData);
        }

        //是否配置关联验证模板
        if (variableId != null) {
            GlobalVariable v = globalVariableDao.get(variableId);
            SceneValidateRule rule = (SceneValidateRule) v.createSettingValue();
            rule.setMessageScene(messageScene);
            sceneValidateRuleDao.save(rule);
        }
        return messageScene.getMessageSceneId();
    }

	@Override
	public void copyScene(List<MessageScene> scenes, List<String> copyParams) {
		// TODO 场景复制
	}

}
