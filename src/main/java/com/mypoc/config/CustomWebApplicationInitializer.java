/**
 * 
 */
package com.mypoc.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author biswadipmukherjee
 *
 */

@Configuration
public class CustomWebApplicationInitializer implements ServletContextInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("CustomWebApplicationInitializer started");
		//servletContext.getSessionCookieConfig().setHttpOnly(true);
		//servletContext.getSessionCookieConfig().setSecure(true);
		//servletContext.setInitParameter("contextConfigLocation", );
        // Manage the lifecycle of the root application context
		//servletContext.addListener(new ContextLoaderListener(rootContext));

		AnnotationConfigWebApplicationContext ctx =  new AnnotationConfigWebApplicationContext();
		ctx.setConfigLocation("com.tenant1.controller");
		ctx.register(MvcConfigrationTenant1.class);
		ctx.setServletContext(servletContext);
		ServletRegistration.Dynamic servletOne = servletContext.addServlet(
				"SpringProgrammaticDispatcherServlet1", new DispatcherServlet(ctx));
        servletOne.setLoadOnStartup(1);
        servletOne.addMapping("/tenant1/*");

		AnnotationConfigWebApplicationContext ctx1 =  new AnnotationConfigWebApplicationContext();
		ctx1.setConfigLocation("com.tenant2.controller");
		ctx1.register(MvcConfigrationTenant2.class);
		ctx1.setServletContext(servletContext);
		ServletRegistration.Dynamic servletTwo = servletContext.addServlet(
				"SpringProgrammaticDispatcherServlet2", new DispatcherServlet(ctx1));
		servletTwo.setLoadOnStartup(2);
		servletTwo.addMapping("/tenant2/*");
	}

}
