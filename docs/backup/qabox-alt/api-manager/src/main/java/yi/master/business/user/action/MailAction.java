package yi.master.business.user.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.user.bean.Mail;
import yi.master.business.user.bean.User;
import yi.master.business.user.service.MailService;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.sql.Timestamp;

/**
 * 用户邮件Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class MailAction extends BaseAction<Mail> {
	private static final long serialVersionUID = 1L;

	private String statusName;
	
	private String status;
	
	private MailService mailService;
	@Autowired
	public void setMailService(MailService mailService) {
		super.setBaseService(mailService);
		this.mailService = mailService;
	}
	
	@Override
	public String[] prepareList() {
		
		User user = FrameworkUtil.getLoginUser();
		if (user != null) {
			this.filterCondition = new String[]{"receiveUser.userId=" + user.getUserId()};
		}		
		return this.filterCondition;
	}

	//获取未读邮件数量
	public String getNoReadMailNum() {
		User user = FrameworkUtil.getLoginUser();
		int num = 0;
		if (user != null) {
			num = mailService.getNoReadNum(user.getUserId());
		}
		setData(num);
		return SUCCESS;
	}
	

	//改变邮件状态
	public String changeStatus() {
		if (statusName.equals("sendStatus") || statusName.equals("readStatus") || statusName.equals("ifValidate")) {			
			if (statusName.equals("sendStatus")) {
				Mail mail1 = mailService.get(model.getMailId());
				if (mail1.getReceiveUser() == null) {
					throw new YiException(AppErrorCode.MAIL_MISS_RECEIVER);
				}
				mail1.setSendTime(new Timestamp(System.currentTimeMillis()));
				mailService.edit(mail1);
			}
			mailService.changeStatus(model.getMailId(), statusName, status);
		} else {
			throw new YiException(AppErrorCode.MISS_PARAM);
		}		
		return SUCCESS;
	}
	

	public String sendMail () {
	    return SUCCESS;
    }
	
	/****************************************************************************************************************/
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

}
