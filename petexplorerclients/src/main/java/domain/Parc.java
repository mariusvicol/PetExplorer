package domain;

import java.io.Serializable;
import java.util.Objects;

public class Parc implements Serializable {
    private Integer id;
    private Float latitudine;
    private Float longitudine;
    private String nume;
    private Boolean nonStop;

    public Parc(Integer id, Float latitudine, Float longitudine, String nume, Boolean nonStop) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nume = nume;
        this.nonStop = nonStop;
    }

    public Parc() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Float latitudine) {
        this.latitudine = latitudine;
    }

    public Float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Float longitudine) {
        this.longitudine = longitudine;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Boolean getNonStop() {
        return nonStop;
    }

    public void setNonStop(Boolean nonStop) {
        this.nonStop = nonStop;
    }

    @Override
    public String toString() {
        return "Parc{" +
                "id=" + id +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", nume='" + nume + '\'' +
                ", nonStop=" + nonStop +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parc parc = (Parc) o;
        return Objects.equals(id, parc.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
