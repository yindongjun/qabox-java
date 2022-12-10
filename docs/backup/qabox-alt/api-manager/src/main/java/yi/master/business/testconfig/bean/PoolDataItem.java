package yi.master.business.testconfig.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.business.message.bean.MessageScene;
import yi.master.business.message.bean.TestResult;
import yi.master.business.user.bean.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据池数据类别
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 9:57
 */
public class PoolDataItem implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 池数据类别ID
     */
    private Integer itemId;
    /**
     * 类别名称
     */
    private String name;
    /**
     * 所属数据池
     */
    private DataPool dataPool;
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
     * 通过接口获取，接口必须为get请求，且返回json
     */
    private String requestUrl;

    /**
     * 关联测试场景
     */
    private MessageScene messageScene;

    /**
     * 关联测试场景使用的测试环境
     */
    private BusinessSystem sceneSystem;

    /**
     * 优先使用测试场景获取数据
     */
    private String useMessageScene;

    /**
     * 类别对应数据值
     */
    private Set<PoolDataValue> dataValues = new HashSet<>();

    /**
     * 已设置的值数量
     */
    @FieldNameMapper(fieldPath = "size(dataValues)", ifSearch = false)
    private Integer valueCount;

    /**
     * 使用前自动更新数据，前提是必须配置了requestUrl或者messageScene
     */
    private String beforeUseAutoUpdate;

    /**
     * 请求返回数据的指定JSON_PATH
     */
    private String responseDataJsonPath;

    /**
     * 关联结果集
     */
    private Set<TestResult> results = new HashSet<>();

    public PoolDataItem() {
    }


    @JSON(serialize = false)
    public Set<TestResult> getResults() {
        return results;
    }

    public void setResults(Set<TestResult> results) {
        this.results = results;
    }

    public PoolDataItem(Integer itemId) {
        this.itemId = itemId;
    }

    public void setResponseDataJsonPath(String responseDataJsonPath) {
        this.responseDataJsonPath = responseDataJsonPath;
    }

    public String getResponseDataJsonPath() {
        return responseDataJsonPath;
    }

    public void setBeforeUseAutoUpdate(String beforeUseAutoUpdate) {
        this.beforeUseAutoUpdate = beforeUseAutoUpdate;
    }

    public String getBeforeUseAutoUpdate() {
        return beforeUseAutoUpdate;
    }

    public void setSceneSystem(BusinessSystem sceneSystem) {
        this.sceneSystem = sceneSystem;
    }

    public BusinessSystem getSceneSystem() {
        return sceneSystem;
    }

    public void setMessageScene(MessageScene messageScene) {
        this.messageScene = messageScene;
    }

    public MessageScene getMessageScene() {
        return messageScene;
    }

    public String getUseMessageScene() {
        return useMessageScene;
    }

    public void setUseMessageScene(String useMessageScene) {
        this.useMessageScene = useMessageScene;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setValueCount(Integer valueCount) {
        this.valueCount = valueCount;
    }

    public Integer getValueCount() {
        return this.dataValues.size();
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public void setDataPool(DataPool dataPool) {
        this.dataPool = dataPool;
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
    public Set<PoolDataValue> getDataValues() {
        return dataValues;
    }

    public void setDataValues(Set<PoolDataValue> dataValues) {
        this.dataValues = dataValues;
    }

    @Override
    public String toString() {
        return "PoolDataItem{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                ", dataPool=" + dataPool +
                ", mark='" + mark + '\'' +
                ", createTime=" + createTime +
                ", dataValues=" + dataValues +
                '}';
    }
}
