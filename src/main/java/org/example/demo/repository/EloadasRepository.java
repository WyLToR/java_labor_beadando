package org.example.demo.repository;

import org.example.demo.entity.Eloadas;
import org.example.demo.entity.EloadasId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EloadasRepository
        extends JpaRepository<Eloadas, EloadasId> {
}