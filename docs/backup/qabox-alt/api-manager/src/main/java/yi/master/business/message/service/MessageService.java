package yi.master.business.message.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.message.bean.Message;

import java.util.List;

/**
 * 接口报文Service接口
 * 
 * @author xuwangcheng
 * @version 1.0.0.0,2017.2.17
 */

public interface MessageService extends BaseService<Message> {
    /**
     *  保存报文
     * @author xuwangcheng
     * @date 2019/11/15 17:06
     * @param message message
     * @param createDefaultScene createDefaultScene  是否创建默认的测试场景
     * @return {@link Integer}
     */
    Integer save(Message message, Boolean createDefaultScene);

    /**
     *  复制报文
     * @author xuwangcheng
     * @date 2020/8/3 15:41
     * @param messages messages
     * @param copyParams copyParams
     * @return
     */
    void copyMessage (List<Message> messages, List<String> copyParams);
}
