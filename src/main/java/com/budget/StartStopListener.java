package com.budget;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

public class StartStopListener implements ServletContextListener {

    private static final Logger LOGGER = Logger.getLogger(StartStopListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Servlet has been started.");
        PostgressUtils.getInstance().load();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Servlet has been stopped.");
    }
}
