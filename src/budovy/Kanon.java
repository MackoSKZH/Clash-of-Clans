package budovy;

import fri.shapesge.Kruh;
import mapa.Mapa;
import obraz.Obrazovka;
import postavy.Jednotka;

//Trieda Kanon reprezentuje špeciálnu budovu, kanón, ktorá počas každého herného kroku automaticky útočí na nepriateľské jednotky.
public class Kanon extends Budovy {
    private long poslednyVystrel;
    private static final int COOLDOWN_MS = 500;

    //Konštruktor vytvorí inštanciu kanónu s daným levelom a životmi. Zároveň nastaví počiatočný čas posledného výstrelu.
    public Kanon(int level, int zivoty) {
        super(TypBudovy.KANON, level, zivoty);
        this.poslednyVystrel = 0;
    }

    //Metóda sa volá počas každého herného „ticku“. Ak uplynul čas od posledného výstrelu, vyhľadá prvú živú jednotku, vystrelí na ňu (zníži jej životy) a spustí animáciu strely smerom k jej aktuálnej polohe.
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
