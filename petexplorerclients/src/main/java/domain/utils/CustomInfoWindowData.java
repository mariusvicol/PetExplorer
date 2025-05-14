package domain.utils;

public class CustomInfoWindowData {
    private String title;
    private String nrTel;
    private String program;
    private int image; // resource id
    private boolean checked;

    public CustomInfoWindowData(String title, String nrTel, String program, int image) {
        this.title = title;
        this.nrTel = nrTel;
        this.program = program;
        this.image = image;
        this.checked=false;
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
}
