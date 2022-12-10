package yi.master.business.testconfig.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.service.PoolDataNameService;
import yi.master.constant.SystemConsts;
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
@Controller("poolDataNameAction")
@Scope("prototype")
public class PoolDataNameAction extends BaseAction<PoolDataName> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private PoolDataNameService poolDataNameService;

    private Integer poolId;

    @Autowired
    public void setPoolDataNameService(PoolDataNameService poolDataNameService) {
        super.setBaseService(poolDataNameService);
        this.poolDataNameService = poolDataNameService;
    }

    @Override
    public String[] prepareList() {
        List<String> conditions = new ArrayList<String>();
        if (this.poolId != null) {
            conditions.add("t.dataPool.poolId=" + this.poolId);
        }

        this.filterCondition = conditions.toArray(new String[0]);
        return this.filterCondition;
    }

    @Override
    public String edit() {
        //判断接口名是否重复
        this.poolId = this.model.getDataPool().getPoolId();
        checkObjectName();
        if (!checkNameFlag.equals(SystemConsts.DefaultBooleanIdentify.TRUE.getString())) {
            throw new YiException(AppErrorCode.NAME_EXIST);
        }
        if (this.model.getId() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
            model.setUser(FrameworkUtil.getLoginUser());
        }
        return super.edit();
    }

    /**
     * 判断接口名重复性
     * 新增或者修改状态下均可用
     */
    @Override
    public void checkObjectName() {
        PoolDataName info = poolDataNameService.findByName(model.getName(), poolId);
        checkNameFlag = (info != null && !info.getId().equals(model.getId())) ? "重复的名称" : "true";

        if (model.getId() == null) {
            checkNameFlag = (info == null) ? "true" : "重复的名称";
        }
    }


    /**
     *  批量新增
     * @author xuwangcheng
     * @date 2021/1/6 18:04
     * @param
     * @return {@link String}
     */
    public String batchAdd () {
        if (StrUtil.isBlank(model.getName()) || poolId == null) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }

        List<String> names = CollUtil.newArrayList(model.getName().split("\\n+"));
        List<String> existNames = CollUtil.newArrayList();
        int successCount = 0;
        for (String name:names) {
            if (StrUtil.isBlank(name)) {
                continue;
            }
            if (poolDataNameService.findByName(name, poolId) == null) {
                PoolDataName poolDataName = new PoolDataName();
                poolDataName.setUser(FrameworkUtil.getLoginUser());
                poolDataName.setCreateTime(new Timestamp(System.currentTimeMillis()));
                poolDataName.setDataPool(new DataPool(poolId));
                poolDataName.setName(name);
                poolDataName.setMark(name);
                poolDataNameService.save(poolDataName);
                successCount++;
            } else {
                existNames.add(name);
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("existNames", existNames);
        jsonObject.put("successCount", successCount);
        setData(jsonObject);

        return SUCCESS;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }
}
