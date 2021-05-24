package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table( name = "users" )
public class User extends model.Entity<Long>{
    private String username;
    private String password;
    private boolean isBibliotecar = false;
    private String nume;
    private String cnp;
    private String adresa;
    private String telefon;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {

    }

    public boolean getIsBibliotecar() {
        return isBibliotecar;
    }

    public void setIsBibliotecar(boolean bibliotecar) {
        isBibliotecar = bibliotecar;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) { super.setId(id); }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        this.cnp = cnp;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isBibliotecar=" + isBibliotecar +
                ", nume='" + nume + '\'' +
                ", cnp='" + cnp + '\'' +
                ", adresa='" + adresa + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
