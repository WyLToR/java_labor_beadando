package org.example.demo.controller;

import org.example.demo.entity.Film;
import org.example.demo.repository.EloadasRepository;
import org.example.demo.repository.FilmRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filmek")
public class FilmRestController {

    private final FilmRepository filmRepository;
    private final EloadasRepository eloadasRepository;

    public FilmRestController(
            FilmRepository filmRepository,
            EloadasRepository eloadasRepository
    ) {
        this.filmRepository = filmRepository;
        this.eloadasRepository = eloadasRepository;
    }

    @GetMapping
    public List<Film> osszesFilm() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Film egyFilm(@PathVariable Integer id) {

        return filmRepository
                .findById(id)
                .orElseThrow(() -> new FilmNotFoundException(id));
    }

    @PostMapping
    public Film ujFilm(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    @PutMapping("/{id}")
    public Film filmModositasa(
            @RequestBody Film adat,
            @PathVariable Integer id
    ) {

        return filmRepository
                .findById(id)
                .map(film -> {

                    film.setCim(adat.getCim());
                    film.setEv(adat.getEv());
                    film.setHossz(adat.getHossz());

                    return filmRepository.save(film);
                })
                .orElseGet(() -> {

                    adat.setId(id);

                    return filmRepository.save(adat);
                });
    }

    @DeleteMapping("/{id}")
    public void filmTorlese(@PathVariable Integer id) {

        if (!filmRepository.existsById(id)) {
            throw new FilmNotFoundException(id);
        }

        eloadasRepository.deleteByFilmId(id);
        filmRepository.deleteById(id);
    }
}