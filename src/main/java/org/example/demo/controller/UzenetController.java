package org.example.demo.controller;

import org.example.demo.repository.UzenetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UzenetController {

    private final UzenetRepository uzenetRepository;

    public UzenetController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/uzenetek")
    public String uzenetek(Model model) {

        model.addAttribute(
                "uzenetek",
                uzenetRepository.findAllByOrderByKuldesIdejeDesc()
        );

        return "messages";
    }
}