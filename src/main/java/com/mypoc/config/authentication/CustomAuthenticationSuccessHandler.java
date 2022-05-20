package com.mypoc.config.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    public CustomAuthenticationSuccessHandler(){
        System.out.println("CustomAuthenticationSuccessHandler ==>>>>");
    }

    /**
     * Called when a user has been successfully authenticated.
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("URI : " + request.getRequestURI());
        String redirectURI = request.getRequestURI().contains("tenant1") ? "/tenant1/tenanthomePage" : "/tenant2/tenanthomePage";
        response.sendRedirect(redirectURI);
    }
}
