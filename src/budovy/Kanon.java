package budovy;

import fri.shapesge.Kruh;
import mapa.Mapa;
import obraz.Obrazovka;
import postavy.Jednotka;

public class Kanon extends Budovy {
    private long poslednyVystrel;
    private static final int COOLDOWN_MS = 500;

    public Kanon(int level, int zivoty) {
        super(TypBudovy.KANON, level, zivoty);
        this.poslednyVystrel = 0;
    }

    @Override
    public void vykonajKrok(Mapa mapa, int x, int y) {
        long teraz = System.currentTimeMillis();
        if (teraz - this.poslednyVystrel < COOLDOWN_MS) {
            return; // ešte sa nabíja
        }

        this.poslednyVystrel = teraz;

        double kanonX = x * Obrazovka.ROZMER_SEGMENTU - Obrazovka.ROZMER_SEGMENTU + 20;
        double kanonY = y * Obrazovka.ROZMER_SEGMENTU + Obrazovka.ROZMER_SEGMENTU * 2 + 20;

        for (Jednotka jednotka : mapa.getHra().getSpravcaJednotiek().getJednotky()) {
            if (!jednotka.jeZivy()) {
                continue;
            }

            jednotka.zran(10);

            // Animácia strely
            double cielX = jednotka.getX();
            double cielY = jednotka.getY();

            Kruh strela = new Kruh((int)kanonX, (int)kanonY);
            strela.zmenPriemer(6);
            strela.zmenFarbu("red");
            strela.zobraz();

            double dx = cielX - kanonX;
            double dy = cielY - kanonY;
            double krokX = dx / 10.0;
            double krokY = dy / 10.0;

            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    strela.posunVodorovne((int)krokX);
                    strela.posunZvisle((int)krokY);
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException ignored) {
                    }
                }
                strela.skry();
            }).start();

            break;
        }
    }
}
