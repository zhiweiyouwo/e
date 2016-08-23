package com.loy.e.core.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;
/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
@Component("myListener") 
public class MyListener implements ServletContextListener{  
    /** 
     * @param arg0 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent) 
     */  
    @Override  
    public void contextDestroyed(ServletContextEvent arg0) {  
        System.out.println("contextDestroyed...");  
    }  
    /** 
     * @param arg0 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent) 
     */  
    @Override  
    public void contextInitialized(ServletContextEvent arg0) {  
        System.out.println("listener contextInitialized...");  
    }  
       
}  
