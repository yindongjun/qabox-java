package io.fluentqa.alt.proxyee.intercept;

import io.fluentqa.alt.proxyee.util.ProtoUtil;

/**
 * @Author
 * @Description 用于拦截隧道请求，在代理服务器与目标服务器连接前
 * @Date 2019/11/4 9:57
 */
public interface HttpTunnelIntercept {
    void handle(ProtoUtil.RequestProto requestProto);
}
