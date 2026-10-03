package org.example.demo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "eloadas")
public class Eloadas {

    @EmbeddedId
    private EloadasId id;

    private Integer nezoszam;
    private Integer bevetel;

    public Eloadas() {
    }

    public EloadasId getId() {
        return id;
    }

    public void setId(EloadasId id) {
        this.id = id;
    }

    public Integer getNezoszam() {
        return nezoszam;
    }

    public void setNezoszam(Integer nezoszam) {
        this.nezoszam = nezoszam;
    }

    public Integer getBevetel() {
        return bevetel;
    }

    public void setBevetel(Integer bevetel) {
        this.bevetel = bevetel;
    }
}