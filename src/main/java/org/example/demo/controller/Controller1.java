package org.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Controller1 {

    @GetMapping("/")
    public String home() {
        return "home";
    }

 /* @GetMapping("/adatbazis")
    public String databasePage() {
        return "database";
    }*/

    @GetMapping("/kapcsolat")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/diagram")
    public String diagramPage() {
        return "diagram";
    }

    @GetMapping("/rest")
    public String restPage() {
        return "rest";
    }

    @GetMapping("/uzenetek")
    public String messagesPage() {
        return "messages";
    }

    @GetMapping("/crud")
    public String crudPage() {
        return "crud";
    }

    @GetMapping("/user")
    public String userPage() {
        return "redirect:/uzenetek";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "redirect:/crud";
    }
}