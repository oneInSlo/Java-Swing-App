package si.feri.opj.imamovic.tretji;

import si.feri.opj.imamovic.prvi.*;
import si.feri.opj.imamovic.drugi.*;
import si.feri.opj.imamovic.cetrti.*;
import si.feri.opj.imamovic.exceptions.PredragoException;

import java.io.Serializable;
import java.lang.IllegalArgumentException;

import java.time.LocalDate;
import java.util.Objects;

public class Posiljka implements Transportni, Serializable {

    protected String naziv;
    protected Dimenzije dimenzije;
    protected LocalDate datumOdposlanja;
    protected Artikel[] seznamArtiklov = new Artikel[5];
    protected transient int stArtiklov = 0;
    protected boolean dragocenost;
    protected double cena;

    protected String tip;

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

    public LocalDate getLocalDate() {
        return datumOdposlanja;
    }

    public void setLocalDate(LocalDate datumOdposlanja) {
        this.datumOdposlanja = datumOdposlanja;
    }

    public boolean getDragocenost() {
        return dragocenost;
    }

    public void setDragocenost(boolean dragocenost) {
        this.dragocenost = dragocenost;
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

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public OznakaZaboja getOznakaZaboja() {
        if (this instanceof Zaboj) {
            Zaboj zaboj = (Zaboj) this;
            return zaboj.getOznakaZaboja();
        } else return null;
    }

    public void setOznakaZaboja(OznakaZaboja oznakaZaboja) {
        if (this instanceof Zaboj) {
            Zaboj zaboj = (Zaboj) this;
            zaboj.setOznakaZaboja(oznakaZaboja);
        }
    }

    public boolean lahkoNaloziArtikel(Artikel artikel) {
        double volumen = 0;
        for (int i = 0; i < stArtiklov; i++) {
            volumen += seznamArtiklov[i].getDimenzije().izracunajVolumen();
        }

        volumen += artikel.getDimenzije().izracunajVolumen();

        System.out.println("\nSkupni volumen artiklov: " + volumen);
        System.out.println("\nVolumen zaboja: " + getDimenzije().izracunajVolumen());

        System.out.println("\nMogoce naloziti artikel? ");

        if (getDimenzije().izracunajVolumen() >= (volumen + artikel.getDimenzije().izracunajVolumen()))
            return true;
        else
            return false;
    }
    
    public void dodajArtikel(Artikel artikel) {
        if (stArtiklov < seznamArtiklov.length) {
            seznamArtiklov[stArtiklov] = artikel;
            stArtiklov++;
        }
        else
            System.out.println("Seznam je poln, ni mogoce dodati artikla.");
    }

    public void odstraniArtikel(Artikel artikel) {
        for (int i = 0; i < seznamArtiklov.length; i++) {
            if (seznamArtiklov[i] == artikel) {
                for (int j = i; j < seznamArtiklov.length - 1; j++) {
                    seznamArtiklov[j] = seznamArtiklov[j + 1]; 
                }
                stArtiklov--;
                return;
            }
        }
        System.out.println("Artikel ni v seznamu.");
    }

    public boolean odstraniArtikel(String naziv) {
        for (int i = 0; i < seznamArtiklov.length; i++) {
            if (Objects.equals(seznamArtiklov[i].getNaziv(), naziv)) {
                for (int j = i; j < seznamArtiklov.length - 1; j++) {
                    seznamArtiklov[j] = seznamArtiklov[j + 1]; 
                }
                stArtiklov--;
                return true;
            }
        }
        System.out.println("Artikel ni v seznamu.");
        return false;
    }

    public int vrniSteviloArtiklov() {
        int st = 0;
        for (Artikel artikel : seznamArtiklov) {
            if (artikel != null)
                st++;
        }
        return st;
    }

    public boolean artikelObstaja(Artikel artikel) {
        for (int i = 0; i < stArtiklov; i++) {
        if (seznamArtiklov[i].equals(artikel)) 
            return true;
        }
        return false;
    }

    public String prikaziArtikle() {
        StringBuilder seznam = new StringBuilder("");
        if (vrniSteviloArtiklov() == 0)
            return "Nima artiklov.";
        else {
            for (int i = 0; i < vrniSteviloArtiklov(); i++) {
                seznam.append(seznamArtiklov[i].getNaziv() + " ");
            }
        }
        return seznam.toString();
    }

    public boolean lahkoNatovori(Dimenzije dimenzijeVozila) throws PredragoException {
            if (this instanceof Zaboj) {
                Zaboj zaboj = (Zaboj) this;
                if (zaboj.getCena() > 500) 
                    throw new PredragoException("-- Posiljka se ne more natovoriti na vozilo, saj je cena visja od EUR 500");
            }
            if (this instanceof Paket) {
                Paket paket = (Paket) this;
                if (paket.getCena() > 100) 
                    throw new PredragoException("-- Posiljka se ne more natovoriti na vozilo, saj je cena visja od EUR 100");
            }
            
            if ((dimenzijeVozila.getDolzina() >= this.dimenzije.getDolzina())
            &&  (dimenzijeVozila.getVisina() >= this.dimenzije.getVisina())
            &&  (dimenzijeVozila.getSirina() >= this.dimenzije.getSirina())
            )
                return true;
            else
                return false;
    }

    public Posiljka(String naziv) {
        this.naziv = naziv;
    }

    public Posiljka(String naziv, LocalDate datumOdposlanja) {
        this(naziv);
        setLocalDate(datumOdposlanja);
    }

    public Posiljka(String naziv, Dimenzije dimenzije) {
        this(naziv);
        setDimenzije(dimenzije);
    }

    public Posiljka(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja) {
        this(naziv, dimenzije);
        setLocalDate(datumOdposlanja);
    }

    public Posiljka(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, boolean dragocenost) {
        this(naziv, dimenzije, datumOdposlanja);
        this.dragocenost = dragocenost;
    }

    public Posiljka(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, double cena) {
        this(naziv, dimenzije, datumOdposlanja);
        setCena(cena);
    }

    public Posiljka(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, boolean dragocenost, double cena) {
        this(naziv, dimenzije, datumOdposlanja, cena);
        this.dragocenost = dragocenost;
    }

    public Posiljka(String tip, String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, boolean dragocenost, double cena) {
        this(naziv, dimenzije, datumOdposlanja, dragocenost, cena);
        this.tip = tip;
    }

    @Override 
    public String toString() {
        return "TIP: " + tip + "\n Naziv: " + naziv + "\n Dimenzije: \n" + dimenzije + "\n Datum odposlanja: " + datumOdposlanja + "\n Dragocenost: " + dragocenost + "\n Seznam artiklov: " + prikaziArtikle();
    }

}
