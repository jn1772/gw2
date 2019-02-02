package com.codebytes.web.setup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.codebytes.*, com.codebytes.web.*")
public class AppConfig implements WebMvcConfigurer{
	
	@Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
//        registry.addResourceHandler("/css/**").addResourceLocations("/css/").setCachePeriod(31556926);
//        registry.addResourceHandler("/img/**").addResourceLocations("/img/").setCachePeriod(31556926);
//        registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
    }
	
//	  @Override
//	  public void addViewControllers(ViewControllerRegistry registry) {
//	    registry.addRedirectViewController("*/", "*")
//	      .setKeepQueryParams(true)
//	      .setStatusCode(HttpStatus.PERMANENT_REDIRECT); 
//	  }
	
//	@Override
//    public void addViewControllers( ViewControllerRegistry registry ) {
//		registry.addViewController("/").setViewName("/WEB-INF/jsp/home.jsp");
//    }
}
