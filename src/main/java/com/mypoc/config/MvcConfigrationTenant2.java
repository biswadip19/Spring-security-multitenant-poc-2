/**
 * 
 */
package com.mypoc.config;

import org.springframework.boot.web.servlet.support.ErrorPageFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
//@ComponentScan(basePackages = "com.mypoc.multitenant.controller2")
public class MvcConfigrationTenant2 implements WebMvcConfigurer {
	
	@Bean
	public ViewResolver internalResourceViewResolver2() {
	    InternalResourceViewResolver bean = new InternalResourceViewResolver();
	    bean.setViewClass(JstlView.class);
	    bean.setPrefix("/WEB-INF/tenant2/");
	    bean.setSuffix(".jsp");
	    return bean;
	}

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(3600)
				.resourceChain(true).addResolver(new PathResourceResolver());
	}

	@Bean
	public ErrorPageFilter errorPageFilter2() {
		return new ErrorPageFilter();
	}
}