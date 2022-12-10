package yi.master.business.base.dto;


import java.io.Serializable;

public class ParseMessageToNodesOutDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Object data;
    private Integer rootPid;
    private String error;

    public ParseMessageToNodesOutDTO(Object data, Integer rootPid, String error) {
        this.data = data;
        this.rootPid = rootPid;
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getRootPid() {
        return rootPid;
    }

    public void setRootPid(Integer rootPid) {
        this.rootPid = rootPid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
