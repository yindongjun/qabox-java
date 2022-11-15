package io.fluentqa.qabox.bytedance.open.api.v1.response.pay;

import io.fluentqa.qabox.bytedance.common.error.ByteDancePayError;
import io.fluentqa.qabox.bytedance.common.http.IByteDanceResponse;
import lombok.Data;

/**
 * @Authorn
 * @date 2021/06/18
 **/
@Data
public class OrderPayDeleteResponse extends ByteDancePayError implements IByteDanceResponse {

    private static final long serialVersionUID = 8474862751253909508L;

}
