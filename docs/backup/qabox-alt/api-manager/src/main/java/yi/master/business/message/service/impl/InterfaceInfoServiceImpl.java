package yi.master.business.message.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.bean.Message;
import yi.master.business.message.bean.Parameter;
import yi.master.business.message.dao.InterfaceInfoDao;
import yi.master.business.message.service.ComplexParameterService;
import yi.master.business.message.service.InterfaceInfoService;
import yi.master.business.message.service.MessageService;
import yi.master.business.message.service.ParameterService;
import yi.master.constant.MessageKeys;

import java.util.List;
import java.util.Set;

/**
 * 接口信息Service实现
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.13
 */
@Service("interfaceInfoService")
public class InterfaceInfoServiceImpl extends BaseServiceImpl<InterfaceInfo> implements InterfaceInfoService {
	
	private InterfaceInfoDao interfaceInfoDao;
	@Autowired
	private MessageService messageService;
	@Autowired
	private ParameterService parameterService;
	@Autowired
	private ComplexParameterService complexParameterService;
	
	@Autowired
	public void setInterfaceInfoDao(InterfaceInfoDao interfaceInfoDao) {
		super.setBaseDao(interfaceInfoDao);
		this.interfaceInfoDao = interfaceInfoDao;
	}
	
	

	@Override
	public List<InterfaceInfo> findInterfaceByCondition(String condition) {
		
		return interfaceInfoDao.findInterfaceByCondition(condition);
	}

	@Override
	public void changeStatus(int id, String status) {
		interfaceInfoDao.changeStatus(id, status);
		
	}

	@Override
	public InterfaceInfo findInterfaceByName(String interfaceName) {
		
		return interfaceInfoDao.findInterfaceByName(interfaceName);
	}

	@Override
	public void copyInterfaceInfo(InterfaceInfo interfaceInfo, List<String> copyParams) {
		//todo 接口复制，包括接口信息和参数信息，报文信息
	}

    @Override
    public void batchInsertParam(Parameter parameter, String ids, boolean updateMessage) {
	    parameter.setPath(MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH + (StringUtils.isBlank(parameter.getPath()) ? "" : ("." + parameter.getPath())));
        List<InterfaceInfo> interfaceInfos = findAll("all".equalsIgnoreCase(ids) ? "1 = 1" : "interfaceId in (" + ids + ")");
        for (InterfaceInfo info:interfaceInfos) {
            // 父节点ID是否存在？
            Integer parentParameterId = Parameter.getParentId(parameter.getPath(), info.getParameters());
            if (parentParameterId == null) {
                continue;
            }
            Parameter insertParameter = parameter.clone();
            insertParameter.setInterfaceInfo(info);
            insertParameter.setParameterId(parameterService.save(insertParameter));

            // 插入报文的节点信息
            if (updateMessage) {
                // 根节点
                if (parentParameterId == 0) {
                    Set<Message> messages = info.getMessages();
                    for (Message msg:messages) {
                        complexParameterService.insert(insertParameter.getParameterId(), msg.getComplexParameter().getId());
                    }
                } else {
                    List<Integer> parentIds = complexParameterService.listIds(parentParameterId);
                    for (Integer id:parentIds) {
                        complexParameterService.insert(insertParameter.getParameterId(), id);
                    }
                }
            }

        }
    }
}
