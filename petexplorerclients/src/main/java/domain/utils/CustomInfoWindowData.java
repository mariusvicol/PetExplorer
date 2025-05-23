package domain.utils;

public class CustomInfoWindowData {
    private String title;
    private String nrTel;
    private String program;
    private int image;
    private boolean checked;
    private String locationType;
    private Integer entityId;

    private double latitude;
    private double longitude;

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


    public CustomInfoWindowData(String title, String nrTel, String program, int image, boolean checked, String locationType, int entityId) {
        this.title = title;
        this.nrTel = nrTel;
        this.program = program;
        this.image = image;
        this.checked = checked;
        this.locationType = locationType;
        this.entityId = entityId;
    }
    public CustomInfoWindowData(String title, String nrTel, String program, int image, boolean checked) {
        this.title = title;
        this.nrTel = nrTel;
        this.program = program;
        this.image = image;
        this.checked = checked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getNrTel() {
        return nrTel;
    }

    public void setNrTel(String nrTel) {
        this.nrTel = nrTel;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
}
