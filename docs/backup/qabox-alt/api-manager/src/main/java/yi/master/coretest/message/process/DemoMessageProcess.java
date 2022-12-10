package yi.master.coretest.message.process;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import yi.master.coretest.message.process.config.DemoMessageProcessParameter;

import java.util.Map;

/**
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2021/1/20  16:35
 */
public class DemoMessageProcess extends MessageProcess {
    private static final Logger logger = Logger.getLogger(DemoMessageProcess.class);
    private static DemoMessageProcess demoMessageProcess;

    private DemoMessageProcess(){}

    public static DemoMessageProcess getInstance() {
        if (demoMessageProcess == null) {
            demoMessageProcess = new DemoMessageProcess();
        }

        return demoMessageProcess;
    }

    @Override
    public String processRequestMessage(String requestMessage, Map<String, String> headers, String processParameter) {
        // 获取参数实例
        JSONObject jsonObject = JSONObject.parseObject(processParameter);
        // 如果没有创建配置参数类，可以直接使用上面的jsonObject来获取参数
        DemoMessageProcessParameter parameter = JSONObject.toJavaObject(jsonObject, DemoMessageProcessParameter.class);

        // 对requestMessage做任何操作，包括格式化，加密等
        // do something
        logger.info("使用DemoMessageProcess处理入参报文...");
        logger.info("入参：" + requestMessage);
        logger.info("处理器参数为：" + parameter.toString());
        return requestMessage;
    }

    @Override
    public String processResponseMessage(String responseMessage, String processParameter) {
        // 获取参数实例
        JSONObject jsonObject = JSONObject.parseObject(processParameter);
        // 如果没有创建配置参数类，可以直接使用上面的jsonObject来获取参数
        DemoMessageProcessParameter parameter = JSONObject.toJavaObject(jsonObject, DemoMessageProcessParameter.class);

        // 对responseMessage做任何操作，包括格式化，解密等
        // do something
        logger.info("使用DemoMessageProcess处理出参报文...");
        logger.info("出参：" + responseMessage);
        logger.info("处理器参数为：" + parameter.toString());
        return responseMessage;
    }
}
