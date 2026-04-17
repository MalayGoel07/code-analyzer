package com.malay.codeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.malay.codeanalyzer.service.AiService;

@RestController
@RequestMapping("/ai")
public class AiController {
    @Autowired
    private AiService aiService;
    @PostMapping("/analyze")
    public String analyze(@RequestBody String code) {
        return aiService.analyze(code);
    }
}
