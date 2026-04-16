package com.malay.codeanalyzer.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.malay.codeanalyzer.model.AdminLoginForm;
import com.malay.codeanalyzer.model.PracticeProblem;
import com.malay.codeanalyzer.repository.PracticeProblemRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AdminController {

    private static final String ADMIN_SESSION_KEY = "adminLoggedIn";

    private final PracticeProblemRepository practiceProblemRepository;

    @Value("${ADMIN_EMAIL}")
    private String adminEmail;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminController(PracticeProblemRepository practiceProblemRepository) {
        this.practiceProblemRepository = practiceProblemRepository;
    }

    @PostMapping("/adminlog")
    public String adminLogin(@Valid @ModelAttribute("form") AdminLoginForm form,BindingResult result,Model model,HttpSession session) {
        if (result.hasErrors()) {return "adminlog";}
        if (adminEmail.equals(form.getEmail()) && adminPassword.equals(form.getPassword())) {
            session.setAttribute(ADMIN_SESSION_KEY, true);
            return "redirect:/adminhome";
        }
        model.addAttribute("error", "Invalid admin credentials");
        return "adminlog";
    }

    @GetMapping("/adminlog")
    public String adminLoginPage(Model model) {
        model.addAttribute("form", new AdminLoginForm());
        return "adminlog";
    }

    @GetMapping("/adminhome")
    public String adminHome(HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/adminlog";
        }
        return "adminhome";
    }

    @GetMapping("/admin/profile")
    public String adminProfile(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/adminlog";
        }
        model.addAttribute("adminEmail", adminEmail);
        return "admin-profile";
    }

    @GetMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/adminlog";
    }

    @GetMapping("/admin/problems/new")
    public String addProblemPage(Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/adminlog";
        }
        if (!model.containsAttribute("problem")) {
            model.addAttribute("problem", new PracticeProblem());
        }
        return "add-problem";
    }

    @PostMapping("/admin/problems")
    public String addProblem(@Valid @ModelAttribute("problem") PracticeProblem problem,BindingResult result, Model model, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/adminlog";
        }
        if (result.hasErrors()) {
            return "add-problem";
        }
        problem.setCreatedAt(LocalDateTime.now());
        practiceProblemRepository.save(problem);
        model.addAttribute("problem", new PracticeProblem());
        model.addAttribute("message", "Problem statement saved successfully.");
        return "add-problem";
    }

    private boolean isAdminLoggedIn(HttpSession session) {
        return Boolean.TRUE.equals(session.getAttribute(ADMIN_SESSION_KEY));
    }
}