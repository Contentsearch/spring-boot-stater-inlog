package com.github.iphase2.inlog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.util.Set;

/**
 * @Title: AutoLogProperties
 * @Author iPhase2
 * @Date 2023/6/5 10:45
 * @description:
 */
@RefreshScope
@ConfigurationProperties(prefix = AutoLogProperties.PROPERTIES_NAME)
public class AutoLogProperties {
    /**
     * 配置前缀
     */
    public static final String PROPERTIES_NAME = "auto-log";

    public static final String ENABLE = "enable";
    /**
     * 全路径名或 包路径.* (*为类名)
     */
    private Set<String> filter;

    private boolean printResult =false;


    public boolean isPrintResult() {
        return printResult;
    }

    public void setPrintResult(boolean printResult) {
        this.printResult = printResult;
    }

    /**
     * 是否开启
     */
    private boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Set<String> getFilter() {
        return filter;
    }

    public void setFilter(Set<String> filter) {
        this.filter = filter;
    }
}