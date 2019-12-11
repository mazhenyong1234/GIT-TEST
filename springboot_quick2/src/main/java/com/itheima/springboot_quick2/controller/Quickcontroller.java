package com.itheima.springboot_quick2.controller;/*
author:ma
*/

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class Quickcontroller {

    @Value("${person.name}")
    private String name;
    private String username;
    private Integer userage;



    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setUserage(Integer userage) {
        this.userage = userage;
    }

    @RequestMapping("/quick2")
    public String quick(){
        System.out.println(name);
        return "spring boot!!!!!!";
    }
}
