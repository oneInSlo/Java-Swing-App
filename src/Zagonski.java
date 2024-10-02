import java.time.LocalDate;
import java.util.ArrayList;

import si.feri.opj.imamovic.prvi.*;
import si.feri.opj.imamovic.drugi.*;
import si.feri.opj.imamovic.threads.Dostavljalec;
import si.feri.opj.imamovic.tretji.*;
import si.feri.opj.imamovic.ui.Swing;
import si.feri.opj.imamovic.exceptions.*;

/**
 * @author Ena Imamovic
 * @version 1.0
 */
public class Zagonski {

    /**
     * 
     * @param args - takes the arguments from the command line
     */
    public static void main(String[] args) {

        LocalDate danes = LocalDate.now();

        PremiumSkladisce premiumSkladisce = new PremiumSkladisce("PremiumX", "Maribor", 3, false);
        PremiumSkladisce premiumSkladisce2 = new PremiumSkladisce("PremiumY", "Maribor", 5, true);

        Dimenzije x = new Dimenzije(2, 4, 6);
        Dimenzije y = new Dimenzije(3, 5, 7);
        Dimenzije z = new Dimenzije(1, 3, 5);
        Dimenzije karkoli = new Dimenzije(2, 8, 9);

        Posiljka posiljkaX = new Posiljka("PosiljkaX", x, danes, true, 10);
        Posiljka posiljkaY = new Posiljka("PosiljkaY", y, danes, false, 10);
        Posiljka posiljkaZ = new Posiljka("PosiljkaZ", z, danes, true, 10);
        Posiljka posiljkaKarkoli = new Posiljka("Karkoli", karkoli, danes, false, 10);
        Posiljka posiljkaKarkoli2 = new Posiljka("Karkoli2", karkoli, danes, true, 10);
        Posiljka posiljkaKarkoli3 = new Posiljka("Karkoli3", karkoli, danes, true, 10);

        Zaboj zaboj1 = new Zaboj("Zaboj", x, danes, OznakaZaboja.BIOLOSKI, 520);
        
        try {
            //premiumSkladisce.skladisciPosiljko(posiljkaX);
            premiumSkladisce.skladisciPosiljko(posiljkaY);
            premiumSkladisce.skladisciPosiljko(posiljkaZ);
            premiumSkladisce.skladisciPosiljko(posiljkaKarkoli);
            premiumSkladisce.skladisciPosiljko(posiljkaKarkoli2);
            premiumSkladisce.skladisciPosiljko(posiljkaKarkoli3);
        } catch (SkladiscenjeException | OznakaZabojaException ex) {
            ex.printStackTrace();
        } 

        System.out.println("\nZasednost skladisca: " + premiumSkladisce.vrniZasednost() + "%");
        System.out.println("\nPosiljke v premium skladiscu: " + premiumSkladisce.prikaziPosiljke());

        Dimenzije vozilo = new Dimenzije(50, 50, 50);
        
        try {
            System.out.println("\nAli se lahko posiljka natovori na vozilo? " + posiljkaX.lahkoNatovori(vozilo) + "\n");
        } catch (PredragoException ex) {
            ex.printStackTrace();
        }

        Zaboj zaboj = new Zaboj("Zaboj", x, danes, OznakaZaboja.BIOLOSKI, 520);

        try {
            System.out.println("\nAli se lahko posiljka natovori na vozilo? " + zaboj.lahkoNatovori(vozilo) + "\n");
        } catch (PredragoException ex) {
            ex.printStackTrace();
        }

        // Illegal Argument Exceptions
        // Dimenzije exc = new Dimenzije(-2, 3, 5);
        // posiljkaX.setCena(-255);

        Swing swing = new Swing();

        ArrayList<String> imena = new ArrayList<String>();
        imena.add("Janez");
        imena.add("Mojca");
        imena.add("Matej");
        imena.add("Katja");
        imena.add("Rok");
        imena.add("Katarina");
        imena.add("Igor");
        imena.add("Olga");

        for (String ime : imena) {
            new Thread(new Dostavljalec(ime)).start();
        }

    }
}