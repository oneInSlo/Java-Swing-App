package si.feri.opj.imamovic.prvi;

import java.io.Serializable;
import java.lang.IllegalArgumentException;

public class Dimenzije implements Serializable {
    private double visina;
    private double sirina;
    private double dolzina;

    /**
     * 
     * @return - returns the attribute visina
     */
    public double getVisina() {
        return visina;
    }

    /**
     * 
     * @param visina - takes the variable visina
     */
    public void setVisina(double visina) {
        if (visina <= 0 || sirina <= 0 || dolzina <= 0) 
            throw new IllegalArgumentException("-- Vrednosti dimenzij ne morejo biti negativne!");
        else
            this.visina = visina;
    }

    public double getSirina() {
        return sirina;
    }

    public void setSirina(double sirina) {
        if (visina <= 0 || sirina <= 0 || dolzina <= 0) 
            throw new IllegalArgumentException("-- Vrednosti dimenzij ne morejo biti negativne!");
        else
            this.sirina = sirina;
    }

    public double getDolzina() {
        return dolzina;
    }

    public void setDolzina(double dolzina) {
        if (visina <= 0 || sirina <= 0 || dolzina <= 0) 
            throw new IllegalArgumentException("-- Vrednosti dimenzij ne morejo biti negativne!");
        else
            this.dolzina = dolzina;
    }

    public void setDimenzije(double visina, double sirina, double dolzina) {
        if (visina <= 0 || sirina <= 0 || dolzina <= 0) 
            throw new IllegalArgumentException("-- Vrednosti dimenzij ne morejo biti negativne!");
        else {
            this.visina = visina;
            this.sirina = sirina;
            this.dolzina = dolzina;
        }
    }

    /**
     * 
     * @param visina - takes the parameter visina
     * @param sirina - takes the parameter sirina
     * @param dolzina - takes the parameter dolzina
     */
    public Dimenzije(double visina, double sirina, double dolzina) {
        if (visina <= 0 || sirina <= 0 || dolzina <= 0) 
            throw new IllegalArgumentException("-- Vrednosti dimenzij ne morejo biti negativne!");
        else
            setDimenzije(visina, sirina, dolzina);
    }

    public double izracunajVolumen() {
        return visina * sirina * dolzina;
    }

    @Override
    public String toString() {
        return " Visina: " + visina + "\n Sirina: " + sirina + "\n Dolzina: " + dolzina;
    }
}
