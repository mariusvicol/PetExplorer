package domain;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDate;

public class AnimalPierdut implements Serializable {
    private Integer id;

    @SerializedName("latitudine")
    private Float latitudine;

    @SerializedName("longitudine")
    private Float longitudine;

    @SerializedName("nume_animal")
    private String numeAnimal;

    @SerializedName("descriere")
    private String descriere;

    @SerializedName("tip_caz")
    private String tipCaz;

    @SerializedName("poza")
    private String poza;

    @SerializedName("nr_telefon")
    private String nrTelefon;

    @SerializedName("data_caz")
    private String dataCaz; // Recomandat ca String dacă nu configurezi un adapter pentru LocalDate

    @SerializedName("id_user")
    private Integer idUser;

    @SerializedName("rezolvat")
    private Boolean rezolvat;

    public AnimalPierdut() {}

    public AnimalPierdut(Integer id, Float latitudine, Float longitudine, String numeAnimal, String descriere,
                         String tipCaz, String poza, String nrTelefon, String dataCaz, Integer idUser, Boolean rezolvat) {
        this.id = id;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.numeAnimal = numeAnimal;
        this.descriere = descriere;
        this.tipCaz = tipCaz;
        this.poza = poza;
        this.nrTelefon = nrTelefon;
        this.dataCaz = dataCaz;
        this.idUser = idUser;
        this.rezolvat = rezolvat;
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

    public String getNumeAnimal() {
        return numeAnimal;
    }

    public void setNumeAnimal(String numeAnimal) {
        this.numeAnimal = numeAnimal;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public String getTipCaz() {
        return tipCaz;
    }

    public void setTipCaz(String tipCaz) {
        this.tipCaz = tipCaz;
    }

    public String getPoza() {
        return poza;
    }

    public void setPoza(String poza) {
        this.poza = poza;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nrTelefon = nrTelefon;
    }

    public String getDataCaz() {
        return dataCaz;
    }

    public void setDataCaz(String dataCaz) {
        this.dataCaz = dataCaz;
    }

    public Integer getIdUser(){return idUser;}

    public void setIdUser(Integer idUser) {this.idUser = idUser;}

    public Boolean getRezolvat() {return rezolvat;}

    public void setRezolvat(Boolean rezolvat) {this.rezolvat = rezolvat;}

    @Override
    public String toString() {
        return "AnimalPierdut{" +
                "id=" + id +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", numeAnimal='" + numeAnimal + '\'' +
                ", descriere='" + descriere + '\'' +
                ", tipCaz='" + tipCaz + '\'' +
                ", poza='" + poza + '\'' +
                ", nrTelefon='" + nrTelefon + '\'' +
                ", dataCaz='" + dataCaz + '\'' +
                ", idUser='" + idUser +'\''+
                '}';
    }
}
