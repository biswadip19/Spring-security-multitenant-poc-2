package com.mypoc.config;

import com.mypoc.config.authentication.AuthenticationProcessingFilter;
import com.mypoc.config.authentication.CustomAuthenticationFailureHandler;
import com.mypoc.config.authentication.CustomAuthenticationProvider;
import com.mypoc.config.authentication.CustomAuthenticationSuccessHandler;
import com.mypoc.config.filter.CustomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity(debug = true)
public class MultiTenantWebSecurityConfig {

   /* @Autowired
    public AuthenticationProcessingFilter authenticationProcessingFilter;*/

    public MultiTenantWebSecurityConfig(){
        System.out.println("MultiTenantWebSecurityConfig started");
    }


    @Configuration
    @Order(1)
    public static class Tenant1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomAuthenticationProvider authProvider;

        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        @Autowired
        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

        /*@Autowired
        private CustomFilterTenant1 customFilterTenant1;*/

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            //super.configure(auth);
            //PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            auth.authenticationProvider(authProvider);
            /* auth
                .inMemoryAuthentication()
                .withUser("user")
                .password(encoder.encode("password"))
                .roles("USER")
                .and()
                .withUser("admin")
                .password(encoder.encode("admin"))
                .roles("USER", "ADMIN");*/
        }
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //super.configure(http);
        /*http.authorizeRequests().antMatchers("/tenant1/tenantloginPage1","/tenant1/welcome1").permitAll()
                .anyRequest().authenticated()
                .and().addFilterAt( getBeforeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .formLogin().loginPage("/tenant1/tenantloginPage1").loginProcessingUrl("/tenant1/login")
                .defaultSuccessUrl("/tenant1/tenanthomePage1",true).failureUrl("/tenant1/tenantloginPage1");
*/
            http.antMatcher("/tenant1/**").sessionManagement().maximumSessions(2).
                    and().invalidSessionUrl("/tenant1/tenantloginPage")
                    .and()
                    .addFilterBefore(getCustomGenericFilter(), AuthenticationProcessingFilter.class )
                    //.addFilterBefore(customFilterTenant1, AuthenticationProcessingFilter.class )
                    .addFilterBefore(getBeforeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().antMatchers("/*/tenantloginPage","/*/welcome").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage("/tenant1/tenantloginPage");
            //.loginProcessingUrl("/tenant1/login").defaultSuccessUrl("/tenant1/tenanthomePage1",true)
            // .failureUrl("/tenant1/tenantloginPage1");
            http.csrf().disable();
        }

        public UsernamePasswordAuthenticationFilter getBeforeAuthenticationFilter() throws Exception {
            AuthenticationProcessingFilter filter = new AuthenticationProcessingFilter();
            filter.setAuthenticationManager(authenticationManager());
            //filter.setFilterProcessesUrl("/login");
            filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            return filter;
        }

        public CustomFilter getCustomGenericFilter(){
            CustomFilter filter = new CustomFilter();
            return filter;
        }

    }

    @Configuration
    @Order(2)
    public static class Tenant2ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Autowired
        private CustomAuthenticationProvider authProvider;

        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        @Autowired
        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

        /*@Autowired
        private CustomFilter customFilter;*/

        @Override
        protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authProvider);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/tenant2/**").sessionManagement().maximumSessions(2).and().invalidSessionUrl("/tenant2/tenantloginPage")
                    //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    //.addFilterBefore(customFilter, AuthenticationProcessingFilter.class )
                    .addFilterBefore(getCustomGenericFilter2(), AuthenticationProcessingFilter.class )
                    .addFilterBefore(getBeforeAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests().antMatchers("/*/tenantloginPage*","/*/welcome*").permitAll()
                    .anyRequest().authenticated()
                    .and().formLogin().loginPage("/tenant2/tenantloginPage");
            http.csrf().disable();
        }


        public UsernamePasswordAuthenticationFilter getBeforeAuthenticationFilter() throws Exception {
            AuthenticationProcessingFilter filter = new AuthenticationProcessingFilter();
            filter.setAuthenticationManager(authenticationManager());
            //filter.setFilterProcessesUrl("/login");
            filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            return filter;
        }

        @Bean
        public CustomFilter getCustomGenericFilter2(){
            CustomFilter filter = new CustomFilter();
            return filter;
        }
    }
}
