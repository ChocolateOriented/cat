package com.cat.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * 应用上下文对象
 */
public class CommonRequestContext {

    private static final ThreadLocal<CommonRequestContext> APPLICATION_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    private static final RequestInfoFetcher requestInfoFetcher = new RequestInfoFetcher();

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 当前用户id
     */
    private String currentUserId;

    /**
     * token
     */
    private String accessToken;

    /**
     * 客户端版本
     */
    private String clientVersion;

    /**
     * 设备号
     */
    private String deviceId;

    public static CommonRequestContext getInstance() {
        return APPLICATION_CONTEXT_THREAD_LOCAL.get();
    }

    private CommonRequestContext(String clientId, String currentUserId, String accessToken,
        String clientVersion, String deviceId) {
        this.clientId = clientId;
        this.currentUserId = currentUserId;
        this.accessToken = accessToken;
        this.clientVersion = clientVersion;
        this.deviceId = deviceId;
    }

    public static CommonRequestContext newInstance(HttpServletRequest request) {
        CommonRequestContext commonRequestContext = new CommonRequestContext(
            requestInfoFetcher.getClientId(request),
            requestInfoFetcher.getAccountCode(request),
            requestInfoFetcher.getAccessToken(request),
            requestInfoFetcher.getClientVersion(request),
            requestInfoFetcher.getDeviceId(request));
        setContext(commonRequestContext);
        return commonRequestContext;
    }

    public static void clean() {
        APPLICATION_CONTEXT_THREAD_LOCAL.remove();
    }

    private static void setContext(CommonRequestContext commonRequestContext) {
        APPLICATION_CONTEXT_THREAD_LOCAL.set(commonRequestContext);
    }

    public String getClientId() {
        return clientId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public String getDeviceId() {
        return deviceId;
    }


}