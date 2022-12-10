package yi.master.business.system.dao;

import yi.master.business.base.bean.PageModel;
import yi.master.business.base.dao.BaseDao;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.user.bean.User;

import java.util.List;

/**
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  10:17
 */
public interface ProjectInfoDao extends BaseDao<ProjectInfo> {
    /**
     *  关联用户到对应项目
     * @author xuwangcheng
     * @date 2020/7/29 11:07
     * @param userId userId
     * @param projectId projectId
     * @return
     */
    void addUserToProject (Integer userId, Integer projectId);
    /**
     *  取消关联到项目的用户
     * @author xuwangcheng
     * @date 2020/7/29 11:07
     * @param userId userId
     * @param projectId projectId
     * @return
     */
    void delUserFromProject (Integer userId, Integer projectId);

    /**
     *  分页查询已经给关联或者没有关联指定项目的用户列表
     * @author xuwangcheng
     * @date 2020/7/29 11:22
     * @param projectId projectId
     * @param dataNum dataNum
     * @param pageSize pageSize
     * @param orderDataName orderDataName
     * @param orderType orderType
     * @param searchValue searchValue
     * @param dataParams dataParams
     * @param mode mode
     * @param filterCondition filterCondition
     * @return {@link PageModel}
     */
    PageModel<User> listProjectUsers (Integer projectId, int dataNum, int pageSize, String orderDataName, String orderType
            , String searchValue, List<List<String>> dataParams, int mode, String ...filterCondition);


    /**
     *  查询用户可供选择的项目
     * @author xuwangcheng
     * @date 2020/7/29 11:31
     * @param userId userId
     * @return {@link List}
     */
    List<ProjectInfo> listUserProjects (Integer userId);
}
