package com.malay.codeanalyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.malay.codeanalyzer.model.LoginForm;
import com.malay.codeanalyzer.model.SignupForm;
import com.malay.codeanalyzer.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }
    @PostMapping("/login")
    public String loginPost(@Valid LoginForm form, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "login";
        }

        SignupForm user = userRepository.findByEmail(form.getEmail());
        if (user != null && user.getPassword().equals(form.getPassword())) {
            session.setAttribute("currentUser", user);
            return "redirect:/home";
        }
        model.addAttribute("error", "Invalid email or password");
        model.addAttribute("form", form);
        return "login";
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("form", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid SignupForm form, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "signup";
        }
        if (userRepository.existsByEmail(form.getEmail())) {
            model.addAttribute("error", "Email already exists");
            model.addAttribute("form", form);
            return "signup";
        }
        userRepository.save(form);
        session.setAttribute("currentUser", form);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
