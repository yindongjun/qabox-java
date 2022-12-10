package yi.master.coretest.message.process.config;

/**
 * @author xuwangcheng14@163.com
 * @version 1.0.0
 * @description
 * @date 2021/1/20  17:01
 */
public class DemoMessageProcessParameter {
    private String params1;
    private String params2;

    public String getParams1() {
        return params1;
    }

    public void setParams1(String params1) {
        this.params1 = params1;
    }

    public String getParams2() {
        return params2;
    }

    public void setParams2(String params2) {
        this.params2 = params2;
    }

    @Override
    public String toString() {
        return "DemoMessageProcessParameter{" +
                "params1='" + params1 + '\'' +
                ", params2='" + params2 + '\'' +
                '}';
    }
}
