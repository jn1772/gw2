package com.codebytes.web.setup;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.codebytes.threads.Driver;

public class AppContextListener implements ServletContextListener {

	@Autowired
	Driver d;

	// Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("Init...");
        WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext()).getAutowireCapableBeanFactory().autowireBean(this);
		System.out.println("Complete...");
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("Shutting down...");
		d.thread.interrupt();
	}
}
