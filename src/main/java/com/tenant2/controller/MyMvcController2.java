package com.tenant2.controller;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class MyMvcController2 {

    MyMvcController2(){
        System.out.println("MyMvcController2 started");
    }

    @GetMapping(path = "/tenantloginPage")
    public String renderWelcomeMvcLoginPage() {
        System.out.println("Inside renderWelcomeMvcLoginPage2");
        return "loginPage";
    }

    @GetMapping(path = "/tenanthomePage")
    public String renderWelcomeMvcPage(ModelMap model,HttpServletRequest httpServletRequest) {
        System.out.println("Inside renderWelcomeMvcPage2");
        System.out.println("Session ID Tenant#2=====> " + httpServletRequest.getSession(false).getId());
        model.put("name", getLoggedinUserName());
        return "homepage";
    }

    private String getLoggedinUserName() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if( principal instanceof UserDetails){
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }

    @GetMapping(path = "/logoutUser")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Logout method invoked" + getLoggedinUserName());
        request.getSession(false).invalidate();
        //SecurityContextHolder.getContext().setAuthentication(null);
        response.sendRedirect("/tenant2/tenantloginPage");
    }

    @GetMapping(path = "/error403")
    public String error403(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Inside renderWelcomeMvcLoginPage1");
        return "403";
    }
}
