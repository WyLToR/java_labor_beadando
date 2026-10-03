package org.example.demo.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
public class Controller1 {
    @GetMapping("/")
    public String home() {
        return "home";
    }
    @GetMapping("/user")
    public String userPage() {
        return "user";
    }
    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }
}