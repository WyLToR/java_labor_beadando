package org.example.demo.controller;

import org.example.demo.entity.Film;
import org.example.demo.repository.EloadasRepository;
import org.example.demo.repository.FilmRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CrudController {

    private final FilmRepository filmRepository;
    private final EloadasRepository eloadasRepository;

    public CrudController(
            FilmRepository filmRepository,
            EloadasRepository eloadasRepository
    ) {
        this.filmRepository = filmRepository;
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping("/crud")
    public String crud(Model model) {

        model.addAttribute(
                "filmek",
                filmRepository.findAll()
        );

        return "crud";
    }

    @GetMapping("/crud/uj")
    public String ujFilm(Model model) {

        model.addAttribute(
                "film",
                new Film()
        );

        model.addAttribute(
                "modositas",
                false
        );

        return "film-form";
    }

    @PostMapping("/crud/ment")
    public String filmMentese(
            @ModelAttribute Film film,
            RedirectAttributes redirectAttributes
    ) {

        if (filmRepository.existsById(film.getId())) {

            redirectAttributes.addFlashAttribute(
                    "uzenet",
                    "Már létezik film ezzel az ID-val."
            );

            return "redirect:/crud";
        }

        filmRepository.save(film);

        redirectAttributes.addFlashAttribute(
                "uzenet",
                "A film sikeresen hozzá lett adva."
        );

        return "redirect:/crud";
    }

    @GetMapping("/crud/modosit/{id}")
    public String filmModositasa(
            @PathVariable Integer id,
            Model model
    ) {

        Film film = filmRepository
                .findById(id)
                .orElseThrow();

        model.addAttribute(
                "film",
                film
        );

        model.addAttribute(
                "modositas",
                true
        );

        return "film-form";
    }

    @PostMapping("/crud/modosit")
    public String filmModositasa(
            @ModelAttribute Film film,
            RedirectAttributes redirectAttributes
    ) {

        filmRepository.save(film);

        redirectAttributes.addFlashAttribute(
                "uzenet",
                "A film sikeresen módosítva."
        );

        return "redirect:/crud";
    }

    @PostMapping("/crud/torol/{id}")
    public String filmTorlese(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {

        eloadasRepository.deleteByFilmId(id);

        filmRepository.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "uzenet",
                "A film sikeresen törölve."
        );

        return "redirect:/crud";
    }
}