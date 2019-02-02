package com.codebytes.web.setup;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.apache.jasper.servlet.JspServlet;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class AppInitializer implements WebApplicationInitializer {
	@Override//servletContext
	public void onStartup(ServletContext container){
		
	      // Create the 'root' Spring application context
	      AnnotationConfigWebApplicationContext rootContext =
	        new AnnotationConfigWebApplicationContext();
	      rootContext.register(com.codebytes.web.setup.AppConfig.class);

	      // Manage the lifecycle of the root application context
	      container.addListener(new ContextLoaderListener(rootContext));
	      
	      rootContext.setServletContext(container);
	      container.addListener(com.codebytes.web.setup.AppContextListener.class);
	      
	      // Create the dispatcher servlet's Spring application context
	      AnnotationConfigWebApplicationContext dispatcherContext =
	        new AnnotationConfigWebApplicationContext();
	      dispatcherContext.register(com.codebytes.web.controllers.HomepageController.class);

	      // Register and map the dispatcher servlet
	      ServletRegistration.Dynamic dispatcher =
	        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
	      dispatcher.setLoadOnStartup(1);
	      dispatcher.addMapping("*.jsp");
	      dispatcher.addMapping("/");
	      
	      ServletRegistration.Dynamic jspd = container.addServlet("jsp", new JspServlet());
	      jspd.setLoadOnStartup(2);
	      jspd.addMapping("/WEB-INF/jsp/*");
	}
}
