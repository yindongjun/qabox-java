package yi.master.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import yi.master.business.system.dao.GlobalSettingDao;
import yi.master.constant.SystemConsts;
import yi.master.util.FrameworkUtil;
import yi.master.util.cache.CacheUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统升级
 * @author xuwangcheng14@163.com
 * @date 2019/11/24 1:34
 */

public class VersionUpdateUtil {
    private static final Logger logger = LoggerFactory.getLogger(VersionUpdateUtil.class);
    /**
     * 所有版本列表
     */
    private static final List<String> ALL_VERSION_LIST = new ArrayList<>();

    /**
     * 是否需要重启
     */
    private static Boolean isNeedRestart = false;
    static {
        ALL_VERSION_LIST.add("0.1.1beta");
        ALL_VERSION_LIST.add("0.1.2beta");
        ALL_VERSION_LIST.add("0.1.3beta");
        ALL_VERSION_LIST.add("0.1.4beta");
        ALL_VERSION_LIST.add("0.1.5beta");
        ALL_VERSION_LIST.add("0.2.0beta");
        ALL_VERSION_LIST.add("0.2.1beta");
        ALL_VERSION_LIST.add("0.2.2beta");
        ALL_VERSION_LIST.add("1.0.0");
        ALL_VERSION_LIST.add("1.0.1");
        ALL_VERSION_LIST.add("1.0.2");
        ALL_VERSION_LIST.add("1.0.3");
        ALL_VERSION_LIST.add("1.0.4");
        ALL_VERSION_LIST.add("1.0.5");
        ALL_VERSION_LIST.add("1.1.0");
        ALL_VERSION_LIST.add("1.1.1");
        ALL_VERSION_LIST.add("1.1.2");
        ALL_VERSION_LIST.add("1.1.3");
        ALL_VERSION_LIST.add("1.1.4");
    }

    /**
     *  升级系统
     * @author xuwangcheng
     * @date 2019/11/24 1:49
     * @param databaseVersion databaseVersion
     * @return
     */
    public static void updateVersion (String databaseVersion) {
        if (StringUtils.isBlank(databaseVersion) || SystemConsts.VERSION.equals(databaseVersion)) {
            return;
        }

        logger.info("当前代码版本为v" + SystemConsts.VERSION + ",当前数据库版本为v" + databaseVersion);
        logger.info("开始升级系统...");

        PlatformTransactionManager dstManager = (PlatformTransactionManager) FrameworkUtil.getSpringBean(PlatformTransactionManager.class);
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transaction = dstManager.getTransaction(def);

        try {
            boolean isBegin = false;
            GlobalSettingDao settingDao = (GlobalSettingDao) FrameworkUtil.getSpringBean("globalSettingDao");
            for (String version:ALL_VERSION_LIST) {
                if (isBegin) {
                    updateSql(version, settingDao);
                    logger.info("成功升级到版本v" + version + "!");
                }
                if (version.equals(databaseVersion)) {
                    isBegin = true;
                }
            }
            settingDao.updateSetting(SystemConsts.GLOBAL_SETTING_VERSION, SystemConsts.VERSION);
            CacheUtil.updateGlobalSettingValue(SystemConsts.GLOBAL_SETTING_VERSION, SystemConsts.VERSION);
            dstManager.commit(transaction);
        } catch (Exception e) {
            logger.error("版本升级出错，请联系作者xuwangcheng14@163.com寻求帮助!", e);
            dstManager.rollback(transaction);

            throw new RuntimeException(e);
        }

        if (isNeedRestart) {
            logger.info("版本升级成功！请重启Tomcat服务器...");
        }
    }

    /**
     *  更新升级SQL
     * @author xuwangcheng
     * @date 2019/11/24 2:18
     * @param version version
     * @param settingDao settingDao
     * @return
     */
    private static void updateSql (String version, GlobalSettingDao settingDao) {
        File file = null;
        try {
            ClassPathResource classPathResource = new ClassPathResource("update/" + version);
            file = classPathResource.getFile();
        } catch (Exception e) {}

        if (file != null && file.exists()) {
            logger.info("正在升级到版本v" + version + "...");
            List<String> sqls = FileUtil.readLines(file, "utf-8");
            if (CollUtil.isNotEmpty(sqls)) {
                for (String sql:sqls) {
                    if (StringUtils.isNotBlank(sql)) {
                        //isNeedRestart = true;

                        logger.info("Update System execute SQL:" + sql);
                        settingDao.getSession().createSQLQuery(sql).executeUpdate();
                    }
                }
            }
        }

    }

    public static Boolean getIsNeedRestart() {
        return isNeedRestart;
    }
}
