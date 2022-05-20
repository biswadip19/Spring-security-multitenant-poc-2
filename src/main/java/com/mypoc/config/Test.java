package com.mypoc.config;

import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class Test {

    public static void main(String[] args) {
        String arr[] = {"abc","bcd"};
        System.out.println(arr.toString());
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

        context.setConfigLocations(arr.toString());
    }
}
