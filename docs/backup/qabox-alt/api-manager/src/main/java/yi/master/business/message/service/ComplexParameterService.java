package yi.master.business.message.service;

import yi.master.business.base.service.BaseService;
import yi.master.business.message.bean.ComplexParameter;

import java.util.List;

public interface ComplexParameterService extends BaseService<ComplexParameter> {

    /**
     *  查询ids
     * @author xuwangcheng
     * @date 2021/3/8 8:49
     * @param parameterId parameterId
     * @return {@link List}
     */
    List<Integer> listIds(Integer parameterId);

    /**
     *  直接插入
     * @author xuwangcheng
     * @date 2021/3/8 9:15
     * @param parameterId parameterId
     * @param parentId parentId
     * @return {@link boolean}
     */
    void insert(Integer parameterId, Integer parentId);
}
