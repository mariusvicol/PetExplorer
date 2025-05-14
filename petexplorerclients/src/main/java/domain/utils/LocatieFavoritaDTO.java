package domain.utils;

import androidx.annotation.NonNull;

public class LocatieFavoritaDTO {

    private double latitude;
    private double longitude;
    private String title;
    private String phone;
    private boolean nonStop;
    private String type;

    public LocatieFavoritaDTO(double latitude, double longitude, String title, String type, boolean nonStop, String phone) {
        this.latitude = latitude;
        this.type = type;
        this.nonStop = nonStop;
        this.phone = phone;
        this.title = title;
        this.longitude = longitude;
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
                '}';
    }
}
