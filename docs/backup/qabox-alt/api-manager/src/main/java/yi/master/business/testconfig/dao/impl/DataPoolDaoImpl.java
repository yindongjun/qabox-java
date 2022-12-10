package yi.master.business.testconfig.dao.impl;

import org.springframework.stereotype.Repository;
import yi.master.business.base.dao.impl.BaseDaoImpl;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.dao.DataPoolDao;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 14:16
 */
@Repository("dataPoolDao")
public class DataPoolDaoImpl extends BaseDaoImpl<DataPool> implements DataPoolDao {

}
