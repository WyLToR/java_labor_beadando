package org.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Embeddable
public class EloadasId implements Serializable {

    @Column(name = "filmid")
    private Integer filmid;

    @Column(name = "moziid")
    private Integer moziid;

    private LocalDate datum;

    public EloadasId() {
    }

    public Integer getFilmid() {
        return filmid;
    }

    public void setFilmid(Integer filmid) {
        this.filmid = filmid;
    }

    public Integer getMoziid() {
        return moziid;
    }

    public void setMoziid(Integer moziid) {
        this.moziid = moziid;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EloadasId that)) return false;

        return Objects.equals(filmid, that.filmid)
                && Objects.equals(moziid, that.moziid)
                && Objects.equals(datum, that.datum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmid, moziid, datum);
    }
}