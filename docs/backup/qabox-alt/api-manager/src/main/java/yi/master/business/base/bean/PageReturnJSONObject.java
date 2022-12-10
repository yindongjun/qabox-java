package yi.master.business.base.bean;

/**
 * 分页返回对象
 * @author xuwangcheng
 * @version 1.0.0
 * @description
 * @date 2019/9/4 19:08
 */
public class PageReturnJSONObject extends ReturnJSONObject {
    private static final long serialVersionUID = 1L;

    private Integer draw;
    private Integer recordsTotal;
    private Integer recordsFiltered;

    public PageReturnJSONObject () {
        super();
    }

    public PageReturnJSONObject (Integer draw, Integer recordsTotal, Integer recordsFiltered) {
        super();
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }
}
