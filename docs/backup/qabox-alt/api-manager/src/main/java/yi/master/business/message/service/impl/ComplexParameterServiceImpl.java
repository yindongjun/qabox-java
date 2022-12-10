package yi.master.business.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yi.master.business.base.service.impl.BaseServiceImpl;
import yi.master.business.message.bean.ComplexParameter;
import yi.master.business.message.dao.ComplexParameterDao;
import yi.master.business.message.service.ComplexParameterService;

import java.util.ArrayList;
import java.util.List;

@Service("complexParameterService")
public class ComplexParameterServiceImpl extends BaseServiceImpl<ComplexParameter> implements ComplexParameterService {
	
	@SuppressWarnings("unused")
	private ComplexParameterDao complexParameterDao;
	
	@Autowired
	public void setComplexParameterDao(ComplexParameterDao complexParameterDao) {
		super.setBaseDao(complexParameterDao);
		this.complexParameterDao = complexParameterDao;
	}

    @Override
    public List<Integer> listIds(Integer parameterId) {
        List<ComplexParameter> parameters = complexParameterDao.findAll("selfParameter.parameterId=" + parameterId);
        List<Integer> ids = new ArrayList<>();
        parameters.stream().forEach(param -> {
            ids.add(param.getId());
        });

        return ids;
    }

    @Override
    public void insert(Integer parameterId, Integer parentId) {
	    String sql = "insert into at_complex_parameter values(null, :parameterId, :parentId, null)";
        complexParameterDao.getSession().createSQLQuery(sql).setInteger("parameterId", parameterId)
                .setInteger("parentId", parentId).executeUpdate();
    }
}
