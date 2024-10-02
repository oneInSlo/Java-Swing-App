package si.feri.opj.imamovic.drugi;

import si.feri.opj.imamovic.prvi.Dimenzije;
import si.feri.opj.imamovic.tretji.OznakaZaboja;
import si.feri.opj.imamovic.tretji.Posiljka;

import java.io.Serializable;
import java.time.LocalDate;

public class Paket extends Posiljka implements Serializable {

    public Paket(String naziv, LocalDate datumOdposlanja) {
        super(naziv, datumOdposlanja);
    }

    public Paket(String naziv, Dimenzije dimenzije, boolean dragocenost, LocalDate datumOdposlanja) {
        super(naziv, dimenzije, datumOdposlanja, dragocenost);
    }

    public Paket(String naziv, Dimenzije dimenzije, boolean dragocenost, LocalDate datumOdposlanja, double cena) {
        super(naziv, dimenzije, datumOdposlanja, dragocenost, cena);
    }

    public Paket(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, boolean dragocenost, double cena) {
        super("PAKET", naziv, dimenzije, datumOdposlanja, dragocenost, cena);
    }

    /* public Paket() {
    } */

    @Override
    public String toString() {
        return super.toString() + "\nDragocenost: " + dragocenost;
    }
}

