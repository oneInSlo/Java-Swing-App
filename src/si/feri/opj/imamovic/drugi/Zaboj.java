package si.feri.opj.imamovic.drugi;

import si.feri.opj.imamovic.prvi.*;
import si.feri.opj.imamovic.tretji.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Zaboj extends Posiljka implements Serializable {

    private OznakaZaboja oznakaZaboja;

    public OznakaZaboja getOznakaZaboja() {
        return oznakaZaboja;
    }

    public void setOznakaZaboja(OznakaZaboja oznakaZaboja) {
        this.oznakaZaboja = oznakaZaboja;
    }

    private Zaboj(String naziv, Dimenzije dimenzije) {
        super(naziv, dimenzije);
    }

    public Zaboj(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja) {
        super(naziv, dimenzije, datumOdposlanja);
    }

    public Zaboj(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, OznakaZaboja oznakaZaboja) {
        super(naziv, dimenzije, datumOdposlanja);
        this.oznakaZaboja = oznakaZaboja;
    }

    public Zaboj(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, OznakaZaboja oznakaZaboja, double cena) {
        super(naziv, dimenzije, datumOdposlanja, cena);
        this.oznakaZaboja = oznakaZaboja;
    }

    public Zaboj(String naziv, Dimenzije dimenzije, LocalDate datumOdposlanja, OznakaZaboja oznakaZaboja, boolean dragocenost, double cena) {
        super("ZABOJ", naziv, dimenzije, datumOdposlanja, dragocenost, cena);
        this.oznakaZaboja = oznakaZaboja;
    }

    /* private Zaboj() {
    } */

    @Override
    public String toString() {
        return super.toString() + "\nOznaka zaboja: " + oznakaZaboja;
    }


}

