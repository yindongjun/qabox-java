package yi.master.business.testconfig.action;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.service.DataPoolService;
import yi.master.business.testconfig.service.PoolDataItemService;
import yi.master.business.testconfig.service.PoolDataValueService;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.excel.ExcelPoolDataTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 17:58
 */
@Controller("poolDataItemAction")
@Scope("prototype")
public class PoolDataItemAction extends BaseAction<PoolDataItem> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private PoolDataItemService poolDataItemService;

    @Autowired
    private PoolDataValueService poolDataValueService;
    @Autowired
    private DataPoolService dataPoolService;

    private Integer poolId;

    private String exportItemIds;

    private String path;

    @Autowired
    public void setPoolDataItemService(PoolDataItemService poolDataItemService) {
        super.setBaseService(poolDataItemService);
        this.poolDataItemService = poolDataItemService;
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
        if (this.model.getItemId() == null) {
            model.setCreateTime(new Timestamp(System.currentTimeMillis()));
            model.setUser(FrameworkUtil.getLoginUser());
        }
        if (this.model.getMessageScene().getMessageSceneId() == null) {
            this.model.setMessageScene(null);
        }
        if (this.model.getSceneSystem().getSystemId() == null) {
            this.model.setSceneSystem(null);
        }

        return super.edit();
    }


    /**
     *  获取该类别的值
     * @author xuwangcheng
     * @date 2021/1/4 19:18
     * @param
     * @return {@link String}
     */
    public String listNameValue () {
        if (this.poolId == null || this.model.getItemId() == null) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }
        setData(poolDataItemService.queryItemNameValue(poolId, this.model.getItemId()));
        return SUCCESS;
    }

    /**
     *  保存该类别的值
     * @author xuwangcheng
     * @date 2021/1/4 20:15
     * @param
     * @return {@link String}
     */
    public String saveNameValue () {
        Map<String, Object> params = FrameworkUtil.getParametersMap();
        for (String key:params.keySet()) {
            if ("itemId".equals(key) || !NumberUtil.isInteger(key)) {
                continue;
            }

            poolDataValueService.updateItemValue(model.getItemId(), Integer.valueOf(key), ((String[])params.get(key))[0].toString());
        }
        return SUCCESS;
    }

    /**
     *  导出导入模板
     * @author xuwangcheng
     * @date 2021/1/6 11:56
     * @param
     * @return {@link String}
     */
    public String exportValueTemplate () {
        if (poolId == null) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }

        DataPool dataPool = dataPoolService.get(poolId);
        if (dataPool == null) {
            throw new YiException("数据池信息不存在");
        }
        if (dataPool.getItemCount() == 0 || dataPool.getNameCount() == 0) {
            throw new YiException("请先添加数据池变量和数据池类别");
        }

        String path = ExcelPoolDataTemplate.exportDocument(dataPool, CollUtil.newArrayList(dataPool.getDataNames())
                , CollUtil.newArrayList(dataPool.getDataItems()));
        if (StrUtil.isBlank(path)) {
            throw new YiException("导出失败");
        }

        setData(path);

        return SUCCESS;
    }


    /**
     * 导入文件设置数据值
     * @author xuwangcheng
     * @date 2021/1/6 14:25
     * @param
     * @return {@link String}
     */
    public String importItemValue () {
        ExcelPoolDataTemplate.importItemValues(poolId, path);

        return SUCCESS;
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
            if (poolDataItemService.findByName(name, poolId) == null) {
                PoolDataItem poolDataItem = new PoolDataItem();
                poolDataItem.setUser(FrameworkUtil.getLoginUser());
                poolDataItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
                poolDataItem.setDataPool(new DataPool(poolId));
                poolDataItem.setBeforeUseAutoUpdate(SystemConsts.DefaultBooleanIdentify.TRUE.getNumber());
                poolDataItem.setName(name);
                poolDataItem.setMark(name);
                poolDataItemService.save(poolDataItem);
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


    /**
     * 判断名称重复性
     * 新增或者修改状态下均可用
     */
    @Override
    public void checkObjectName() {
        PoolDataItem info = poolDataItemService.findByName(model.getName(), poolId);
        checkNameFlag = (info != null && !info.getItemId().equals(model.getItemId())) ? "重复的名称" : "true";

        if (model.getItemId() == null) {
            checkNameFlag = (info == null) ? "true" : "重复的名称";
        }
    }

    /**
     *  通过请求更新数据池值
     * @author xuwangcheng
     * @date 2021/1/7 15:56
     * @param
     * @return {@link String}
     */
    public String updateValueByRequest () {
        if (model.getItemId() == null) {
            throw new YiException(AppErrorCode.MISS_PARAM);
        }

        poolDataItemService.updateItemValueByRequest(model.getItemId());

        return SUCCESS;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
    }

    public void setExportItemIds(String exportItemIds) {
        this.exportItemIds = exportItemIds;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
