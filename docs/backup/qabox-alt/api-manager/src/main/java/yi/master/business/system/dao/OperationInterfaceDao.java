package yi.master.business.system.dao;

import java.util.List;

import yi.master.business.base.dao.BaseDao;
import yi.master.business.system.bean.OperationInterface;

/**
 * 操作接口DAO接口
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.14
 */

public interface OperationInterfaceDao extends BaseDao<OperationInterface> {

	/**
	 * 获取指定role拥有的操作接口
	 * @param roleId
	 * @return
	 */
	List<OperationInterface> listByRoleId(Integer roleId);

	/**
	 *  获取指定页面上的权限
	 * @author xuwangcheng
	 * @date 2019/11/24 18:56
	 * @param pageName pageName
	 * @return {@link List}
	 */
	List<OperationInterface> listByPageName(String pageName);
}
