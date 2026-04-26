package com.malay.codeanalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/")
    public String welcome(){
        return "welcome";
    }

    @GetMapping("/demo")
    public String demo() {
        return "demo";
    }
}
