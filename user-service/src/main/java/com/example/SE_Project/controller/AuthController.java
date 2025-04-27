package com.example.SE_Project.controller;


import com.example.SE_Project.model.User;
import com.example.SE_Project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        if (session.getAttribute("userEmail") != null) {
            return "redirect:/index";
        }
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(String email, String password, Model model, HttpSession session) {
        if (userService.validateLogin(email, password)) {
            session.setAttribute("userEmail", email);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/index")
    public String index(HttpSession session) {
        if (session.getAttribute("userEmail") == null) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}