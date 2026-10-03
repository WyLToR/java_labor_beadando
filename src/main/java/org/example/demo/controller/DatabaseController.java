package org.example.demo.controller;

import org.example.demo.repository.EloadasRepository;
import org.example.demo.repository.FilmRepository;
import org.example.demo.repository.MoziRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DatabaseController {

    private final FilmRepository filmRepository;
    private final MoziRepository moziRepository;
    private final EloadasRepository eloadasRepository;

    public DatabaseController(
            FilmRepository filmRepository,
            MoziRepository moziRepository,
            EloadasRepository eloadasRepository
    ) {
        this.filmRepository = filmRepository;
        this.moziRepository = moziRepository;
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/adatbazis")
    public String database(Model model) {

        model.addAttribute(
                "filmek",
                filmRepository.findAll()
        );

        model.addAttribute(
                "mozik",
                moziRepository.findAll()
        );

        model.addAttribute(
                "eloadasok",
                eloadasRepository
                        .findAll(PageRequest.of(0, 50))
                        .getContent()
        );

        return "database";
    }
}