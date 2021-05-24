package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table( name = "carti" )
public class Carte extends Entity<Long>{
    private String titlu;
    private String autor;
    private int exemplare;

    public Carte(String titlu, String autor, int exemplare) {
        this.titlu = titlu;
        this.autor = autor;
        this.exemplare = exemplare;
    }

    public Carte(){}

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) { super.setId(id); }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getExemplare() {
        return exemplare;
    }

    public void setExemplare(int exemplare) {
        this.exemplare = exemplare;
    }
}
