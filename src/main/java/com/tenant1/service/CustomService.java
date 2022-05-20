package com.tenant1.service;


import com.mypoc.config.filter.CustomFilter;
import org.springframework.stereotype.Service;

@Service
public class CustomService {

    public CustomService(){
        System.out.println("CustomService instantiated..");
    }
}
