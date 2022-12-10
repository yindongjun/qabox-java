package yi.master.util;

import java.sql.ResultSet;

/**
 * 自定义的结果集处理
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/1/2  22:04
 */
public interface CustomResultSetOperator {
    /**
     *  处理执行SQL返回的结果集
     * @author xuwangcheng
     * @date 2020/1/2 22:06
     * @param rs rs
     * @return {@link String}
     */
    String handle(ResultSet rs) throws Exception;
}
