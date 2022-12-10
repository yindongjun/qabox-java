package yi.master.business.testconfig.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.business.user.bean.User;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 数据池数据value
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 9:58
 */
public class PoolDataValue implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * value id
     */
    private Integer id;
    /**
     * value值
     */
    private String value;
    /**
     * 对应name
     */
    private PoolDataName poolDataName;
    /**
     * 创建用户
     */
    private User user;
    /**
     * 创建时间
     */
    private Timestamp createTime;
    /**
     * 所属池数据类别
     */
    private PoolDataItem poolDataItem;
    /**
     * 备注信息
     */
    private String mark;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PoolDataName getPoolDataName() {
        return poolDataName;
    }

    public void setPoolDataName(PoolDataName poolDataName) {
        this.poolDataName = poolDataName;
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

    public PoolDataItem getPoolDataItem() {
        return poolDataItem;
    }

    public void setPoolDataItem(PoolDataItem poolDataItem) {
        this.poolDataItem = poolDataItem;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "PoolDataValue{" +
                "id=" + id +
                ", value='" + value + '\'' +
                ", poolDataName=" + poolDataName +
                ", createTime=" + createTime +
                ", poolDataItem=" + poolDataItem +
                ", mark='" + mark + '\'' +
                '}';
    }
}
