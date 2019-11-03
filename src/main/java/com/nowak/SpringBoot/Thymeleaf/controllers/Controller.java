package com.nowak.SpringBoot.Thymeleaf.controllers;

import org.springframework.web.bind.annotation.GetMapping;


@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String showMain(){
        return "main-page";
    }
}
