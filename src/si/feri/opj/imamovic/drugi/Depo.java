package si.feri.opj.imamovic.drugi;

import si.feri.opj.imamovic.prvi.Dimenzije;
import si.feri.opj.imamovic.threads.DostavnoVozilo;
import si.feri.opj.imamovic.tretji.OznakaZaboja;
import si.feri.opj.imamovic.tretji.Posiljka;
import si.feri.opj.imamovic.tretji.PremiumSkladisce;
import si.feri.opj.imamovic.cetrti.*;
import si.feri.opj.imamovic.exceptions.*;
import si.feri.opj.imamovic.tretji.Skladisce;

import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.util.*;

public abstract class Depo implements Transportni, Serializable {
    private String naziv;
    private String lokacija;
    private Posiljka[] posiljka = new Posiljka[10];
    protected double cena;

    protected String tipDepo;

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
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

    public String getTipDepo() {
        return tipDepo;
    }

    public void setTipDepo(String tipDepo) {
        this.tipDepo = tipDepo;
    }

    public void skladisciPosiljko(Posiljka p) throws SkladiscenjeException, OznakaZabojaException {
       
        if (this instanceof PremiumSkladisce) {
            PremiumSkladisce sklad = (PremiumSkladisce) this;
            if (!sklad.getKamera() && p.getDragocenost()) {
                throw new SkladiscenjeException("-- Skladisce " + sklad.getNaziv() + " nima kamere, zato ne more skladisciti posiljke " + p.getNaziv());
            }
        }

        if (p instanceof Zaboj) {
            Zaboj zaboj = (Zaboj) p;
            if (zaboj.getOznakaZaboja() == OznakaZaboja.BIOLOSKI)
                throw new OznakaZabojaException("-- Oznaka zaboja ne more biti BIOLOSKI!");
        }

        if (vrniSteviloShranjenihPosiljk() == posiljka.length)
            System.out.println("\nDepo je poln! Ni mogoce dodati vec posiljk.");
        else {
            for (int i = 0; i < posiljka.length; i++) {
                if (posiljka[i] == null) {
                    posiljka[i] = p;
                    break;
                }
            }
        }
        
    }

    public void odstraniPosiljko(Posiljka pos) {
        if (posiljka.length != 0) {
            for (int i = 0; i < posiljka.length; i++) {
                if (posiljka[i] == pos)
                    for (int j = i; j < posiljka.length - 1; j++) {
                        posiljka[j] = posiljka[j + 1];
                    }
            }
        }
    }

    public double vrniZasednost() {
        double posiljke = 0;
        double x;
        for (int i = 0; i < posiljka.length; i++) {
            if (posiljka[i] != null)
                posiljke++;
        }
        x = (posiljke * 100) / posiljka.length;
        return x;
    }

    public int vrniSteviloShranjenihPosiljk() {
        int shranjene = 0;
        if (posiljka == null) {
            return 0;
        } else {
            for (int i = 0; i < posiljka.length; i++) {
                if (posiljka[i] != null)
                    shranjene++;
            }
            return shranjene;
        }
    }

    public abstract double vrniCenoSkladiscenja();

    public String prikaziPosiljke() {
        StringBuilder seznam = new StringBuilder("");
        if (vrniSteviloShranjenihPosiljk() == 0) 
            return "Nima posiljk.";
        else {
            for (int i = 0; i < vrniSteviloShranjenihPosiljk(); i++) { 
                seznam.append(posiljka[i].getNaziv() + " ");
            }
        }
        return seznam.toString();
    }

    public boolean lahkoNatovori(Dimenzije dimenzijeVozila) {
        
        System.out.println("Ali se lahko posiljke natovorijo na vozilo?");

        double sDolzina = 0;
        double sVisina = 0;
        double sSirina = 0;

            for (Posiljka pos : posiljka) {
                
                if (pos != null) {

                    sDolzina += pos.getDimenzije().getDolzina();
                    sVisina += pos.getDimenzije().getVisina();
                    sSirina += pos.getDimenzije().getSirina();
                }
            }
            
            if (dimenzijeVozila.getDolzina() >= sDolzina 
            && dimenzijeVozila.getVisina() >= sVisina
            && dimenzijeVozila.getSirina() >= sSirina) 
                return true;
            else {
                return false;
            }
    }

    private transient ArrayList<DostavnoVozilo> vozila = new ArrayList<>();

    public synchronized DostavnoVozilo pridobiDostavnoVozilo() {
         for (DostavnoVozilo voz : vozila) {
             if (!voz.isvDostavi()) {
                 voz.setvDostavi(true);
                 return voz;
             }
         }
         return null;
    }

    public Depo() {
        for (int i = 1; i <= 8; i++) {
            vozila.add(new DostavnoVozilo(new Dimenzije(2, 4, 6)));
        }
    }

    public Depo(String naziv) {
        this.naziv = naziv;
    }

    private Depo(int stPosiljk) {
        if (stPosiljk <= 0) 
            throw new IllegalArgumentException("-- Stevilo posiljk ne more imeti negativno vrednost!");
        else
            posiljka = new Posiljka[stPosiljk];
    }

    public Depo(String naziv, String lokacija, int stPosiljk) {
        this(stPosiljk);
        setNaziv(naziv);
        this.lokacija = lokacija;
    }

    public Depo(String naziv, String lokacija, double cena) {
        this(naziv);
        this.lokacija = lokacija;
        this.cena = cena;
    }

    public Depo(String naziv, String lokacija, int stPosiljk, double cena) {
        this(stPosiljk);
        this.naziv = naziv;
        this.lokacija = lokacija;
        setCena(cena);
    }

    public Depo(Depo depo, String naziv, String lokacija, double cena, boolean kamera) {
        this(naziv, lokacija, cena);
        if (depo instanceof Skladisce) {
            tipDepo = "NAVADNO";
        }
        else {
            tipDepo = "PREMIUM";
        }
        if (depo instanceof PremiumSkladisce) {
            PremiumSkladisce ps = (PremiumSkladisce) this;
            ps.setKamera(kamera);
        }
    }

    public Depo(String tipDepo, String naziv, String lokacija, double cena) {
        this(naziv, lokacija, cena);
        this.tipDepo = tipDepo;
    }

    public Depo(String tipDepo, String naziv, String lokacija, double cena, boolean kamera) {
        this(naziv, lokacija, cena);
        this.tipDepo = tipDepo;
        PremiumSkladisce ps = (PremiumSkladisce) this;
        ps.setKamera(kamera);
    }

    @Override 
    public String toString() {
        return "\n Naziv: " + naziv + "\n Lokacija: " + lokacija + "\n Seznam posiljk: " + prikaziPosiljke();
    }
}

