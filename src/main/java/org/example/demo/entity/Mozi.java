package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "mozi")
public class Mozi {

    @Id
    private Integer id;

    private String nev;
    private String varos;
    private Integer ferohely;

    public Mozi() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getVaros() {
        return varos;
    }

    public void setVaros(String varos) {
        this.varos = varos;
    }

    public Integer getFerohely() {
        return ferohely;
    }

    public void setFerohely(Integer ferohely) {
        this.ferohely = ferohely;
    }
}