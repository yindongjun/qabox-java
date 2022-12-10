package yi.master.business.message.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Parameter;

import java.util.List;

/**
 * 接口信息Service接口
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */
public interface InterfaceInfoService extends BaseService<InterfaceInfo> {
	
	/**
	 * 通过传入的条件来操作指定的接口信息列表
	 * @param condition  查询条件 可为interfaceId或者interfaceName(模糊匹配)
	 * @return List<InterfaceInfo>  符合条件的interfaceInfo集合
	 */
	List<InterfaceInfo> findInterfaceByCondition(String condition);
	
	/**
	 * 改变指定id的接口信息的状态
	 * @param id 接口id
	 * @param status 需要变更为的状态 只能为"0"或者"1"
	 * @return void
	 */
	void changeStatus(int id,String status);
	
	/**
	 * 根据接口名来查找指定的接口信息
	 * @param interfaceName 接口名称
	 * @return InterfaceInfo 符合条件的接口信息
	 */
	InterfaceInfo findInterfaceByName(String interfaceName);

	/**
	 *  复制接口
	 * @author xuwangcheng
	 * @date 2020/8/3 15:20
	 * @param interfaceInfo interfaceInfo
	 * @param copyParams copyParams
	 * @return
	 */
	void copyInterfaceInfo (InterfaceInfo interfaceInfo, List<String> copyParams);

	/**
	 *  批量往接口中插入参数
	 * @author xuwangcheng
	 * @date 2021/3/8 9:28
	 * @param parameter parameter
	 * @param ids ids
	 * @param updateMessage updateMessage 是否将新增的参数更新到接口下的报文中去
	 * @return
	 */
	void batchInsertParam(Parameter parameter, String ids, boolean updateMessage);
}
