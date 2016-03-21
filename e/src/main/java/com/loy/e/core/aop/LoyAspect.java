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
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.RequestMapping;

import com.loy.e.core.annotation.ControllerLogExeTime;
import com.loy.e.core.conf.Settings;
import com.loy.e.core.entity.BaseEntity;
import com.loy.e.core.log.LoyLogService;
import com.loy.e.core.util.UserUtils;
import com.loy.e.core.web.SimpleUser;
import com.loy.e.sys.domain.entity.UserEntity;
import com.loy.e.sys.repository.UserRepository;

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
	 UserRepository userRepository;
	 @Autowired
	 private Settings settings;
	 @Before(value  = "@annotation(org.springframework.web.bind.annotation.RequestMapping)")
     public void beforAdvice( JoinPoint joinPoint) throws Throwable{
    	 Object[] args = joinPoint.getArgs();
    	 SimpleUser simpleUser = UserUtils.getSimipleUser();
    	 if(simpleUser!= null){
    		 String operatorId = simpleUser.getId();
        	 if(args != null){
        		 for(Object obj: args){
        			 if(obj instanceof BaseEntity){
        				 BaseEntity arg = (BaseEntity)obj;
        				 arg.setCreatorId(simpleUser.getId());
        				 arg.setModifierId(operatorId);
        			 }
        		 }
        	 }
    	 }
     }
     @Around(value  = "@annotation(com.loy.e.core.annotation.ControllerLogExeTime)")
     public Object aroundAdvice( ProceedingJoinPoint joinPoint) throws Throwable{
    	 Object[] args = joinPoint.getArgs();
    	 if(settings.getRecordOperateLog()){
    		 try{
    	    	 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    	         Method method = signature.getMethod();
    	         SimpleUser simpleUser = UserUtils.getSimipleUser();
    	         ControllerLogExeTime controllerLogExeTime = method.getAnnotation(ControllerLogExeTime.class);
    	         if(controllerLogExeTime.log()){
    	        	 String description  = method.getAnnotation(ControllerLogExeTime.class).description();
    	        	 if(simpleUser != null){
    	        		 loyLogService.log(simpleUser.getId(), simpleUser.getName(),description , args);
    	        	 }else{
		        			String methodName = method.getName();
			        		if("login".equals(methodName)){
			        			 simpleUser = new SimpleUser();
			        			 String userName = (String)args[0];
			        			 Object[] temp = {args[0]};
			        			 UserEntity user = userRepository.findByUsername(userName);
			        			 if(user != null){
			        				 loyLogService.log(user.getId(), user.getName(),description , temp); 
			        			 }
			        		} 
    	        	 }
    	         }
        	 }catch(Throwable e){
        		 logger.error("记录操作日志错误", e);
        	 }
    	 }
    	 
    	 Object rt = joinPoint.proceed(args);
    	 return rt;
       
     }
   
     @Around(value  = "@annotation(com.loy.e.core.annotation.ControllerLogExeTime)")
     public Object timeAroundAdvice( ProceedingJoinPoint joinPoint) throws Throwable{
    	
    	 Object[] args = joinPoint.getArgs();
         long startTime = System.currentTimeMillis(); 
    	 Object rt = joinPoint.proceed(args);
    	 try{
    		 MethodSignature signature = (MethodSignature) joinPoint.getSignature();
             Method method = signature.getMethod();
    		 ControllerLogExeTime controllerLogExeTime = method.getAnnotation(ControllerLogExeTime.class);
    		 if(controllerLogExeTime.exeTime()){
    			 RequestMapping requestMappingAnnotation = joinPoint.getTarget().getClass().getAnnotation(RequestMapping.class);
            	 String url ="";
            	 if(requestMappingAnnotation != null){
            		 String[] temp = requestMappingAnnotation.value();
            		 url = StringUtils.join(temp);
            	 }
                 
                 String[] value  = method.getAnnotation(RequestMapping.class).value(); 
            	 long endTime = System.currentTimeMillis();
            	 endTime = (endTime - startTime);
            	 url = url+StringUtils.join(value);
            	 logger.debug(method.toString()+"方法执行时间 ： "+endTime+"(ms)");
            	 loyLogService.record(url,method.toString(),endTime,controllerLogExeTime.description());
    		 }
    	 }catch(Throwable e){
    		 logger.error("记录方法执行时间错误", e);
    	 }
         
    	 return rt; 
     }
 }  
