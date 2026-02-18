package com.gamereviewhub.controller;

import com.gamereviewhub.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out.");
        }
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {
        if (username == null || username.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username cannot be empty.");
            return "redirect:/register";
        }
        if (password == null || password.length() < 4) {
            redirectAttributes.addFlashAttribute("errorMessage", "Password must be at least 4 characters.");
            return "redirect:/register";
        }
        if (userService.existsByUsername(username.trim())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username already taken.");
            return "redirect:/register";
        }
        userService.registerUser(username.trim(), password);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login";
    }
}
