package domain;

import java.io.Serializable;

public class User implements Serializable {

    private Integer id;
    private String email;
    private String password;
    private String nume;
    private String nr_telefon;

    public User() {
    }

    public User(Integer id, String email, String password, String nume, String nrTelefon) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nume = nume;
        this.nr_telefon = nrTelefon;
    }

    // Getters și Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getNrTelefon() {
        return nr_telefon;
    }

    public void setNrTelefon(String nrTelefon) {
        this.nr_telefon = nrTelefon;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", nume='" + nume + '\'' +
                ", nrTelefon='" + nr_telefon + '\'' +
                '}';
    }
}
