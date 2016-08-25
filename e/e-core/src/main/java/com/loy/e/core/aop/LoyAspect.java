package com.loy.e.core.aop;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loy.e.common.vo.SessionUser;
import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.conf.Settings;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.security.service.LoyLogService;
import com.loy.e.security.service.SecurityUserService;
import com.loy.e.security.service.SystemKeyService;
import com.loy.e.security.service.UserSessionService;
import com.loy.e.security.vo.User;

/**
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 * 
 */
@Aspect
@Order(1)
public class LoyAspect {
    protected final Log logger = LogFactory.getLog(LoyAspect.class);

    @Autowired
    LoyLogService loyLogService;
    @Autowired
    SecurityUserService securityUserService;
    @Autowired
    UserSessionService userSessionService;
    @Autowired
    private Settings settings;
    @Autowired
    SystemKeyService systemKeyService;

    @Before(value = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void beforAdvice(JoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Object target = joinPoint.getTarget();
        if (target instanceof ErrorController) {
            return;
        }
        SessionUser user = userSessionService.getSessionUser();
        if (user != null && user.getId() != null) {
            if (args != null) {
                for (Object obj : args) {
                    if (obj instanceof BaseEntity) {
                        BaseEntity arg = (BaseEntity) obj;
                        if (user != null) {
                            arg.setCreatorId(user.getId());
                            arg.setModifierId(user.getId());
                        }
                    }
                }
            }
        }
    }

    @Around(value = "@annotation(com.loy.e.core.annotation.ControllerLogExeTime)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (settings.getRecordOperateLog()) {
            try {
                MethodSignature signature = (MethodSignature) joinPoint.getSignature();
                Method method = signature.getMethod();
                SessionUser user = userSessionService.getSessionUser();
                ControllerLogExeTime controllerLogExeTime = method
                        .getAnnotation(ControllerLogExeTime.class);
                if (controllerLogExeTime.log()) {
                    String description = method.getAnnotation(ControllerLogExeTime.class)
                            .description();
                    if (user != null) {
                        loyLogService.log(systemKeyService.getSystemCode(), user.getId(),
                                user.getName(), description, args);
                    } else {
                        String methodName = method.getName();
                        if ("login".equals(methodName)) {
                            String userName = (String) args[0];
                            Object[] temp = { args[0] };
                            User u = securityUserService.findByUsername(userName);
                            if (u != null) {
                                loyLogService.log(systemKeyService.getSystemCode(), u.getId(),
                                        u.getName(), description, temp);
                            }
                        }
                    }
                }
            } catch (Throwable e) {
                logger.error("记录操作日志错误", e);
            }
        }

        Object rt = joinPoint.proceed(args);
        return rt;

    }

    @Around(value = "@annotation(com.loy.e.core.annotation.ControllerLogExeTime)")
    public Object timeAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        Object rt = joinPoint.proceed(args);
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            ControllerLogExeTime controllerLogExeTime = method
                    .getAnnotation(ControllerLogExeTime.class);
            if (controllerLogExeTime.exeTime()) {
                RequestMapping requestMappingAnnotation = joinPoint.getTarget().getClass()
                        .getAnnotation(RequestMapping.class);
                String url = "";
                if (requestMappingAnnotation != null) {
                    String[] temp = requestMappingAnnotation.value();
                    url = StringUtils.join(temp);
                }

                String[] value = method.getAnnotation(RequestMapping.class).value();
                long endTime = System.currentTimeMillis();
                endTime = (endTime - startTime);
                url = url + StringUtils.join(value);
                logger.debug(method.toString() + "方法执行时间 ： " + endTime + "(ms)");
                loyLogService.record(systemKeyService.getSystemCode(), url, method.toString(),
                        endTime, controllerLogExeTime.description());
            }
        } catch (Throwable e) {
            logger.error("记录方法执行时间错误", e);
        }

        return rt;
    }
}
