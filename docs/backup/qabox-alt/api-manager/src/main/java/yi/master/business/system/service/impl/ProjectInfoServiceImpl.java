package yi.master.business.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.bean.PageModel;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.system.dao.ProjectInfoDao;
import yi.master.business.system.service.ProjectInfoService;
import yi.master.business.user.bean.User;
import yi.master.exception.AppErrorCode;
import yi.master.exception.YiException;

import java.util.List;

/**
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  10:22
 */
@Service("projectInfoService")
public class ProjectInfoServiceImpl extends BaseServiceImpl<ProjectInfo> implements ProjectInfoService {

    private ProjectInfoDao projectInfoDao;

    @Autowired
    public void setProjectInfoDao(ProjectInfoDao projectInfoDao) {
        super.setBaseDao(projectInfoDao);
        this.projectInfoDao = projectInfoDao;
    }

    @Override
    public void addUserToProject(Integer userId, Integer projectId) {
        projectInfoDao.addUserToProject(userId, projectId);
    }

    @Override
    public void delUserFromProject(Integer userId, Integer projectId) {
        projectInfoDao.delUserFromProject(userId, projectId);
    }

    @Override
    public PageModel<User> listProjectUsers(Integer projectId, int dataNum, int pageSize, String orderDataName, String orderType, String searchValue, List<List<String>> dataParams, int mode, String... filterCondition) {
        return projectInfoDao.listProjectUsers(projectId, dataNum, pageSize, orderDataName, orderType, searchValue, dataParams, mode, filterCondition);
    }

    @Override
    public List<ProjectInfo> listUserProjects(Integer userId) {
        return projectInfoDao.listUserProjects(userId);
    }

    @Override
    public void delete(int id) {
        // 验证是否有关联的信息，包括接口、报文、场景、测试集、接口Mock、接口探测、接口性能测试、定时任务、测试报告、测试集、组合场景
        String hql = "select count(*) from InterfaceInfo where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from Message where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from MessageScene where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from ComplexScene where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from InterfaceMock where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from InterfaceProbe where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from PerformanceTestConfig where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from AutoTask where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from TestReport where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }
        hql = "select count(*) from TestSet where projectInfo.projectId=" + id;
        if (projectInfoDao.getHqlCount(hql) > 0) {
            throw new YiException(AppErrorCode.PROJECT_CAN_NOT_DELETE);
        }

        super.delete(id);
    }
}
