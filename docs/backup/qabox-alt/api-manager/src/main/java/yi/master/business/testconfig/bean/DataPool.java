package yi.master.business.testconfig.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.user.bean.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据池
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 9:53
 */
public class DataPool implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 池ID
     */
    private Integer poolId;
    /**
     * 池名称
     */
    private String name;
    /**
     * 备注信息
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
     * 池数据类别
     */
    private Set<PoolDataItem> dataItems = new HashSet<>();
    /**
     * 池数据名称
     */
    private Set<PoolDataName> dataNames = new HashSet<>();

    /**
     * 关联测试配置
     */
    private Set<TestConfig> testConfigs = new HashSet<>();

    /**
     * 所属项目
     */
    private ProjectInfo projectInfo;

    @FieldNameMapper(fieldPath = "size(dataNames)", ifSearch = false)
    private Integer nameCount;

    @FieldNameMapper(fieldPath = "size(dataItems)", ifSearch = false)
    private Integer itemCount;

    public DataPool(Integer poolId) {
        this.poolId = poolId;
    }

    public DataPool() {
    }


    @JSON(serialize = false)
    public Set<TestConfig> getTestConfigs() {
        return testConfigs;
    }

    public void setTestConfigs(Set<TestConfig> testConfigs) {
        this.testConfigs = testConfigs;
    }

    public Integer getNameCount() {
        return this.dataNames.size();
    }

    public void setNameCount(Integer nameCount) {
        this.nameCount = nameCount;
    }

    public Integer getItemCount() {
        return this.dataItems.size();
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getPoolId() {
        return poolId;
    }

    public void setPoolId(Integer poolId) {
        this.poolId = poolId;
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

    @JSON(serialize = false)
    public Set<PoolDataItem> getDataItems() {
        return dataItems;
    }

    public void setDataItems(Set<PoolDataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @JSON(serialize = false)
    public Set<PoolDataName> getDataNames() {
        return dataNames;
    }

    public void setDataNames(Set<PoolDataName> dataNames) {
        this.dataNames = dataNames;
    }

    public ProjectInfo getProjectInfo() {
        return projectInfo;
    }

    public void setProjectInfo(ProjectInfo projectInfo) {
        this.projectInfo = projectInfo;
    }

    @Override
    public String toString() {
        return "DataPool{" +
                "poolId=" + poolId +
                ", name='" + name + '\'' +
                ", mark='" + mark + '\'' +
                ", createTime=" + createTime +
                ", dataItems=" + dataItems +
                ", dataNames=" + dataNames +
                ", projectInfo=" + projectInfo +
                '}';
    }
}
