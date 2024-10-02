package si.feri.opj.imamovic.threads;

import si.feri.opj.imamovic.prvi.*;

import java.util.Random;

public class DostavnoVozilo {

    private boolean vDostavi;
    private Dimenzije dimenzijeVozila;
    private int hitrost;

    public boolean isvDostavi() {
        return vDostavi;
    }

    public void setvDostavi(boolean vDostavi) {
        this.vDostavi = vDostavi;
    }

    public Dimenzije getDimenzijeVozila() {
        return dimenzijeVozila;
    }

    public void setDimenzijeVozila(Dimenzije dimenzijeVozila) {
        this.dimenzijeVozila = dimenzijeVozila;
    }

    public int getHitrost() {
        return hitrost;
    }

    public void setHitrost(int hitrost) {
        this.hitrost = hitrost;
    }

    public DostavnoVozilo(Dimenzije dimenzijeVozila) {
        this.vDostavi = false;
        this.dimenzijeVozila = dimenzijeVozila;
        this.hitrost = new Random().nextInt(41) + 50;
    }

}
