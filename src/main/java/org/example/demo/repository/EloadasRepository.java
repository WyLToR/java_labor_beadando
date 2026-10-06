package org.example.demo.repository;

import org.example.demo.entity.Eloadas;
import org.example.demo.entity.EloadasId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EloadasRepository
        extends JpaRepository<Eloadas, EloadasId> {

    @Query(
            value = """
                    SELECT m.nev, SUM(e.bevetel)
                    FROM eloadas e
                    JOIN mozi m ON e.moziid = m.id
                    GROUP BY m.id, m.nev
                    ORDER BY SUM(e.bevetel) DESC
                    """,
            nativeQuery = true
    )
    List<Object[]> findBevetelMozinkent();

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM eloadas WHERE filmid = :filmId",
            nativeQuery = true
    )
    void deleteByFilmId(Integer filmId);
}