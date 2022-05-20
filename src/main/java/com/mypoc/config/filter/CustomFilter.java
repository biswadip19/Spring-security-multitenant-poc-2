package com.mypoc.config.filter;

import com.mypoc.config.authentication.CustomAuthenticationFailureHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class CustomFilter implements Filter {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    /**
     * The <code>doFilter</code> method of the Filter is called by the container
     * each time a request/response pair is passed through the chain due to a
     * client request for a resource at the end of the chain. The FilterChain
     * passed in to this method allows the Filter to pass on the request and
     * response to the next entity in the chain.
     * <p>
     * A typical implementation of this method would follow the following
     * pattern:- <br>
     * 1. Examine the request<br>
     * 2. Optionally wrap the request object with a custom implementation to
     * filter content or headers for input filtering <br>
     * 3. Optionally wrap the response object with a custom implementation to
     * filter content or headers for output filtering <br>
     * 4. a) <strong>Either</strong> invoke the next entity in the chain using
     * the FilterChain object (<code>chain.doFilter()</code>), <br>
     * 4. b) <strong>or</strong> not pass on the request/response pair to the
     * next entity in the filter chain to block the request processing<br>
     * 5. Directly set headers on the response after invocation of the next
     * entity in the filter chain.
     *
     * @param request  The request to process
     * @param response The response associated with the request
     * @param chain    Provides access to the next filter in the chain for this
     *                 filter to pass the request and response to for further
     *                 processing
     * @throws IOException      if an I/O error occurs during this filter's
     *                          processing of the request
     * @throws ServletException if the processing fails for any other reason
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Another way to adding secure cookie
        Cookie[] allCookies = httpServletRequest.getCookies();
        if(null != allCookies){
            Cookie session = Arrays.stream(allCookies)
                    .filter(x -> x.getName().contains("JSESSIONID"))
                    .findFirst().orElse(null);
            if(session != null) {
                session.setHttpOnly(true);
                session.setSecure(true);
                httpServletResponse.addCookie(session);
            }
        }

        if(null != httpServletRequest.getSession(false)){
            System.out.println("Inside Custom Filter for Session ID ==>" + httpServletRequest.getSession(false).getId());
        }
        System.out.println("Inside Custom Filter for Request URI ==>" + httpServletRequest.getRequestURI() );
        System.out.println("Inside Custom Filter for authentication ==>" +
                SecurityContextHolder.getContext().getAuthentication() );
        String tenantCode =  httpServletRequest.getRequestURI().contains("tenant1") ? "TENANT1" : "TENANT2";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication) {
            List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();
            boolean isValidTenant = false;
            for(GrantedAuthority grantedAuthority  :  grantedAuthorities){
                System.out.println("Authority ==> " + grantedAuthority.getAuthority());
                if(grantedAuthority.getAuthority().contains(tenantCode)){
                    isValidTenant = true;
                    break;
                }
            }
            if(authentication.getPrincipal().equals("anonymousUser")){
                System.out.println("anonymousUser ==> ");
                chain.doFilter(httpServletRequest,httpServletResponse);
            }else {
                if( isValidTenant){
                    System.out.println("isValidTenant ==> " +isValidTenant);
                    chain.doFilter(httpServletRequest,httpServletResponse);
                }else{
                    System.out.println("isValidTenant ==> " +isValidTenant);
                    httpServletResponse.setHeader("ERR","403");
                    httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
                    httpServletResponse.sendRedirect("/error403");
                }
            }
        }else{
            chain.doFilter(httpServletRequest,httpServletResponse);
        }



    }
}
