package si.feri.opj.imamovic.tretji;

import si.feri.opj.imamovic.drugi.*;

import java.io.Serializable;

public class PremiumSkladisce extends Depo implements Serializable {

    private boolean kamera;

    public boolean getKamera() {
        return kamera;
    }

    public void setKamera(boolean kamera) {
        this.kamera = kamera;
    }

    public double vrniCenoSkladiscenja() {
        if (getKamera())
            return cena * 2;
        else
            return cena;
    }

    public PremiumSkladisce(String naziv, String lokacija, int stPosiljk) {
        super(naziv, lokacija, stPosiljk);
    }

    public PremiumSkladisce(String naziv, String lokacija, int stPosiljk, boolean kamera) {
        super(naziv, lokacija, stPosiljk);
        this.kamera = kamera;
    }

    public PremiumSkladisce(String naziv, String lokacija, int stPosiljk, boolean kamera, double cena) {
        super(naziv, lokacija, stPosiljk, cena);
        this.kamera = kamera;
    }

    public PremiumSkladisce(String naziv, String lokacija, double cena, boolean kamera) {
        super("PREMIUM", naziv, lokacija, cena);
        this.kamera = kamera;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}
