package yi.master.util;

import java.util.HashMap;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/9 16:14
 */
public class ParameterMap extends HashMap<String, Object> {

    @Override
    public ParameterMap put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
