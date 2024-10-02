package si.feri.opj.imamovic.tretji;

import si.feri.opj.imamovic.drugi.*;

import java.io.Serializable;

public class Skladisce extends Depo implements Serializable {

    public double vrniCenoSkladiscenja() {
        return cena;
    }

    public Skladisce(String naziv, String lokacija, int stPosiljk) {
        super(naziv, lokacija, stPosiljk);
    }

    //public Skladisce(String naziv, String lokacija, double cena) { super(naziv, lokacija, cena); }

    public Skladisce(String naziv, String lokacija, double cena) { super("NAVADNO", naziv, lokacija, cena); }

    @Override
    public String toString() {
        return super.toString();
    }
}
