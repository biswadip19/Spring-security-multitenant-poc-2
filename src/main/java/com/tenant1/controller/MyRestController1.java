package com.tenant1.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController1 {

    MyRestController1(){
        System.out.print("MyRestController1 initialized ==>");
    }
    @GetMapping(path = "/welcome")
    public String welcome1(){
        return "welcome 1 to custom rest controller";
    }
}
