package com.tenant2.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController2 {

    MyRestController2(){
        System.out.print("MyRestController2 initialized ==>");
    }

    @GetMapping(path = "/welcome")
    public String welcome2(){
        return "welcome 2 to custom rest controller";
    }
}
