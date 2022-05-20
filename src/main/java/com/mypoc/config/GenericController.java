package com.mypoc.config;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class GenericController {

    @GetMapping(path = "/error403")
    public String error403(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Inside renderWelcomeMvcLoginPage1");
        return "403";
    }
}
