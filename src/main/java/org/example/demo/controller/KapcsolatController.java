package org.example.demo.controller;

import jakarta.validation.Valid;
import org.example.demo.entity.Uzenet;
import org.example.demo.model.KapcsolatForm;
import org.example.demo.repository.UzenetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class KapcsolatController {

    private final UzenetRepository uzenetRepository;

    public KapcsolatController(UzenetRepository uzenetRepository) {
        this.uzenetRepository = uzenetRepository;
    }

    @GetMapping("/kapcsolat")
    public String kapcsolat(Model model) {

        if (!model.containsAttribute("kapcsolatForm")) {
            model.addAttribute(
                    "kapcsolatForm",
                    new KapcsolatForm()
            );
        }

        return "contact";
    }

    @PostMapping("/kapcsolat")
    public String kapcsolatKuldes(
            @Valid @ModelAttribute("kapcsolatForm") KapcsolatForm form,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            return "contact";
        }

        Uzenet uzenet = new Uzenet();

        uzenet.setNev(form.getNev());
        uzenet.setEmail(form.getEmail());
        uzenet.setUzenet(form.getUzenet());

        uzenetRepository.save(uzenet);

        redirectAttributes.addFlashAttribute(
                "siker",
                "Az üzenetet sikeresen elküldted."
        );

        return "redirect:/kapcsolat";
    }
}