package yi.master.constant;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;
import yi.master.util.FrameworkUtil;

import javax.servlet.ServletContext;
import java.io.File;

/**
 * 自定义配置变量
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/12/10 16:26
 */
public class CustomSettingVariable {
    public static String GLOBAL_VARIABLE_FILE_SAVE_PATH = FrameworkUtil.getProjectPath() + File.separator + ".." + File.separator + "variableFileResources";
    public static String PT_RESULT_FILE_SAVE_PATH = FrameworkUtil.getProjectPath() + File.separator + ".." + File.separator + "tmp/ptr/";

    public static void setSettingVariable (ServletContext context) {
        String str = checkFolder(context, "globalVariableFileSavePath");
        if (str != null) {
            GLOBAL_VARIABLE_FILE_SAVE_PATH = str;
        }

        if (!FileUtil.exist(GLOBAL_VARIABLE_FILE_SAVE_PATH)) {
            FileUtil.mkdir(GLOBAL_VARIABLE_FILE_SAVE_PATH);
        }

        str = checkFolder(context, "ptResultFileSavePath");
        if (str != null) {
            PT_RESULT_FILE_SAVE_PATH = str;
        }

        if (!FileUtil.exist(PT_RESULT_FILE_SAVE_PATH)) {
            FileUtil.mkdir(PT_RESULT_FILE_SAVE_PATH);
        }
    }

    private static String checkFolder (ServletContext context, String attributeKey) {
        Object str = context.getAttribute(attributeKey);
        if (str == null || StringUtils.isBlank(str.toString())) {
            return null;
        }

        return str.toString();
    }
}
