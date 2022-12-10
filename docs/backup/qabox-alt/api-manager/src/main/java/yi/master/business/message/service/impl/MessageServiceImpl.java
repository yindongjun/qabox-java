package yi.master.business.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.Message;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.message.dao.MessageDao;
import yi.master.business.message.service.MessageSceneService;
import yi.master.business.message.service.MessageService;

import java.util.List;

/**
 * 接口报文Service接口实现
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.17
 */

@Service("messageService")
public class MessageServiceImpl extends BaseServiceImpl<Message> implements MessageService{
	
	private MessageDao messageDao;

	@Autowired
	private MessageSceneService messageSceneService;
	
	@Autowired
	public void setMessageDao(MessageDao messageDao) {
		super.setBaseDao(messageDao);
		this.messageDao = messageDao;
	}


    @Override
    public Integer save(Message message, Boolean createDefaultScene) {
        if (message.getMessageId() != null) {
            message.setMessageId(null);
        }

        messageDao.getSession().clear();
	    message.setMessageId(messageDao.save(message));

        if (createDefaultScene == true) {
            MessageScene messageScene = new MessageScene();
            messageScene.setMessage(message);
            messageScene.setProjectInfo(message.getProjectInfo());
            messageScene.setSceneName("正常场景");
            messageScene.setSystems(message.getSystems());
            messageScene.setMark("这是自动创建的正常测试场景");

            messageSceneService.save(messageScene, true, null);
        }

        return message.getMessageId();
    }

    @Override
    public void copyMessage(List<Message> messages, List<String> copyParams) {
        //TODO 报文信息复制
    }
}
