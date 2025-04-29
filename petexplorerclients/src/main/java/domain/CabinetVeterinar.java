package domain;

import java.io.Serializable;

public class CabinetVeterinar implements Serializable {

    private Integer id;
    private Float latitudine;
    private Float longitudine;
    private String nume_cabinet;
    private String nrTelefon;
    private Boolean non_stop;

    public CabinetVeterinar() {
    }

    public CabinetVeterinar(Integer id, Float latitudine, Float longitudine, String numeCabinet, String nrTelefon, Boolean nonStop) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nume_cabinet = numeCabinet;
        this.nrTelefon = nrTelefon;
        this.non_stop = nonStop;
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

    public String getNume_cabinet() {
        return nume_cabinet;
    }

    public void setNume_cabinet(String numeCabinet) {
        this.nume_cabinet = numeCabinet;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public Boolean getNon_stop() {
        return non_stop;
    }

    public void setNon_stop(Boolean nonStop) {
        this.non_stop = nonStop;
    }

    @Override
    public String toString() {
        return "CabinetVeterinar{" +
                "id=" + id +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", nume_cabinet='" + nume_cabinet + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", non_stop=" + non_stop +
                '}';
    }
}
