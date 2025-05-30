package domain.utils;

public class SearchResultDTO {

    private double latitude;
    private double longitude;
    private String title;
    private String phone;
    private boolean nonStop;
    private String type;
    private Integer idLocation;

    public SearchResultDTO() { }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {
        this.idLocation = idLocation;
    }

    @Override
    public String toString() {
        return "SearchResultDTO{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", title='" + title + '\'' +
                ", phone='" + phone + '\'' +
                ", nonStop=" + nonStop +
                ", type='" + type + '\'' +
                ", idLocation=" + idLocation +
                '}';
    }
}
