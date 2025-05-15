package domain.utils;

import androidx.annotation.NonNull;

public class LocatieFavoritaDTO {

    private double latitude;
    private double longitude;
    private String title;
    private String phone;
    private boolean nonStop;
    private String type;
    private Integer idLocation;
    private Integer idUser;

    public LocatieFavoritaDTO() {}

    public LocatieFavoritaDTO(double latitude, double longitude, String title, String type, boolean nonStop, String phone) {
        this.latitude = latitude;
        this.type = type;
        this.nonStop = nonStop;
        this.phone = phone;
        this.title = title;
        this.longitude = longitude;
    }

    // pentru salvare/stergere, ce trimit la backend sa corespunda cu ce am nevoie pentru adaugarea in tabela
    public LocatieFavoritaDTO(Integer idUser, String locationType, Integer idLocation) {
        this.idUser = idUser;
        this.type = locationType;
        this.idLocation = idLocation;
    }

    public LocatieFavoritaDTO(double latitude, double longitude, String title, String type, boolean nonStop, String phone, Integer idLoc) {
        this.latitude = latitude;
        this.type = type;
        this.nonStop = nonStop;
        this.phone = phone;
        this.title = title;
        this.longitude = longitude;
        this.idLocation = idLoc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isNonStop() {
        return nonStop;
    }

    public void setNonStop(boolean nonStop) {
        this.nonStop = nonStop;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    @NonNull
    @Override
    public String toString() {
        return "LocatieFavoritaDTO{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", nonStop=" + nonStop +
                ", type='" + type + '\'' +
                ", idLocation=" + idLocation +
                ", idUser=" + idUser +
                '}';
    }
}
