package yi.master.business.system.bean;

import org.apache.struts2.json.annotations.JSON;
import yi.master.annotation.FieldNameMapper;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.user.bean.User;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 项目信息
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  9:16
 */
public class ProjectInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer projectId;
    private String projectName;
    private String details;
    private String status;
    private User createUser;
    private Timestamp createTime;
    private String mark;

    private Set<User> users = new HashSet<>();
    private Set<InterfaceInfo> interfaces = new HashSet<>();

    @FieldNameMapper(fieldPath="size(users)", ifSearch=false)
    private Integer userNum;
    @FieldNameMapper(fieldPath="size(interfaces)", ifSearch = false)
    private Integer interfaceNum;

    public ProjectInfo() {
    }

    public ProjectInfo(Integer projectId) {
        this.projectId = projectId;
    }

    public ProjectInfo(Integer projectId, String projectName, String details, String status, User createUser, Timestamp createTime, String mark) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.details = details;
        this.status = status;
        this.createUser = createUser;
        this.createTime = createTime;
        this.mark = mark;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @JSON(serialize = false)
    public Set<User> getUsers() {
        return users;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getUserNum() {
        return this.users.size();
    }

    public void setInterfaceNum(Integer interfaceNum) {
        this.interfaceNum = interfaceNum;
    }

    public Integer getInterfaceNum() {
        return this.interfaces.size();
    }

    public void setInterfaces(Set<InterfaceInfo> interfaces) {
        this.interfaces = interfaces;
    }

    @JSON(serialize = false)
    public Set<InterfaceInfo> getInterfaces() {
        return interfaces;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", details='" + details + '\'' +
                ", status='" + status + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", mark='" + mark + '\'' +
                '}';
    }
}
