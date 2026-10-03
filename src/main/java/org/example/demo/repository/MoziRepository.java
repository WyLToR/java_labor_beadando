package org.example.demo.repository;

import org.example.demo.entity.Mozi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoziRepository
        extends JpaRepository<Mozi, Integer> {
}