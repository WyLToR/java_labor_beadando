package org.example.demo.repository;

import org.example.demo.entity.Uzenet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UzenetRepository extends JpaRepository<Uzenet, Long> {

    List<Uzenet> findAllByOrderByKuldesIdejeDesc();
}