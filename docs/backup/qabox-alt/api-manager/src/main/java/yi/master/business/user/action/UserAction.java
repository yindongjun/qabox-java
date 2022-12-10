package yi.master.business.user.action;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.cpjit.swagger4j.annotation.API;
import com.cpjit.swagger4j.annotation.APIs;
import com.cpjit.swagger4j.annotation.Param;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.user.bean.User;
import yi.master.business.user.enums.UserStatus;
import yi.master.business.user.service.UserService;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.MD5Util;
import yi.master.util.ParameterMap;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;


/**
 * 用户信息Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
@APIs
public class UserAction extends BaseAction<User>{

	private static final long serialVersionUID = 1L;	
	
	private static final Logger LOGGER =Logger.getLogger(UserAction.class);
	
	/**
	 * 对用户进行锁定或者解锁
	 */
	private String mode;
	/**
	 * 第三方登录token
	 */
	private String token;
	
	/**
	 * 登录验证码
	 */
	private String verifyCode;
	
	private UserService userService;
	@Autowired
	public void setUserService(UserService userService) {
		super.setBaseService(userService);
		this.userService = userService;
	}
	
	@Override
	public String[] prepareList() {
		this.filterCondition = new String[]{"ifNew='atp'"};
		return this.filterCondition;
	}
	
	
	/**
	 * 用户登录
	 * @return
	 * @throws NoSuchAlgorithmException 
	 */
	@API(value = "user-toLogin", summary = "用户登录", parameters = {
			@Param(name = "username", description = "用户名", required = true, type = "string"),
			@Param(name = "password", description = "密码", required = true, type = "string"),
			@Param(name = "verifyCode", description = "验证码", required = true,type= "string"),
			@Param(name = "loginIdentification", description = "登录标识",type= "string")
	})
	public String toLogin() throws NoSuchAlgorithmException {
		boolean passwdLogin = false;
		if (PracticalUtils.isNormalString(model.getLoginIdentification())) {
			model = userService.loginByIdentification(model.getUsername(), model.getLoginIdentification());
		} else {
			//如果是账号密码登录，先验证验证码
			if (StringUtils.isBlank(verifyCode) 
					|| !verifyCode.equalsIgnoreCase(FrameworkUtil.getSessionMap().get(SystemConsts.SESSION_ATTRIBUTE_VERIFY_CODE).toString())) {
			    throw new YiException(AppErrorCode.USER_VERIFY_CODE_ERROR);
			}
            FrameworkUtil.getSessionMap().put(SystemConsts.SESSION_ATTRIBUTE_VERIFY_CODE, "");
			model = userService.login(model.getUsername(), MD5Util.code(model.getPassword()));
			passwdLogin = true;
		}
		
		User user = FrameworkUtil.getLoginUser();

		if (model == null) {
			throw new YiException(AppErrorCode.USER_ERROR_ACCOUNT);
		}

		if (user != null && user.getUserId().equals(model.getUserId())) {
			throw new YiException(AppErrorCode.USER_RE_LOGIN);
		}

		if (UserStatus.LOCKED.getStatus().equalsIgnoreCase(model.getStatus())) {
			throw new YiException(AppErrorCode.USER_ACCOUNT_LOCK);
		}

		setData(new ParameterMap().put("user", model)
				.put("lastLoginTime", PracticalUtils.formatDate(PracticalUtils.FULL_DATE_PATTERN, model.getLastLoginTime())));


		//将用户信息放入session中
		FrameworkUtil.getSessionMap().put("user", model);
		FrameworkUtil.getSessionMap().put("lastLoginTime", PracticalUtils.formatDate(PracticalUtils.FULL_DATE_PATTERN, model.getLastLoginTime()));
		model.setLastLoginTime(new Timestamp(System.currentTimeMillis()));

		if (passwdLogin == true) {
			model.setLoginIdentification(PracticalUtils.createUserLoginIdentification(model.getPassword()));
		}

		userService.edit(model);
		LOGGER.info("用户" + model.getRealName() + "[ID=" + model.getUserId() + "]" + "登录成功!");


		return SUCCESS;		
	}
	
	/**
	 * 用户登出
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String logout() {
		LOGGER.info("用户" + (FrameworkUtil.getLoginUser()).getRealName() + "已登出!");
		((SessionMap)FrameworkUtil.getSessionMap()).invalidate();

		return SUCCESS;
	}
	
	/**
	 * 判断用户是否登录
	 * @return
	 */
	public String judgeLogin() {
		User user = FrameworkUtil.getLoginUser();

		if (user == null) {
			throw new YiException(AppErrorCode.NO_LOGIN);
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前已登录用户的基本信息
	 * @return
	 */
	public String getLoginUserInfo() {
		User user = null;
		
		if (StringUtils.isNotEmpty(token)) {
			user = (User) FrameworkUtil.getApplicationMap().get(token);
		}
		
		String usertoken = (String) FrameworkUtil.getSessionMap().get("token");
		
		if (user != null) {
			FrameworkUtil.getApplicationMap().remove(token);
			
			if (!token.equals(usertoken)) {
				FrameworkUtil.getSessionMap().put("token", token);
			}
		}
		
		if (user == null ) {
			user = FrameworkUtil.getLoginUser();
		}

		if (user == null) {
			throw new YiException(AppErrorCode.NO_LOGIN);
		}

		setData(new ParameterMap().put("user", user)
				.put("lastLoginTime", FrameworkUtil.getSessionMap().get("lastLoginTime"))
                .put("homeUrl", CacheUtil.getSettingValue(SystemConsts.GLOBAL_SETTING_HOME)));

		FrameworkUtil.getSessionMap().put("user", user);

		user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		userService.edit(user);

		return SUCCESS;
	}
	
	/**
	 * 登录用户修改自己的真实姓名
	 * @return
	 */
	public String editMyName() {
		User user = FrameworkUtil.getLoginUser();
		userService.updateRealName(model.getRealName(), user.getUserId());
		user.setRealName(model.getRealName());

		return SUCCESS;
	}
	
	/**
	 * 用户验证密码
	 * @return
	 */
	public String verifyPasswd() {
		User user = FrameworkUtil.getLoginUser();
		try {
			if (!user.getPassword().equals(MD5Util.code(model.getPassword()))) {
				throw new YiException(AppErrorCode.USER_PASSWORD_VALIDATE_ERROR);
			}
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("加密失败!", e);
			return ERROR;
		}		
		return SUCCESS;
	}

	/**
	 * 修改密码
	 * @return
	 */
	public String modifyPasswd() {
		User user = FrameworkUtil.getLoginUser();
		try {
			userService.resetPasswd(user.getUserId(), MD5Util.code(model.getPassword()));
			user.setPassword(MD5Util.code(model.getPassword()));
			//重置登录标识
			user.setLoginIdentification("");
			userService.edit(user);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("加密失败!", e);
			return ERROR;
		}		

		return SUCCESS;
	}
	
	/**
	 * 管理员操作
	 * 删除指定用户
	 */
	@Override
	public String del() {
		if (SystemConsts.DefaultObjectId.ADMIN_USER.getId() == model.getUserId()) {
			throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "不能删除预置管理员用户!");
		}
		userService.delete(id);

		return SUCCESS;
	}
	
	/**
	 * 根据状态来锁定或者解锁用户
	 * @return
	 */
	public String lock() {
		if (SystemConsts.DefaultObjectId.ADMIN_USER.getId() == model.getUserId()) {
			throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "不能锁定预置管理员用户!");
		}
		userService.lockUser(model.getUserId(), mode);

		return SUCCESS;
		
	}
	
	/**
	 * 登录用户操作
	 * 重置密码为111111
	 * @return
	 */
	public String resetPwd() {
		try {
			userService.resetPasswd(model.getUserId(), MD5Util.code(SystemConsts.DEFAULT_USER_PASSWORD));
		} catch (NoSuchAlgorithmException e) {
			LOGGER.warn("NoSuchAlgorithmException", e);
		}

		return SUCCESS;
	}
	
	
	/**
	 * 管理员操作
	 * 编辑用户详细信息
	 */
	@Override
	public String edit() {
		User u1 = userService.validateUsername(model.getUsername(), model.getUserId());
		if(u1 != null){
			throw new YiException(AppErrorCode.NAME_EXIST.getCode(), "用户名已存在!");
		}
		if (model.getUserId() == null) {
			//新增
			model.setIfNew("atp");
			model.setCreateTime(new Timestamp(System.currentTimeMillis()));
			try {
				model.setPassword(MD5Util.code(SystemConsts.DEFAULT_USER_PASSWORD));
			} catch (NoSuchAlgorithmException e) {
				LOGGER.error("密码加密失败!", e);
				return ERROR;
			}
			model.setStatus(UserStatus.NORMAL.getStatus());
			model.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
		} else {
			//修改
			User u2 = userService.get(model.getUserId());
			model.setIfNew(u2.getIfNew());
			model.setPassword(u2.getPassword());			
		}
		userService.edit(model);

		return SUCCESS;
	}
	
	
	/**
	 * 按条件查询指定用户
	 * @return
	 */
	public String filter() {
		List<User> users = userService.findByRealName(model.getRealName());
		
		if (users.size() == 0) {
			throw new YiException(AppErrorCode.NO_RESULT.getCode(), "没有查询到指定的用户");
		}
		setData(users);
		return SUCCESS;
	}
	
	/**
	 * 创建验证码图片并返回路径给前台
	 * @return
	 */
	public String createVerifyCode() {
		LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);

		FrameworkUtil.getSessionMap().put(SystemConsts.SESSION_ATTRIBUTE_VERIFY_CODE
				, lineCaptcha.getCode());
		setData(lineCaptcha.getImageBase64());
		return SUCCESS;
	}	
	
	/*****************************GET-SET******************************************************/
	
	public void setMode(String mode) {
		this.mode = mode;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
	
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
}
