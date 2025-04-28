package domain;

import java.io.Serializable;
import java.util.Objects;

public class PensiuneCanina extends Entity<Integer> implements Serializable {
    private Float latitude;
    private Float longitude;
    private String name;
    private String nrTel;
    private Boolean non_stop;

    public PensiuneCanina() {}

    public PensiuneCanina(Float latitudine, Float longitudine, String numePensiune, String nrTel, Boolean nonStop) {
        this.latitude = latitudine;
        this.longitude = longitudine;
        this.name = numePensiune;
        this.nrTel = nrTel;
        this.non_stop = nonStop;
    }

    public PensiuneCanina(Integer id, Float latitudine, Float longitudine, String numePensiune, String nrTel, Boolean nonStop) {
        this.setId(id);

        this.latitude = latitudine;
        this.longitude = longitudine;
        this.name = numePensiune;
        this.nrTel = nrTel;
        this.non_stop = nonStop;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
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
        PensiuneCanina that = (PensiuneCanina) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(name, that.name) && Objects.equals(nrTel, that.nrTel) && Objects.equals(non_stop, that.non_stop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, name, nrTel, non_stop);
    }

    @Override
    public String toString() {
        return "PensiuneCanina{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", name='" + name + '\'' +
                ", nrTel='" + nrTel + '\'' +
                ", non_stop=" + non_stop +
                '}';
    }
}