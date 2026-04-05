package com.malay.codeanalyzer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.malay.codeanalyzer.model.FeedbackForm;
import com.malay.codeanalyzer.repository.FeedbackRepository;

import jakarta.validation.Valid;

@Controller
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping("/feedback")
    public String feedback(Model model) {
        model.addAttribute("form", new FeedbackForm());
        return "feedback";
    }

    @PostMapping("/feedback")
    public String feedbackPost(@Valid FeedbackForm form, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "feedback";
        }
        feedbackRepository.save(form);
        model.addAttribute("form", new FeedbackForm());
        model.addAttribute("messages", List.of(Map.of("type", "success", "text", "Thanks for your feedback!")));
        return "feedback";
    }
}
