package yi.master.business.testconfig.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.business.user.bean.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import static yi.master.constant.GlobalVariableConstant.USE_VARIABLE_LEFT_BOUNDARY;
import static yi.master.constant.GlobalVariableConstant.USE_VARIABLE_RIGHT_BOUNDARY;

/**
 * 数据池数据name
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 9:57
 */
public class PoolDataName implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * name id
     */
    private Integer id;
    /**
     * 所属数据池
     */
    private DataPool dataPool;
    /**
     * 名称
     */
    private String name;

    /**
     * 使用时的key
     */
    private String useKey;

    /**
     * 备注
     */
    private String mark;
    /**
     * 创建用户
     */
    private User user;
    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 默认值
     */
    private String defaultValue;

    /**
     * 类别对应数据值
     */
    private Set<PoolDataValue> dataValues = new HashSet<>();

    public PoolDataName() {
    }

    public PoolDataName(Integer id) {
        this.id = id;
    }


    public void setUseKey(String useKey) {
        this.useKey = useKey;
    }

    public String getUseKey() {
        return USE_VARIABLE_LEFT_BOUNDARY + this.name + USE_VARIABLE_RIGHT_BOUNDARY;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDataValues(Set<PoolDataValue> dataValues) {
        this.dataValues = dataValues;
    }

    @JSON(serialize = false)
    public Set<PoolDataValue> getDataValues() {
        return dataValues;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public void setDataPool(DataPool dataPool) {
        this.dataPool = dataPool;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "PoolDataName{" +
                "id=" + id +
                ", dataPool=" + dataPool +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
