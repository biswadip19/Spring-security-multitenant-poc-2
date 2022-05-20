package com.mypoc.config.authentication;

import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class AuthenticationProcessingFilter extends UsernamePasswordAuthenticationFilter {

    public AuthenticationProcessingFilter(){
        System.out.println("Bean Created AuthenticationProcessingFilter");
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/*/login", "POST"));
    }

  /*  @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }*/

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String userName = obtainUsername(request);
        System.out.println("username ======> " +  userName);
        String password = obtainPassword(request);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        String tenantRole = request.getRequestURI().contains("tenant1") ? "ROLE_TENANT1" : "ROLE_TENANT2";
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        if(null != userName && null != password && "" != userName ){
            authorityList.add(new SimpleGrantedAuthority(tenantRole));
            System.out.println("authorityList ==> " + authorityList);
            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userName, "" , authorityList);
            super.setDetails(request, usernamePasswordAuthenticationToken);
        }else{
            System.out.println("Blank User-->");
            usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken("", "" , authorityList);
            usernamePasswordAuthenticationToken.setAuthenticated(false);
        }
        System.out.println("AuthenticationProcessingFilter attemptAuthentication ==>" +
                usernamePasswordAuthenticationToken.isAuthenticated());
        return this.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

    }
}