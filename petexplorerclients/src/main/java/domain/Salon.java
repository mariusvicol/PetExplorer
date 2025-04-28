package domain;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.Objects;

public class Salon extends Entity<Integer> implements Serializable {
    private Float latitude;
    private Float longitude;
    private String name;
    private String nrTel;
    private Boolean non_stop;

    public Salon () {}

    public Salon(Float latitudine, Float longitudine, String numeSalon, String nrTelefon, Boolean nonStop) {
        this.latitude = latitudine;
        this.longitude = longitudine;
        this.name = numeSalon;
        this.nrTel = nrTelefon;
        this.non_stop = nonStop;
    }

    public Salon(Integer id, Float latitudine, Float longitudine, String numeSalon, String nrTelefon, Boolean nonStop) {
        this.setId(id);

        this.latitude = latitudine;
        this.longitude = longitudine;
        this.name = numeSalon;
        this.nrTel = nrTelefon;
        this.non_stop = nonStop;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getNon_stop() {
        return non_stop;
    }

    public void setNon_stop(Boolean non_stop) {
        this.non_stop = non_stop;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Salon salon = (Salon) o;
        return Objects.equals(latitude, salon.latitude) && Objects.equals(longitude, salon.longitude) && Objects.equals(name, salon.name) && Objects.equals(nrTel, salon.nrTel) && Objects.equals(non_stop, salon.non_stop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, name, nrTel, non_stop);
    }

    @NonNull
    @Override
    public String toString() {
        return "Salon{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", nrTel='" + nrTel + '\'' +
                ", non_stop=" + non_stop +
                '}';
    }
}