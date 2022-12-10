package yi.master.coretest.message.parse;

import org.apache.commons.lang.StringUtils;
import yi.master.business.message.bean.ComplexParameter;
import yi.master.business.message.bean.Parameter;
import yi.master.business.message.service.ParameterService;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;

import java.util.List;
import java.util.Map;


/**
 * 自定义报文
 * <br>无限定报文，针对于目前无法识别的格式报文，报文不做任何验证
 * @author xuwangcheng
 * @version 1.0.0.0, 20171030
 *
 */
public class OPTMessageParse extends FixedMessageParse {
	private static OPTMessageParse optMessageParse;

	private OPTMessageParse () {

	}

	public static OPTMessageParse getInstance () {
		if (optMessageParse == null) {
			optMessageParse = new OPTMessageParse();
		}

		return optMessageParse;
	}

	@Override
	public String checkParameterValidity(List<Parameter> params, String message) {
		return SystemConsts.DefaultBooleanIdentify.TRUE.getString();
	}
	
	@Override
	public ComplexParameter parseMessageToObject(String message,
			List<Parameter> params) {
		
		ParameterService ps = (ParameterService) FrameworkUtil.getSpringBean("parameterService");
		int pid = ps.save(new Parameter("defaultName", "", message, MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH, "String"));
		return new ComplexParameter(new Parameter(pid), null, null);
	}

	@Override
	public String depacketizeMessageToString(ComplexParameter complexParameter,
			String paramsData) {
		if (complexParameter == null) {
			return "";
		}

		if (StringUtils.isNotEmpty(paramsData)) {
			Map<String, Object> params = PracticalUtils.jsonToMap(paramsData);
			for (Object o:params.values()) {
				return messageFormatBeautify(o.toString());
			}
		}

		if ("defaultValue".equals(complexParameter.getSelfParameter().getDefaultValue())) {
            return messageFormatBeautify(complexParameter.getSelfParameter().getParameterIdentify());
        } else {
            return messageFormatBeautify(complexParameter.getSelfParameter().getDefaultValue());
        }
	}
	
}
