package yi.master.coretest.message.parse;

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import yi.master.business.message.bean.ComplexParameter;
import yi.master.business.message.bean.Parameter;
import yi.master.business.message.service.ParameterService;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.message.JsonUtil;
import yi.master.util.message.JsonUtil.TypeEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static yi.master.constant.MessageKeys.MessageParameterType;

/**
 * 接口自动化<br>
 * json格式报文相关的解析等方法实现
 * @author xuwangcheng
 * @version 2017.04.11,1.0.0.0
 *
 */
public class JSONMessageParse extends MessageParse {
	private static ObjectMapper mapper = new ObjectMapper();
	private static JSONMessageParse jsonMessageParse;

	private JSONMessageParse () {

	}

	public static JSONMessageParse getInstance() {
		if (jsonMessageParse == null) {
			jsonMessageParse = new JSONMessageParse();
		}

		return jsonMessageParse;
	}
	
	@Override
	public String getObjectByPath(String message, String path) {
		return JsonUtil.getObjectByJson(message, path, TypeEnum.string);
	}	
	
	protected static String JSONMessageFormatBeautify(String message) {
		
		try {
			Object obj = mapper.readValue(message, Object.class);
		    return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (Exception e) {
			LOGGER.info("json串美化失败：\n" + message, e);			
		}
		return message;
	    
	}

	@SuppressWarnings("rawtypes")
	@Override
	public ComplexParameter parseMessageToObject(String message, List<Parameter> params) {		
		Object maps = PracticalUtils.jsonToObject(message);
		if (maps == null) {
			return null;
		}
		ParameterService service = (ParameterService) FrameworkUtil.getSpringBean("parameterService");
        Integer outerParameterId = null;
        if (maps instanceof List || maps instanceof ArrayList) {
            outerParameterId = SystemConsts.DefaultObjectId.PARAMETER_OUTER_ARRAY.getId();
        } else {
            outerParameterId = SystemConsts.DefaultObjectId.PARAMETER_OBJECT.getId();
        }

		return parseObjectToComplexParameter(maps, new ComplexParameter(service.get(outerParameterId),
				new HashSet<ComplexParameter>(), null), params, new StringBuilder(MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH));
	}

	@Override
	public String depacketizeMessageToString(ComplexParameter complexParameter, String paramsData) {
		if (complexParameter == null) {
			return "";
		}
		return messageFormatBeautify(parseJsonMessage(complexParameter, new StringBuilder(""), PracticalUtils.jsonToMap(paramsData)).toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public String checkParameterValidity(List<Parameter> params, String message) {
		Object[] o = null;
		try {
			o = (Object[]) JsonUtil.getJsonList(message, 3);
		} catch (Exception e) {
			LOGGER.error("解析json失败:" + message, e);
			return "解析json失败";
		}
		
		if (o == null) {
			return "不是合法的json格式或者无内容!";
		}
		
		List<String> paramNames = (List<String>) o[0];
		List<String> paramPaths = (List<String>) o[2];
		
		boolean paramCorrectFlag = false;
		boolean allCorrectFlag = true;
		
		String returnMsg = "入参节点:";
		
		for (int i = 0; i < paramNames.size(); i++) {
			for (Parameter p:params) {
				if (paramNames.get(i).equalsIgnoreCase(p.getParameterIdentify()) && paramPaths.get(i).equalsIgnoreCase(p.getPath())) {
					paramCorrectFlag = true;
				}				
			}
			if (!paramCorrectFlag) {
				allCorrectFlag = false;
				returnMsg += "[" + paramNames.get(i) + "] ";
			} else {
				paramCorrectFlag = false;
			}
		}
		
		if (!allCorrectFlag) {
			return returnMsg + "未在接口参数中定义或者类型/路径不匹配,请检查!";
		} 
		
		return SystemConsts.DefaultBooleanIdentify.TRUE.getString();
	}
	
	
	/**
	 * 递归生成：根据复制参数的定义来生成JSON串
	 * @author xuwangcheng
	 * @date 2019/12/23 15:22
	 * @param parameter parameter 复杂参数
	 * @param message message 生成的报文
	 * @param messageData messageData 数据
	 * @return {@link StringBuilder}
	 */
	private StringBuilder parseJsonMessage(ComplexParameter parameter, StringBuilder message, Map<String, Object> messageData) {
		
		Parameter param = parameter.getSelfParameter();
		
		if (param == null || param.getType() == null) {
			return message;
		}

		String parameterType = param.getType().toUpperCase();

		List<ComplexParameter> childParams = new ArrayList<ComplexParameter>();
		if (parameter.getChildComplexParameters() != null) {
			childParams.addAll(parameter.getChildComplexParameters());
		}
				

		if (!parameterType.matches(MessageParameterType.ARRAY_ARRAY.name() + "|" + MessageParameterType.ARRAY_MAP.name()
				+ "|" + MessageParameterType.OBJECT.name() + "|" + MessageParameterType.OUTER_ARRAY.name())) {
			message.append("\"" + param.getParameterIdentify()).append("\":");
		}

		if (MessageParameterType.isObjectType(parameterType)) {
			message.append("{");
			for (int i = 0; i < childParams.size(); i++) {
				if (childParams.get(i).getSelfParameter() == null) {
					continue;
				}
                parseJsonMessage(childParams.get(i), message, messageData);
				if (i < childParams.size() - 1) {
					message.append(",");
				}
			}
			message.append("}");
		}

		if (MessageParameterType.isArrayType(parameterType)) {
			message.append("[");
			if (childParams.size() == 0) {
                message.append(findParameterValue(param, messageData));
            } else {
                for (int i = 0; i < childParams.size(); i++) {
                    if (childParams.get(i).getSelfParameter() == null) {
                        continue;
                    }
                    parseJsonMessage(childParams.get(i), message, messageData);
                    if (i < childParams.size() - 1) {
                        message.append(",");
                    }
                }
            }
			message.append("]");
		}

		if (MessageParameterType.STRING.name().equals(parameterType)
				|| MessageParameterType.CDATA.name().equals(parameterType)) {
			message.append("\"" + findParameterValue(param, messageData) + "\"");
		}

		if (MessageParameterType.NUMBER.name().equals(parameterType)) {
			message.append(findParameterValue(param, messageData));
		}

		
		return message;
	}

	
	@Override
	public boolean messageFormatValidation(String message) {
//		Object a = null;
//		try {
//			a = JSONObject.fromObject(message);
//		} catch (Exception e) {
//            try {
//                a = JSONArray.fromObject(message);
//            } catch (Exception e1) {
//            }
//		}
//
//		return a == null ? false : true;
        try {
            new ObjectMapper().readValue(message, Object.class);
            return true;
        } catch (IOException e) {
            return false;
        }

    }

	@SuppressWarnings("unchecked")
	@Override
	public Set<Parameter> importMessageToParameter(String message, Set<Parameter> existParams) {
		
		Object[] jsonTree = null;
		try {
			jsonTree = (Object[]) JsonUtil.getJsonList(message, 3);
		} catch (Exception e) {
			LOGGER.error("解析json串失败:" + message, e);		
			return null;
		}
		
		Set<Parameter> params = new HashSet<Parameter>();
		
		if (jsonTree != null) {			
			Map<String,String> valueMap = (Map<String, String>)jsonTree[3];
			List<String> paramList = (List<String>) jsonTree[0];
			List<String> typeList = (List<String>) jsonTree[1];
			List<String> pathList = (List<String>) jsonTree[2];

			Parameter param = null;
			for (int i = 0;i < paramList.size();i++) {
				param = new Parameter(paramList.get(i), "", valueMap.get(paramList.get(i)), pathList.get(i), typeList.get(i));
				if (validateRepeatabilityParameter(params, param) && validateRepeatabilityParameter(existParams, param)) {
					params.add(param);
				}								
			}		
		} 		
		return params;
	}


	@Override
	public String createMessageByNodes(JSONObject nodes) {
		
		Map<String, Object> message = new HashMap<String, Object>(10);
		for (Object key:nodes.keySet()) {
			if ("rootId".equals(key.toString())) {continue;}
			JSONObject node = nodes.getJSONObject(key.toString());
			if (MessageParameterType.isStringOrNumberType(node.getString("type"))) {
				Object value = node.getString("defaultValue");
				if (MessageParameterType.NUMBER.name().equals(node.getString("type").toUpperCase())) {
					value = (value == null || StringUtils.isBlank(value.toString())) ? 0 : new BigDecimal(value.toString());
				}
                JSONObject parentNode = nodes.getJSONObject(node.getString("parentId"));
				String parentType = null;
                if (parentNode != null && !parentNode.isNullObject()) {
                    parentType = parentNode.getString("type").toUpperCase();
                }

				createMessageNode(node.getString("path"), message, parentType).put(node.getString("parameterIdentify"), value);
			}
		}
		return JSONMessageFormatBeautify(JSONObject.fromObject(message).toString());
	}
	
	private Map<String, Object> createMessageNode(String path, Map<String, Object> message, String parentType) {
		String[] pathNames = path.split("\\.");
		Map<String, Object> nodeObj = message;
		for (String nodePath:pathNames) {
			if (MessageKeys.MESSAGE_PARAMETER_DEFAULT_ROOT_PATH.equals(nodePath)) {
				continue;
			}
			Object nodeObj_l = nodeObj.get(nodePath);
			if (nodeObj_l == null) {
			    if (MessageParameterType.isArrayType(parentType)) {
                    nodeObj_l = new ArrayList<Map<String, Object>>();
                } else {
                    nodeObj_l = new HashMap<String, Object>();
                }
                nodeObj.put(nodePath, nodeObj_l);
			}
			// 如果上级为数组，则创建一个新对象
			if (nodeObj_l instanceof List || nodeObj_l instanceof ArrayList) {
			    List<Map<String, Object>> list = (List<Map<String, Object>>) nodeObj_l;
			    if (list.size() == 0) {
			        list.add(new HashMap<>());
                }

                nodeObj_l = list.get(0);

            }
			nodeObj = (Map<String, Object>) nodeObj_l;
		}
		
		return nodeObj;
	}

}
