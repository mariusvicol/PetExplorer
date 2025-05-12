package domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CabinetVeterinar implements Serializable {

    private Integer id;
    private Float latitudine;
    private Float longitudine;
    private String numeCabinet;
    private String nrTelefon;
    private Boolean nonStop;

    public CabinetVeterinar() {
    }

    public CabinetVeterinar(Integer id, Float latitudine, Float longitudine, String numeCabinet, String nrTelefon, Boolean nonStop) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.numeCabinet = numeCabinet;
        this.nrTelefon = nrTelefon;
        this.nonStop = nonStop;
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

    public String getNumeCabinet() {
        return numeCabinet;
    }

    public void setNumeCabinet(String numeCabinet) {
        this.numeCabinet = numeCabinet;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public Boolean getNonStop() {
        return nonStop;
    }

    public void setNonStop(Boolean nonStop) {
        this.nonStop = nonStop;
    }

    @Override
    public String toString() {
        return "CabinetVeterinar{" +
                "id=" + id +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", numeCabinet='" + numeCabinet + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", non_stop=" + nonStop +
                '}';
    }
}
