package com.codebytes.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.codebytes.core.GW2TP;

public class AppContextListener implements ServletContextListener {

	@Autowired
	GW2TP gInstance;

	// Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Init...");
        WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
		gInstance.init();
		System.out.println("Complete...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Shutting down...");
		gInstance.thread.interrupt();
	}
}
