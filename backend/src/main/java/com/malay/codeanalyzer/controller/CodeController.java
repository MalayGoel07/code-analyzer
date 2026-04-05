package com.malay.codeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.malay.codeanalyzer.model.AnalysisResult;
import com.malay.codeanalyzer.service.AnalyzerFactory;

@Controller
public class CodeController {

    @Autowired
    private AnalyzerFactory factory;

    @PostMapping("/analyze")
    public String analyze(@RequestParam String code,@RequestParam String language,Model model) {
        AnalysisResult result = factory.analyze(language, code);
        model.addAttribute("result", result);
        model.addAttribute("code", code);
        model.addAttribute("language", language);
        return "code-comp"; 
}

}

