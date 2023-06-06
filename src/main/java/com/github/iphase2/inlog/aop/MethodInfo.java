package com.github.iphase2.inlog.aop;

/**
 * 接口信息
 *
 * @Title: MethodInfo
 * @Author iPhase2
 * @Date 2023/6/5 17:23
 * @description:
 */
public class MethodInfo {

    /**
     * 接口全路径名
     */
    private String interfaceDesc;
    /**
     * 返回结果
     */
    private Object result;
    /**
     * 目标接口参数
     */
    private Object param;

    /**
     * queryString
     */
    private String queryString;
    /**
     * 源URL
     */
    private String originUrl;

    /**
     * 请求方法的类型
     */
    private String method;
    /**
     * 返回的错误信息
     */
    private String errorMsg;
    /**
     * 执行耗时
     */
    private long consumed;
    /**
     * 目标接口信息
     */
    private String targetDetail;

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getTargetDetail() {
        return targetDetail;
    }

    public void setTargetDetail(String targetDetail) {
        this.targetDetail = targetDetail;
    }

    public long getConsumed() {
        return consumed;
    }

    public void setConsumed(long consumed) {
        this.consumed = consumed;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getInterfaceDesc() {
        return interfaceDesc;
    }

    public void setInterfaceDesc(String interfaceDesc) {
        this.interfaceDesc = interfaceDesc;
    }


    public void setResult(String result) {
        this.result = result;
    }


    public void setParam(String param) {
        this.param = param;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }
}
