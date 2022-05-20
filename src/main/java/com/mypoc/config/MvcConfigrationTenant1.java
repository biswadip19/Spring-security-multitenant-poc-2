/**
 * 
 */
/**
 *
 */
package com.mypoc.config;

import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 * @author biswadipmukherjee
 *
 */

@Configuration
@EnableWebMvc
//@ComponentScan(basePackages = {"com.mypoc.multitenant.controller1"})
public class MvcConfigrationTenant1 implements WebMvcConfigurer {

	MvcConfigrationTenant1(){
		System.out.println("MvcConfigrationTenant1");
	}
	
	@Bean
	public ViewResolver internalResourceViewResolver() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/tenant1/");
	    bean.setSuffix(".jsp");
	    return bean;
	}

   /* @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new PathResourceResolver());
	}

	@Bean
	public ErrorPageFilter errorPageFilter() {
		return new ErrorPageFilter();
	}
}
