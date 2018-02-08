package com.tanyouping.weixiao.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Tan Youping on 2018/1/9
 */
public class ServletContextInitListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("system initialization");
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("system destroyed");
    }
}
