package io.fluentqa.qabox.bytedance.open.message.event;

import java.io.Serializable;
import lombok.Data;

/**
 * @Authorn
 * @date 2020/09/23
 **/
@Data
public class ModifyAppIconResult implements Serializable {

    private static final long serialVersionUID = 8668242945522877197L;
    /**
     * 如果被拒，被拒原因
     */
    private String reason;

    /**
     * 0或1，0代表不通过，1代表通过
     */
    private Integer status;
}