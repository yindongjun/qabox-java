package yi.master.business.testconfig.dto;

import static yi.master.constant.GlobalVariableConstant.USE_VARIABLE_LEFT_BOUNDARY;
import static yi.master.constant.GlobalVariableConstant.USE_VARIABLE_RIGHT_BOUNDARY;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2021/1/4 19:04
 */
public class ItemNameValueDto {

    private Integer nameId;
    private String useKey;
    private String name;
    private String value;
    private String defaultValue;


    public String getUseKey() {
        return USE_VARIABLE_LEFT_BOUNDARY + this.name + USE_VARIABLE_RIGHT_BOUNDARY;
    }

    public void setUseKey(String useKey) {
        this.useKey = useKey;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getNameId() {
        return nameId;
    }

    public void setNameId(Integer nameId) {
        this.nameId = nameId;
    }
}
