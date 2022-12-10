package yi.master.business.testconfig.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.TestResult;
import yi.master.business.testconfig.bean.PoolDataItem;
import yi.master.business.testconfig.bean.PoolDataName;
import yi.master.business.testconfig.bean.TestConfig;
import yi.master.business.testconfig.dao.PoolDataItemDao;
import yi.master.business.testconfig.dto.ItemNameValueDto;
import yi.master.business.testconfig.service.PoolDataItemService;
import yi.master.business.testconfig.service.PoolDataNameService;
import yi.master.business.testconfig.service.PoolDataValueService;
import yi.master.business.testconfig.service.TestConfigService;
import yi.master.constant.MessageKeys;
import yi.master.constant.SystemConsts;
import yi.master.coretest.message.parse.JSONMessageParse;
import yi.master.coretest.message.test.MessageAutoTest;
import yi.master.coretest.message.test.TestMessageScene;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;
import yi.master.util.PracticalUtils;
import yi.master.util.message.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 17:47
 */
@Service("poolDataItemService")
public class PoolDataItemServiceImpl extends BaseServiceImpl<PoolDataItem> implements PoolDataItemService {

    private static final Logger logger = LoggerFactory.getLogger(PoolDataItemServiceImpl.class);

    private PoolDataItemDao poolDataItemDao;
    @Autowired
    private PoolDataValueService poolDataValueService;
    @Autowired
    private PoolDataNameService poolDataNameService;
    @Autowired
    private MessageAutoTest messageAutoTest;
    @Autowired
    private TestConfigService testConfigService;

    @Autowired
    public void setPoolDataItemDao(PoolDataItemDao poolDataItemDao) {
        super.setBaseDao(poolDataItemDao);
        this.poolDataItemDao = poolDataItemDao;
    }

    @Override
    public List<ItemNameValueDto> queryItemNameValue(Integer poolId, Integer itemId) {
        return poolDataItemDao.queryItemNameValue(poolId, itemId);
    }

    @Override
    public PoolDataItem findByName(String name, Integer poolId) {
        return poolDataItemDao.findByName(name, poolId);
    }

    @Override
    public void updateItemValueByRequest(Integer itemId) {
        PoolDataItem poolDataItem = poolDataItemDao.get(itemId);
        if (poolDataItem == null) {
            throw new YiException("该池类别不存在");
        }

        boolean useMessageScene = SystemConsts.DefaultBooleanIdentify.TRUE.getString().equals(poolDataItem.getUseMessageScene());
        boolean requestUrlIsNotBlank = StrUtil.isNotBlank(poolDataItem.getRequestUrl());
        boolean messageSceneNotNull = poolDataItem.getMessageScene() != null;

        if (!requestUrlIsNotBlank && !messageSceneNotNull) {
            throw new YiException("未配置数据请求地址和接口场景");
        }

        String result = "";

        try {
            // 使用messageScene
            if ((useMessageScene && messageSceneNotNull) || (!useMessageScene && !requestUrlIsNotBlank)) {
                if (poolDataItem.getSceneSystem() == null) {
                    throw new YiException("数据池[{}]自动获取数据出错：你必须为接口场景选择一个可用的测试环境", poolDataItem.getDataPool().getName());
                }
                TestConfig testConfig = testConfigService.configByUserId(FrameworkUtil.getLoginUser().getUserId());
                Set<TestMessageScene> testMessageScenes = messageAutoTest.packageRequestObject(poolDataItem.getMessageScene(), testConfig, poolDataItem.getSceneSystem(), null);
                if (CollUtil.isEmpty(testMessageScenes)) {
                    throw new YiException("数据池[{}]自动获取数据出错：通过接口获取数据出错：无法解析要使用的接口场景", poolDataItem.getDataPool().getName());
                }

                TestResult result1 = messageAutoTest.singleTest(new ArrayList<>(testMessageScenes).get(0), null);
                if (!MessageKeys.TestRunStatus.SUCCESS.getCode().equals(result1.getRunStatus())) {
                    throw new YiException("数据池[{}]自动获取数据出错:{}", poolDataItem.getDataPool().getName(), result1.getMark());
                }

                result = result1.getResponseMessage();
            }

            // 使用requestUrl
            if ((!useMessageScene && requestUrlIsNotBlank) || (useMessageScene && !messageSceneNotNull)) {
                result = PracticalUtils.doGetHttpRequest(poolDataItem.getRequestUrl());
            }
        } catch (Exception e) {
            if (e instanceof YiException) {
                throw e;
            } else {
                throw new YiException("数据池[{}]接口请求出错:{}", poolDataItem.getDataPool().getName(), e.getMessage());
            }
        }

        if (StrUtil.isBlank(result) || !JSONMessageParse.getInstance().messageFormatValidation(result)) {
            throw new YiException("数据池[{}]接口请求出错:返回数据格式不正确，必须为JSON格式!", poolDataItem.getDataPool().getName());
        }

        logger.info(StrUtil.format("通过接口获取数据池[{}]数据，接口返回内容为:{}", poolDataItem.getDataPool().getName(), result));

        // 是否配置了指定的JSONPATH？
        if (StrUtil.isNotBlank(poolDataItem.getResponseDataJsonPath())) {
            result = JsonUtil.getObjectByJson(result, poolDataItem.getResponseDataJsonPath(), JsonUtil.TypeEnum.map);
        }
        if (StrUtil.isBlank(result)) {
            throw new YiException("数据池[{}]接口请求出错:JSON_PATH配置有误,无法获取指定节点[{}]下的数据", poolDataItem.getDataPool().getName()
                    , poolDataItem.getResponseDataJsonPath());
        }

        JSONObject jsonObject = JSONObject.parseObject(result);

        for (String key:jsonObject.keySet()) {
            if (jsonObject.get(key) == null) {
                continue;
            }

            PoolDataName dataName = poolDataNameService.findByName(key, poolDataItem.getDataPool().getPoolId());
            if (dataName != null) {
                poolDataValueService.updateItemValue(itemId, dataName.getId(), jsonObject.getString(key));
            }
        }
    }

    @Override
    public List<PoolDataItem> listByPoolId(Integer poolId) {
        return poolDataItemDao.listByPoolId(poolId);
    }
}
