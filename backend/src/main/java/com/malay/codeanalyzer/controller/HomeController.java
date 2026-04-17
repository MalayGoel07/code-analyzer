package com.malay.codeanalyzer.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.malay.codeanalyzer.model.EditForm1;
import com.malay.codeanalyzer.model.EditForm2;
import com.malay.codeanalyzer.repository.PracticeProblemRepository;
import com.malay.codeanalyzer.model.SignupForm;
import com.malay.codeanalyzer.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PracticeProblemRepository practiceProblemRepository;

    public HomeController(UserRepository userRepository, PracticeProblemRepository practiceProblemRepository) {
        this.userRepository = userRepository;
        this.practiceProblemRepository = practiceProblemRepository;
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        SignupForm user = userRepository.findByEmail(currentUser.getEmail());
        model.addAttribute("user", user != null ? user : currentUser);
        return "home";
    }

    @GetMapping("/code-comp")
    public String codeComp() {
        return "code-comp";
    }

    @GetMapping("/code-para")
    public String codePara() {
        return "code-comp";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
    @GetMapping("/welabout")
    public String welabout() {
        return "welabout";
    }

    @GetMapping("/instant-metrics")
    public String instantMetrics(Model model) {
        model.addAttribute("problems", practiceProblemRepository.findAllByOrderByCreatedAtDesc());
        return "instant-metrics";
    }

    @GetMapping("/deep-compare")
    public String deepCompare() {
        return "deep-compare";
    }

    @GetMapping("/roadmap")
    public String qualitySignals() {
        return "roadmap";
    }

    @GetMapping("/ai")
    public String ai() {
        return "ai";
    }

    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }

    @GetMapping("/profile")
    public String profile(Model model, HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        SignupForm user = userRepository.findByEmail(currentUser.getEmail());
        model.addAttribute("user", user != null ? user : currentUser);
        return "profile";
    }

    @GetMapping("/profile/delete")
    public String deleteProfileConfirm(HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        return "delete-account";
    }

    @PostMapping("/profile/delete")
    public String deleteProfile(HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        SignupForm user = userRepository.findByEmail(currentUser.getEmail());
        if (user != null) {
            userRepository.delete(user);
        }
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/editform1")
    public String editForm1(Model model, HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        SignupForm dbUser = userRepository.findByEmail(currentUser.getEmail());
        if (dbUser == null) {
            model.addAttribute("error", "User not found in database. Please login again.");
            return "profile";
        }
        EditForm1 form = new EditForm1();
        form.setUserId(dbUser.getId());
        form.setName(dbUser.getName());
        form.setEmail(dbUser.getEmail());
        model.addAttribute("form", form);
        return "editform1";
    }

@PostMapping("/edit-name")
    public String editName(@Valid EditForm1 form, BindingResult result, Model model, HttpSession session, @RequestParam("userId") String userId) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        if (result.hasErrors()) {
            model.addAttribute("form", form);
            return "editform1";
        }
        SignupForm user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            model.addAttribute("error", "User not found. Please login again.");
            model.addAttribute("form", form);
            return "editform1";
        }
        user.setName(form.getName());
        userRepository.save(user);
        session.setAttribute("currentUser", user);
        model.addAttribute("form", form);
        model.addAttribute("messages", List.of(Map.of("type", "success", "text", "Name updated")));
        return "editform1";
    }

    @GetMapping("/editform2")
    public String editForm2(Model model, HttpSession session) {
        SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
        if (currentUser == null) {
            return "redirect:/login";
        }
        SignupForm dbUser = userRepository.findByEmail(currentUser.getEmail());
        if (dbUser == null) {
            model.addAttribute("error", "User not found in database. Please login again.");
            return "profile";
        }
        EditForm2 form = new EditForm2();
        form.setUserId(dbUser.getId());
        model.addAttribute("form", form);
        return "editform2";
    }

    @PostMapping("/edit-password")
        public String editPassword(@Valid EditForm2 form, BindingResult result, Model model, HttpSession session, @RequestParam("userId") String userId) {
            SignupForm currentUser = (SignupForm) session.getAttribute("currentUser");
            if (currentUser == null) {
                return "redirect:/login";
            }
            if (result.hasErrors()) {
                model.addAttribute("form", form);
                return "editform2";
            }
            SignupForm user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                model.addAttribute("error", "User not found. Please login again.");
                model.addAttribute("form", form);
                return "editform2";
            }
            user.setPassword(form.getPassword());
            userRepository.save(user);
            session.setAttribute("currentUser", user);
            model.addAttribute("form", new EditForm2());
            model.addAttribute("messages", List.of(Map.of("type", "success", "text", "Password updated")));
            return "editform2";
        }
}
