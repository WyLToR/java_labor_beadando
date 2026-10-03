package org.example.demo.repository;

import org.example.demo.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository
        extends JpaRepository<Film, Integer> {
}