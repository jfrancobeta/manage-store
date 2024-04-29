package com.jfranco.spring.tienda.springbootapptienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/start")
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }
    
}
