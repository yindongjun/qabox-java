package yi.master.business.system.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import yi.master.business.base.action.BaseAction;
import yi.master.business.base.bean.PageModel;
import yi.master.business.base.bean.PageReturnJSONObject;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.message.service.InterfaceInfoService;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.system.service.ProjectInfoService;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;
import yi.master.util.FrameworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目信息
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  10:30
 */
@Controller
@Scope("prototype")
public class ProjectInfoAction extends BaseAction<ProjectInfo> {
    private static final long serialVersionUID = 1L;

    private ProjectInfoService projectInfoService;
    @Autowired
    private InterfaceInfoService interfaceInfoService;

    /**
     * (1)、添加还是删除用户,0-删除 1-增加<br>
     * (2)、查询被关联到项目或者没有关联到项目的用户列表,0-已被关联到项目的用户  1-没有被关联到项目的用户<br>
     */
    private String mode;
    private Integer userId;
    private Integer userProjectId;
    private String copyParams;
    private Integer interfaceId;

    @Autowired
    public void setProjectInfoService(ProjectInfoService projectInfoService) {
        super.setBaseService(projectInfoService);
        this.projectInfoService = projectInfoService;
    }

    @Override
    public String[] prepareList() {
        List<String> conditions = new ArrayList<String>();
        conditions.add("t.projectId!=" + SystemConsts.DefaultObjectId.DEFAULT_PROJECT_ID.getId());
        this.filterCondition = conditions.toArray(new String[0]);
        return this.filterCondition;
    }


    @Override
    public String edit() {
        if (model.getProjectId() == null) {
            model.setCreateUser(FrameworkUtil.getLoginUser());
        }
        return super.edit();
    }

    /**
     *  新增用户到项目或者删除用户
     * @author xuwangcheng
     * @date 2020/7/29 15:05
     * @param
     * @return {@link String}
     */
    public String addOrDelUser () {
        // 关联用户到项目
        if ("1".equals(mode)) {
            projectInfoService.addUserToProject(userId == null ? id : userId, model.getProjectId());
        }

        if ("0".equals(mode)) {
            projectInfoService.delUserFromProject(userId == null ? id : userId, model.getProjectId());
        }

        return SUCCESS;
    }

    /**
     *  获取当前项目关联的或者没有关联的用户
     * @author xuwangcheng
     * @date 2020/7/29 15:13
     * @param
     * @return {@link String}
     */
    public String queryUsers () {
        Map<String,Object> dt = FrameworkUtil.getDTParameters(User.class);
        PageModel<User> pm = projectInfoService.listProjectUsers(userProjectId, start, length
                ,(String)dt.get("orderDataName"),(String)dt.get("orderType")
                ,(String)dt.get("searchValue"),(List<List<String>>)dt.get("dataParams"), Integer.parseInt(mode));

        jsonObject = new PageReturnJSONObject(draw, pm.getRecordCount(), pm.getFilteredCount());
        jsonObject.data(processListData(pm.getDatas()));

        return SUCCESS;
    }

    /**
     *  查询包含在项目的接口或者不包含的
     * @author xuwangcheng
     * @date 2020/8/3 10:56
     * @param
     * @return {@link String}
     */
    public String queryInterfaces () {
        Map<String,Object> dt = FrameworkUtil.getDTParameters(InterfaceInfo.class);
        String[] querys = new String[]{ "projectInfo.projectId" + ("1".equals(mode) ? "!=" : "=") + userProjectId };
        PageModel<InterfaceInfo> pm = interfaceInfoService.findByPager(start, length
                ,(String)dt.get("orderDataName"),(String)dt.get("orderType")
                ,(String)dt.get("searchValue"),(List<List<String>>)dt.get("dataParams")
                , querys);

        jsonObject = new PageReturnJSONObject(draw, pm.getRecordCount(), pm.getFilteredCount());
        jsonObject.data(pm.getDatas());

        return SUCCESS;
    }

    /**
     *  项目移动接口，复制接口，删除接口操作
     * @author xuwangcheng
     * @date 2020/8/3 14:42
     * @param
     * @return {@link String}
     */
    public String moveOrCopyOrDelInterface () {
        if ("0".equals(mode)) {
            interfaceInfoService.delete(interfaceId);
        }
        if ("1".equals(mode)) {
            throw new YiException(AppErrorCode.ILLEGAL_HANDLE.getCode(), "该功能还未完成，敬请期待或者去群里催催作者！");
        }
        return SUCCESS;
    }


    /**
     *  查询用户拥有的项目
     * @author xuwangcheng
     * @date 2020/7/29 16:11
     * @param
     * @return {@link String}
     */
    public String listUserProjects () {
        setData(projectInfoService.listUserProjects(FrameworkUtil.getLoginUser().getUserId()));
        return SUCCESS;
    }

    /*******************************************************************************************************************************/
    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setUserProjectId(Integer userProjectId) {
        this.userProjectId = userProjectId;
    }

    public void setCopyParams(String copyParams) {
        this.copyParams = copyParams;
    }

    public void setInterfaceId(Integer interfaceId) {
        this.interfaceId = interfaceId;
    }
}
