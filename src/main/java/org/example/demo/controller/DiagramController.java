package org.example.demo.controller;

import org.example.demo.repository.EloadasRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DiagramController {

    private final EloadasRepository eloadasRepository;

    public DiagramController(EloadasRepository eloadasRepository) {
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/diagram")
    public String diagram(Model model) {

        List<Object[]> eredmenyek =
                eloadasRepository.findBevetelMozinkent();

        List<String> mozik = new ArrayList<>();
        List<Long> bevetelek = new ArrayList<>();

        for (Object[] sor : eredmenyek) {
            mozik.add((String) sor[0]);

            Number bevetel = (Number) sor[1];
            bevetelek.add(bevetel.longValue());
        }

        model.addAttribute("mozik", mozik);
        model.addAttribute("bevetelek", bevetelek);

        return "diagram";
    }
}