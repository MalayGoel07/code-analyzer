package com.malay.codeanalyzer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.malay.codeanalyzer.service.JdoodleService;


@Controller
public class SandboxController {
    private final JdoodleService jdoodleService;

    public SandboxController(JdoodleService jdoodleService) {
        this.jdoodleService = jdoodleService;
    }

    @GetMapping("/SandboxCompiler")
    public String showComparePage() {
        return "SandboxCompiler";
    }

    @PostMapping
    public String run(@RequestParam String code,@RequestParam String language,Model model) {
        String output = jdoodleService.runCode(code, language);
        model.addAttribute("code", code);
        model.addAttribute("language", language);
        model.addAttribute("output", output);
        return "SandboxCompiler";
}
}
