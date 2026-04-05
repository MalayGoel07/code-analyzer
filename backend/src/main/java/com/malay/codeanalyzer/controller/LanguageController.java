package com.malay.codeanalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LanguageController {

    @GetMapping("/language")
    public String languagePage(@RequestParam String name) {
        return "language";
    }
}

