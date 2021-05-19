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

}
