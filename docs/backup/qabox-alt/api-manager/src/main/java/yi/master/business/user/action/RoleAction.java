package yi.master.business.user.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.hutool.core.collection.CollUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import yi.master.business.base.action.BaseAction;
import yi.master.business.system.bean.BusiMenuInfo;
import yi.master.business.system.bean.OperationInterface;
import yi.master.business.system.service.BusiMenuInfoService;
import yi.master.business.user.bean.Role;
import yi.master.business.user.bean.User;
import yi.master.business.user.service.RoleService;
import yi.master.constant.ReturnCodeConsts;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.cache.CacheUtil;

/**
 * 角色信息Action
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BusiMenuInfoService busiMenuInfoService;
	
	private RoleService roleService;
	@Autowired
	public void setRoleService(RoleService roleService) {
		super.setBaseService(roleService);
		this.roleService = roleService;
	}
	
	
	/**
	 * 指定角色被删除的操作权限的id集合
	 * 以逗号分隔
	 */
	private String delOpIds;
	
	/**
	 * 指定角色增加的操作权限的id集合
	 * 以逗号分隔
	 */
	private String addOpIds;
	
	/**
	 * 绑定每个角色的拥有权限个数
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object processListData(Object o) {
		List<Role> roles = (List<Role>) o;
		for (Role r:roles) {
			r.setOiNum();
			r.setMenuNum();
		}
		
		return roles;
	}
	
	/**
	 * 删除角色,但是不能删除预置的管理员角色和默认角色
	 */
	@Override
	public String del() {
		if (id == SystemConsts.DefaultObjectId.ADMIN_ROLE.getId() || id == SystemConsts.DefaultObjectId.DEFAULT_ROLE.getId()) {
			throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "不能删除管理员角色或者默认角色");
		}		
		//删除其他角色,配置该角色的用户变更成default角色
		roleService.del(id);
		return SUCCESS;
	}
	
	@Override
	public void checkObjectName() {
		Role role = roleService.get(model.getRoleName());
		checkNameFlag = (role != null && !role.getRoleId().equals(model.getRoleId())) ? "重复的角色名" : "true";
		
		if (model.getRoleId() == null) {
			checkNameFlag = (role == null) ? "true" : "重复的角色名";
		}
	}
	
	/**
	 * 编辑角色信息
	 * 根据传入的id判断是否为新增或者更新
	 */
	@Override
	public String edit() {
		checkObjectName();	
		
		if (!checkNameFlag.equals("true")) {
			throw new YiException(AppErrorCode.NAME_EXIST);
		}
		
		if (model.getRoleId() != null) {
			if (SystemConsts.DefaultObjectId.ADMIN_ROLE.getId() == model.getRoleId()
					|| SystemConsts.DefaultObjectId.DEFAULT_ROLE.getId() == model.getRoleId()) {
				throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "不能编辑预置管理员或者默认角色信息");
			}
			//修改
			model.setOis(roleService.get(model.getRoleId()).getOis());
		}
		roleService.edit(model);
		return SUCCESS;
	}
	
	/**
	 * 获取当前所有的操作接口列表
	 * 并且对当前角色已拥有的操作接口打标记
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getInterfaceNodes() {		
		List<OperationInterface> ops = CacheUtil.getSystemInterfaces();
		Role role = roleService.get(model.getRoleId());
		Set<OperationInterface> ownOps = role.getOis();
		
		for (OperationInterface op : ops) {
			op.setIsOwn(false);			
			for (OperationInterface op1 : ownOps) {
				if (op1.getOpId().equals(op.getOpId())) {
					op.setIsOwn(true);
				}
			}
		}
		setData(ops);
		return SUCCESS;
	}

	/**
	 * 获取当前用户的角色权限列表
	 * @author xuwangcheng
	 * @date 2019/11/24 18:45
	 * @param
	 * @return {@link String}
	 */
	public String getUserPermissionList () {
		User user = FrameworkUtil.getLoginUser();
		if (user == null) {
			throw new YiException(AppErrorCode.NO_LOGIN);
		}
		Role role = roleService.get(user.getRole().getRoleId());
		Set<OperationInterface> ownOps = role.getOis();
		Map<Integer, OperationInterface> maps = new HashMap<>();
		for (OperationInterface o:ownOps) {
			maps.put(o.getOpId(), o);
		}

		setData(maps);
		return SUCCESS;
	}

	/**
	 * 获取当前用户的菜单树
	 * @return
	 */
	public String getMenuNodes() {
		List<BusiMenuInfo> menus = busiMenuInfoService.findAll();
		Role role = roleService.get(model.getRoleId());
		Set<BusiMenuInfo> ownMenus = role.getMenus();
		
		for (BusiMenuInfo m:menus) {
			m.setIsOwn(false);
			for (BusiMenuInfo ownM:ownMenus) {
				if (ownM.getMenuId().equals(m.getMenuId())) {
					m.setIsOwn(true);
				}
			}
		}

		setData(menus);
		return SUCCESS;
	}

	/**
	 * 更新菜单与角色的关系
	 * @return
	 */
	public String updateRoleMenu() {
		Role role = roleService.get(model.getRoleId());
		Set<BusiMenuInfo> menus = role.getMenus();
		
		//更新增加的菜单
		BusiMenuInfo menu = null;
		if (addOpIds != null && !addOpIds.isEmpty()) {
			String[] addOpArray = addOpIds.split(",");
			for (String s : addOpArray) {
				menu = new BusiMenuInfo();
				menu.setMenuId(Integer.valueOf(s));
				menus.add(menu);
			}
		}
		
		//更新删除的权限
		if (delOpIds != null && !delOpIds.isEmpty()) {
			String[] delOpArray = delOpIds.split(",");
			for (String s : delOpArray) {
				menu = new BusiMenuInfo();
				menu.setMenuId(Integer.valueOf(s));
				menus.remove(menu);
			}
		}
		role.setMenus(menus);
		roleService.edit(role);

		return SUCCESS;
	}
	
	/**
	 * 更新角色的权限信息
	 * 更新角色与操作接口的关联关系
	 * @return
	 */
	public String updateRolePower() {
		Role role = roleService.get(model.getRoleId());
		Set<OperationInterface> ops = role.getOis();
		//更新增加的权限
		OperationInterface o = null;
		if (addOpIds != null && !addOpIds.isEmpty()) {
			String[] addOpArray = addOpIds.split(",");
			for (String s : addOpArray) {
				o = new OperationInterface();
				o.setOpId(Integer.valueOf(s));
				ops.add(o);
			}
			
		}
		//更新删除的权限
		if (delOpIds != null && !delOpIds.isEmpty()) {
			String[] delOpArray = delOpIds.split(",");
			for (String s : delOpArray) {
				o = new OperationInterface();
				o.setOpId(Integer.valueOf(s));
				ops.remove(o);
			}
		}
		role.setOis(ops);
		roleService.edit(role);

		return SUCCESS;
	}
	
	/********************************************************************************************/
	public void setDelOpIds(String delOpIds) {
		this.delOpIds = delOpIds;
	}
	
	public void setAddOpIds(String addOpIds) {
		this.addOpIds = addOpIds;
	}
	
	
}
