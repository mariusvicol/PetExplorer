package domain;

public class Parc extends Entity<Integer>  {

    private Float latitudine;
    private Float longitudine;
    private String nume;
    private Boolean non_stop;

    public Parc(Float latitudine, Float longitudine, String nume, Boolean non_stop) {
        this.latitudine = latitudine;
        this.longitudine = longitudine;
        this.nume = nume;
        this.non_stop = non_stop;
    }

    public Parc() {}

    public Float getLatitudine() {
        return latitudine;
    }

    public Float getLongitudine() {
        return longitudine;
    }

    public String getNume() {
        return nume;
    }

    public Boolean getNon_stop() {
        return non_stop;
    }

    public void setLatitudine(Float latitudine) {
        this.latitudine = latitudine;
    }

    public void setLongitudine(Float longitudine) {
        this.longitudine = longitudine;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setNon_stop(Boolean non_stop) {
        this.non_stop = non_stop;
    }

    @Override
    public String toString() {
        return "Parc{" +
                "latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", nume='" + nume + '\'' +
                ", non_stop=" + non_stop +
                '}';
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Parc parc = (Parc) obj;
        return getId().equals(parc.getId());
    }

    @Override
    public int hashCode() {

        return getId().hashCode();
    }
}