package org.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "film")
public class Film {

    @Id
    private Integer id;

    private String cim;
    private Integer ev;
    private Integer hossz;

    public Film() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public Integer getEv() {
        return ev;
    }

    public void setEv(Integer ev) {
        this.ev = ev;
    }

    public Integer getHossz() {
        return hossz;
    }

    public void setHossz(Integer hossz) {
        this.hossz = hossz;
    }
}