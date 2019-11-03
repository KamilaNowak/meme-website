package com.nowak.SpringBoot.Thymeleaf.controllers;

import org.springframework.web.bind.annotation.GetMapping;


@org.springframework.stereotype.Controller
public class Controller {

    @GetMapping("/")
    public String showMain(){
        return "main-page";
    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "login-page";
    }

    @GetMapping("/register")
    public String showRegisterPage(){
        return "register-page";
    }

    @GetMapping("/proceedLogin")
    public String proceedLogin(){
        return  null;
    }
}
