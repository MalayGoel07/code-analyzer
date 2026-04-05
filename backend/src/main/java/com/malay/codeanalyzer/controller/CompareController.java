package com.malay.codeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.malay.codeanalyzer.service.CompAnalyzer;

@Controller
@RequestMapping("/compare")
public class CompareController {

    private final CompAnalyzer codeAnalyzer;

    @Autowired
    public CompareController(CompAnalyzer codeAnalyzer) {
        this.codeAnalyzer = codeAnalyzer;
    }

    @GetMapping
    public String showComparePage() {
        return "compare";
    }

    @PostMapping
    public String compareFiles(@RequestParam(value = "file1", required = false) MultipartFile file1,@RequestParam(value = "file2", required = false) MultipartFile file2,@RequestParam(value = "code1", required = false) String code1,@RequestParam(value = "code2", required = false) String code2,Model model) {
        try {
            boolean hasFiles = file1 != null && !file1.isEmpty() && file2 != null && !file2.isEmpty();
            boolean hasText = code1 != null && !code1.isBlank() && code2 != null && !code2.isBlank();
            String result;
            if (hasFiles && hasText) {
                result = "Please provide either files OR text input, not both.";
            } else if (hasText) {
                result = codeAnalyzer.analyzeText(code1, code2);
            } else if (hasFiles) {
                result = codeAnalyzer.analyzeFiles(file1, file2);
            } else {
                result = "Please provide two files or two code snippets to compare.";
            }
            model.addAttribute("result", result);
        } catch (Exception e) {
            model.addAttribute("result", "Error occurred while comparing: " + e.getMessage());
        }
        return "compare";
    }
}
