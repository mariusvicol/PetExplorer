package domain;

public class Farmacie extends Entity<Integer>{
    private Integer id;
    private Float latitudine;
    private Float longitudine;
    private String nume;
    private Boolean non_stop;

    public Farmacie(){}
    public Farmacie(Float latitudine, Float longitudine, String nume, Boolean non_stop) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nume = nume;
        this.non_stop = non_stop;
    }

    public Float getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(Float longitudine) {
        this.longitudine = longitudine;
    }

    public Float getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(Float latitudine) {
        this.latitudine = latitudine;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
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
        Farmacie farmacie = (Farmacie) o;
        return getId().equals(farmacie.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }


    @Override
    public String toString() {
        return "Farmacie { ID = "+getId().toString()+", Longitudine = "+ getLongitudine().toString() + ", Latitudine = "+ getLatitudine().toString()+ ", Nume = "+ getNume() + ", Non_stop = "+ getNon_stop().toString() + "}";
    }
}
