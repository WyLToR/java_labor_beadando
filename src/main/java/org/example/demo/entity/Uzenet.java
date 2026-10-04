package org.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "uzenet")
public class Uzenet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nev;

    private String email;

    @Column(length = 2000)
    private String uzenet;

    private LocalDateTime kuldesIdeje;

    public Uzenet() {
    }

    @PrePersist
    public void prePersist() {
        kuldesIdeje = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUzenet() {
        return uzenet;
    }

    public void setUzenet(String uzenet) {
        this.uzenet = uzenet;
    }

    public LocalDateTime getKuldesIdeje() {
        return kuldesIdeje;
    }

    public void setKuldesIdeje(LocalDateTime kuldesIdeje) {
        this.kuldesIdeje = kuldesIdeje;
    }
}

