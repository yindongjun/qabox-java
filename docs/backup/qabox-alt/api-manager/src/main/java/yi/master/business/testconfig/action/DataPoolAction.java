package yi.master.business.testconfig.action;

import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.service.DataPoolService;
import yi.master.business.testconfig.service.PoolDataItemService;
import yi.master.business.testconfig.service.PoolDataNameService;
import yi.master.business.testconfig.service.PoolDataValueService;
import yi.master.coretest.message.parse.JSONMessageParse;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 17:58
 */
@Controller("dataPoolAction")
@Scope("prototype")
public class DataPoolAction extends BaseAction<DataPool> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private DataPoolService dataPoolService;

    @Autowired
    public void setDataPoolService(DataPoolService dataPoolService) {
        super.setBaseService(dataPoolService);
        this.dataPoolService = dataPoolService;
    }

    @Autowired
    private PoolDataItemService poolDataItemService;
    @Autowired
    private PoolDataNameService poolDataNameService;
    @Autowired
    private PoolDataValueService poolDataValueService;


    @Override
    public String[] prepareList() {
        List<String> conditions = new ArrayList<String>();
        if (projectId != null) {
            conditions.add("t.projectInfo.projectId=" + projectId);
        }

        this.filterCondition = conditions.toArray(new String[0]);
        return this.filterCondition;
    }

    @Override
    public String edit() {
        if (this.model.getPoolId() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
            model.setUser(FrameworkUtil.getLoginUser());
        }
        return super.edit();
    }

    /**
     *  获取指定数据池的变量名称列表
     * @author xuwangcheng
     * @date 2021/1/3 14:57
     * @param
     * @return {@link String}
     */
    public String listDataName () {
        if (model.getPoolId() == null) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }

        setData(poolDataNameService.listDataName(model.getPoolId()));

        return SUCCESS;
    }

    /**
     *  json数据示例
     * @author xuwangcheng
     * @date 2021/1/7 12:16
     * @param
     * @return {@link String}
     */
    public String showJsonData () {
        DataPool dataPool = dataPoolService.get(model.getPoolId());
        if (dataPool == null) {
            throw new YiException("信息不存在!");
        }

        JSONObject jsonObject = new JSONObject();
        for (PoolDataName name:dataPool.getDataNames()) {
            jsonObject.put(name.getName(), name.getDefaultValue() == null ? "" : name.getDefaultValue());
        }

        setData(JSONMessageParse.getInstance().messageFormatBeautify(jsonObject.toString()));
        return SUCCESS;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
}
