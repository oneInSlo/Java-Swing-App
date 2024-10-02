package si.feri.opj.imamovic.prvi;

import java.io.Serializable;

public class Artikel implements Serializable {
    private String naziv;
    private Dimenzije dimenzije;
    private double teza;
    private double cena;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Dimenzije getDimenzije() {
        return dimenzije;
    }

    public void setDimenzije(Dimenzije dimenzije) {
        this.dimenzije = dimenzije;
    }

    public double getTeza() {
        return teza;
    }

    public void setTeza(double teza) {
        if (teza <= 0) 
            throw new IllegalArgumentException("-- Teza ne more imeti negativno vrednost!");
        else
            this.teza = teza;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        if (cena <= 0) 
            throw new IllegalArgumentException("-- Cena ne more imeti negativno vrednost!");
        else
            this.cena = cena;
    }
    
    public Artikel(String naziv, Dimenzije dimenzije) {
        setNaziv(naziv);
        setDimenzije(dimenzije);
    }

    public Artikel(String naziv, Dimenzije dimenzije, double teza, double cena) {
        this(naziv, dimenzije);
        setTeza(teza);
        setCena(cena);
    }

    @Override
    public String toString() {
        return "\nNaziv: " + naziv + "\n Dimenzije: \n" + dimenzije + "\n Teza: " + teza + "\n Cena: " + cena;
    }

}
