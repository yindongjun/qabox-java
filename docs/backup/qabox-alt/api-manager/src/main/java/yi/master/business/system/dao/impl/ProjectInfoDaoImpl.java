package yi.master.business.system.dao.impl;

import org.springframework.stereotype.Repository;
import yi.master.business.base.bean.PageModel;
import yi.master.business.base.dao.impl.BaseDaoImpl;
import yi.master.business.message.bean.InterfaceInfo;
import yi.master.business.system.bean.ProjectInfo;
import yi.master.business.system.dao.ProjectInfoDao;
import yi.master.business.system.enums.ProjectStatus;
import yi.master.business.user.bean.User;
import yi.master.constant.SystemConsts;

import java.util.List;

/**
 * 项目信息
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2020/7/28  10:18
 */
@Repository("projectInfoDao")
public class ProjectInfoDaoImpl extends BaseDaoImpl<ProjectInfo> implements ProjectInfoDao {


    @Override
    public void addUserToProject(Integer userId, Integer projectId) {
        String sql = "insert into at_project_user (project_id, user_id) values (:projectId, :userId)";
        getSession().createSQLQuery(sql).setInteger("projectId", projectId).setInteger("userId", userId).executeUpdate();
    }

    @Override
    public void delUserFromProject(Integer userId, Integer projectId) {
        String sql = "delete from at_project_user where user_id = :userId and project_id = :projectId";
        getSession().createSQLQuery(sql).setInteger("projectId", projectId).setInteger("userId", userId).executeUpdate();
    }

    @Override
    public PageModel<User> listProjectUsers(Integer projectId, int dataNum, int pageSize, String orderDataName, String orderType, String searchValue, List<List<String>> dataParams, int mode, String... filterCondition) {
        PageModel<User> pm = new PageModel<>(orderDataName, orderType, searchValue, dataParams, dataNum, pageSize);
        StringBuilder hql = new StringBuilder("from User u1 where");
        //mode=0 查询存在的 mode=1查询不存在的
        hql.append(mode == 1 ? " not" : "");

        hql.append(" exists (select 1 from User u2 join u2.projects s "
                + "where s.projectId=" + projectId + " and u1.userId=u2.userId) ");
        LOGGER.info("The query HQL String: \n" + hql.toString());
        pm.setRecordCount(getHqlCount("select count(u1) " + hql.toString()));

        //增加搜索条件
        if (searchValue != "") {
            hql.append(" and (");
            int i = 1;
            for (List<String> ss : dataParams) {
                i++;
                String columnName = ss.get(0);

                if (ss.size() == 1) {
                    hql.append(columnName + " like '%" + searchValue + "%'");
                }

                if (ss.size() > 1) {
                    for (int m = 1;m < ss.size();m ++) {
                        hql.append(columnName + " like '%" + ss.get(m) + "%'");
                        if (m + 1 < ss.size()) {
                            hql.append(" or ");
                        }
                    }
                }

                if (i <= dataParams.size()) {
                    hql.append(" or ");
                }
            }
            hql.append(")");
        }

        //增加自定义的条件
        if (filterCondition != null && filterCondition.length > 0) {
            hql.append(" and ");

            int i = 1;
            for (String s : filterCondition) {
                hql.append(s);
                i++;
                if (i <= filterCondition.length) {
                    hql.append(" and ");
                }
            }
        }

        LOGGER.info("The query HQL String: \n" + hql.toString());
        pm.setFilteredCount(getHqlCount("select count(u1) " + hql.toString()));

        //增加排序
        if (!orderDataName.isEmpty()) {
            hql.append(" order by " + orderDataName + " " + orderType);
        }

        LOGGER.info("The query HQL String: \n" + hql.toString());

        pm.setDatas(getSession().createQuery(hql.toString())
                .setFirstResult(dataNum)
                .setMaxResults(pageSize)
                .setCacheable(true).list());

        return pm;
    }

    @Override
    public List<ProjectInfo> listUserProjects(Integer userId) {
        String hql = "from ProjectInfo p1 where (exists (select 1 from ProjectInfo p2 join p2.users u " +
                "where u.userId=:userId and p1.projectId=p2.projectId) or p1.createUser.userId=:userId or p1.projectId=:defaultProjectId) and p1.status!=" + ProjectStatus.DISABLED.getStatus();
        LOGGER.info("The query HQL String: \n" + hql.toString());

        return getSession().createQuery(hql).setInteger("userId", userId).setInteger("defaultProjectId", SystemConsts.DefaultObjectId.DEFAULT_PROJECT_ID.getId()).setCacheable(true).list();
    }
}
