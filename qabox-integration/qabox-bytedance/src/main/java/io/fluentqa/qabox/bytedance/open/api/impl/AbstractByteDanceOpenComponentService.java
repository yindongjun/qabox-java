package io.fluentqa.qabox.bytedance.open.api.impl;

import cn.hutool.core.util.StrUtil;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceErrorException;
import io.fluentqa.qabox.bytedance.common.error.ByteDanceErrorMsgEnum;
import io.fluentqa.qabox.bytedance.common.error.IByteDanceError;
import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenComponentService;
import io.fluentqa.qabox.bytedance.open.api.IByteDanceOpenService;
import io.fluentqa.qabox.bytedance.open.api.IExecutable;
import io.fluentqa.qabox.bytedance.open.api.IRetryableExecutor;
import com.google.common.collect.Multimap;
import java.util.concurrent.locks.Lock;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

/**
 * @Authorn
 * @date 2020/09/18
 **/
@Slf4j
public abstract class AbstractByteDanceOpenComponentService implements IByteDanceOpenComponentService, IRetryableExecutor {

    private IByteDanceOpenService byteDanceOpenService;

    public AbstractByteDanceOpenComponentService(IByteDanceOpenService byteDanceOpenService) {
        this.byteDanceOpenService = byteDanceOpenService;
    }

    @Override
    public IByteDanceOpenService getByteDanceOpenService() {
        return byteDanceOpenService;
    }

    @Override
    public String get(String url) {
        return get(url, String.class);
    }

    @Override
    public <T> T get(String url, Class<T> t) {
        return retryableExecuteRequest(
            (url2, headers, request2, t2) -> {
                return getInternal(url2, t2);
            },
            url,null, null, t);
    }

    private <T> T getInternal(String url, Class<T> t) {
        return executeRequest((uriWithCommonParam, headers, request, t2)->{
            return getByteDanceOpenService().getByteDanceHttpClient().get(uriWithCommonParam, t2);
        }, url, null, null, t);
    }

    @Override
    public <T> T post(String url, Object request, Class<T> t) {
        return retryableExecuteRequest(
            (url2, headers, request2, t2) -> postInternal(url2, request2, t2),
            url, null, request, t);
    }

    private <T> T postInternal(String url, Object request, Class<T> t) {
        return executeRequest((uriWithCommonParam, headers2, request2, t2)->{
            return getByteDanceOpenService().getByteDanceHttpClient().post(uriWithCommonParam, request2, t2);
        }, url, null, request, t);
    }

    @Override
    public <T> T postWithHeaders(String url, Multimap<String,String> headers, Object request, Class<T> t){
        return retryableExecuteRequest((url2, headers2, request2, t2) -> postWithHeadersInternal(url2, headers2, request2, t2),
            url, headers, request, t);
    }

    private <T> T postWithHeadersInternal(String url, Multimap<String,String> headers, Object request, Class<T> t){
        return executeRequest((uriWithCommonParam, headers2, request2, t2)->{
            return getByteDanceOpenService().getByteDanceHttpClient().postWithHeaders(uriWithCommonParam, headers2, request2, t2);
        }, url, headers, request, t);
    }

    @Override
    public boolean shouldRetry(IByteDanceError error) {
        return shouldExpireComponentAccessToken(error)
            || ByteDanceErrorMsgEnum.CODE_40000.getCode() == error.errorCode();
    }


    /**
     * 调用字节跳动服务器接口
     * 统一在url上添加component_access_token 和 component_appid参数
     * @param executable
     * @param url
     * @param request
     * @param t
     * @param <T>
     * @return
     */
    private <T> T executeRequest(IExecutable<T> executable, String url, Multimap<String,String> headers, Object request, Class<T> t){
        String componentAccessToken = getComponentAccessToken(false);
        String componentAppId = byteDanceOpenService.getByteDanceOpenConfigStorage().getComponentAppId();
        String uriWithCommonParam = url + (url.contains("?") ? "&" : "?") + "component_access_token=" + componentAccessToken + "&component_appid=" + componentAppId;
        try {
            return executable.execute(uriWithCommonParam, headers, request, t);
        } catch (ByteDanceErrorException e) {
            IByteDanceError error = e.getError();
            /*
             * 发生以下情况时尝试刷新access_token
             * 40009 第三方平台AccessToken已过期
             * 40010 第三方平台AccessToken不正确
             */
            if (shouldExpireComponentAccessToken(error)) {
                // 强制设置componentAccessToken过期，这样在下一次请求里就会刷新componentAccessToken
                Lock lock = byteDanceOpenService.getByteDanceOpenConfigStorage().getComponentAccessTokenLock();
                lock.lock();
                try {
                    if (StrUtil.equals(componentAccessToken, byteDanceOpenService.getByteDanceOpenConfigStorage().getComponentAccessToken())) {
                        byteDanceOpenService.getByteDanceOpenConfigStorage().expireComponentAccessToken();
                    }
                } catch (Exception ex) {
                    byteDanceOpenService.getByteDanceOpenConfigStorage().expireComponentAccessToken();
                } finally {
                    lock.unlock();
                }
            }
            if (!error.checkSuccess()) {
                log.error("\n【请求地址】: {}\n【错误信息】: {}", url, e.getError());
                throw new ByteDanceErrorException(error, e);
            }
            return null;
        }
    }

    /**
     * 是否应该让保存的ComponentAccessToken过期
     * @param error
     * @return
     */
    private boolean shouldExpireComponentAccessToken(IByteDanceError error){
        return error.errorCode() == ByteDanceErrorMsgEnum.CODE_40009.getCode()
            || error.errorCode() == ByteDanceErrorMsgEnum.CODE_40010.getCode();
    }

    @Override
    public Logger getLogger() {
        return log;
    }
}
