package si.feri.opj.imamovic.threads;

import si.feri.opj.imamovic.drugi.Depo;
import si.feri.opj.imamovic.tretji.Skladisce;

public class Dostavljalec implements Runnable {

    private String ime;
    private DostavnoVozilo dostavnoVozilo;
    private int trajanjeDostave;

    private static int oddaljenost = 3000;
    private static Dostavljalec najhitrejsi;

    public Dostavljalec(String ime) {
        this.ime = ime;
        this.dostavnoVozilo = null;
        this.trajanjeDostave = 0;
    }

    @Override
    public void run() {
        Depo depo = new Depo() {
            @Override
            public double vrniCenoSkladiscenja() {
                return 0;
            }
        };

        dostavnoVozilo = depo.pridobiDostavnoVozilo();

        int dis = 0;
        while (dis < oddaljenost) {
            try {
                Thread.sleep(1000);
                dis += dostavnoVozilo.getHitrost();
                trajanjeDostave++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(ime + " je koncal/a s dostavo v " + trajanjeDostave + " sekundah.");

        synchronized (Dostavljalec.class) {
            if (najhitrejsi == null || trajanjeDostave < najhitrejsi.trajanjeDostave) {
                najhitrejsi = this;
            }
            System.out.println("-- NAJHITREJSI: " + najhitrejsi.ime + " (koncal/a je dostavo v " + najhitrejsi.trajanjeDostave + " sekundah).\n");
        }

    }
}
