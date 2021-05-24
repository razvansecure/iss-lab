package model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@javax.persistence.Entity
@Table( name = "imprumut" )
public class Imprumut extends Entity<Long>{
    private long abonat;
    private long carte;
    private LocalDate dataImprumut;
    private LocalDate dataReturnare;

    public Imprumut(){}

    public Imprumut(long abonat, long carte, LocalDate dataImprumut, LocalDate dataReturnare) {
        this.abonat = abonat;
        this.carte = carte;
        this.dataImprumut = dataImprumut;
        this.dataReturnare = dataReturnare;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) { super.setId(id); }

    public long getAbonat() {
        return abonat;
    }

    public void setAbonat(long abonat) {
        this.abonat = abonat;
    }

    public long getCarte() {
        return carte;
    }

    public void setCarte(long carte) {
        this.carte = carte;
    }

    public LocalDate getDataImprumut() {
        return dataImprumut;
    }

    public void setDataImprumut(LocalDate dataImprumut) {
        this.dataImprumut = dataImprumut;
    }

    public LocalDate getDataReturnare() {
        return dataReturnare;
    }

    public void setDataReturnare(LocalDate dataReturnare) {
        this.dataReturnare = dataReturnare;
    }

    @Override
    public String toString() {
        return "Imprumut{" +
                "abonat=" + abonat +
                ", carte=" + carte +
                ", dataImprumut=" + dataImprumut +
                ", dataReturnare=" + dataReturnare +
                '}';
    }
}
