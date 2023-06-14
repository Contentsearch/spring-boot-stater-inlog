package com.github.iphase2.inlog.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.iphase2.inlog.config.AutoLogProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 通过注解，固定配置来开启
 *
 * @Title: LogAspect
 * @Author iPhase2
 * @Date 2023/6/5 9:34
 * @description: 日志切面
 */
@Slf4j
@Aspect
public class LogAspect {


    private final AutoLogProperties logProperties;


    /**
     * 以自定义 @PrintlnLog 注解作为切面入口
     */
    public LogAspect(AutoLogProperties logProperties) {
        this.logProperties = logProperties;
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.RequestMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping)" +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void PrintlnLog() {
    }

    /**
     * @param joinPoint
     * @description 切面方法入参日志打印
     */
    @Around("PrintlnLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        long begin = System.currentTimeMillis();
        if (!matchRequest(getSignature(joinPoint).getDeclaringTypeName()) || !logProperties.isEnable()) {
            if (log.isDebugEnabled()) {
                log.debug("==== >>>>[doBefore]: unMatch...");
            }
            try {
                return joinPoint.proceed();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }

        String declaringTypeName = getSignature(joinPoint).getDeclaringTypeName();
        // 获取目标类的全路径名
        String methodName = getSignature(joinPoint).getName();
        // 判断当前方法是否需要记录日志
        String methodInfo = declaringTypeName + "." + methodName;
        ServletRequestAttributes attributes = null;
        HttpServletRequest request = null;
        MethodInfo info = new MethodInfo();
        try {
            attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            request = attributes.getRequest();
            info.setQueryString(request.getQueryString());
        } catch (Exception e) {
            log.error("===> error on autoLog getServlet:", e);
        }

        if (log.isDebugEnabled()) {
            log.debug("------------------------------- start --------------------------");
        }
        Object result = null;
        info.setMethod(request != null ? request.getMethod() : "undefined");
        Object[] args = joinPoint.getArgs();
        if (Objects.nonNull(args) && args.length > 0) {
            List<Object> filterArgs = Arrays.stream(args)
                    .filter(arg -> (!(arg instanceof HttpServletRequest)
                            && !(arg instanceof HttpServletResponse)
                            && !(arg instanceof MultipartFile)))
                    .collect(Collectors.toList());
            info.setParam(filterArgs);
        }
        info.setOriginUrl(request != null ? request.getRequestURL().toString() : null);
        info.setInterfaceDesc(methodInfo);
        info.setTargetDetail(joinPoint.getTarget().toString());
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            info.setErrorMsg(e != null ? e.getMessage() : null);
            log.error("===> An error occurred during around operation:api info: {} ", e);
            throw new RuntimeException(e);
        } finally {
            if (logProperties.isPrintResult()) {
                info.setResult(result);
            }
            info.setConsumed(System.currentTimeMillis() - begin);
            // 打印调用方法全路径以及执行方法
            try {
                log.info("===> Request Interface Info: \n{};", JSON.toJSONString(info, SerializerFeature.PrettyFormat));

            } catch (Exception e) {
                log.error("Log aspect fastjson serializer error... ignore");
            }
        }
        return result;
    }

    private boolean matchRequest(String targetName) {
        Set<String> filter = logProperties.getFilter();
        if (CollectionUtils.isEmpty(filter)) {
            return false;
        }
        String prefixName = targetName.substring(0, targetName.lastIndexOf("."));
        return filter.contains(targetName) ||
                filter.contains(prefixName);
    }

    /**
     * @param joinPoint
     */
    public MethodSignature getSignature(JoinPoint joinPoint) {
        return (MethodSignature) joinPoint.getSignature();
    }

    /**
     * @param joinPoint
     */
    public String getClass(JoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getName();
    }

    /**
     * @param joinPoint
     */
    public String getMethod(JoinPoint joinPoint) {
        return joinPoint.getSignature().getName();
    }

}