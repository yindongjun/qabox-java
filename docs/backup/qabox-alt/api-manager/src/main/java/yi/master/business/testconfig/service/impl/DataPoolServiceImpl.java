package yi.master.business.testconfig.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.testconfig.bean.DataPool;
import yi.master.business.testconfig.dao.DataPoolDao;
import yi.master.business.testconfig.service.DataPoolService;

/**
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2020/12/30 16:57
 */
@Service("dataPoolService")
public class DataPoolServiceImpl extends BaseServiceImpl<DataPool> implements DataPoolService {

    private DataPoolDao dataPoolDao;

    @Autowired
    public void setDataPoolDao(DataPoolDao dataPoolDao) {
        super.setBaseDao(dataPoolDao);
        this.dataPoolDao = dataPoolDao;
    }
}
