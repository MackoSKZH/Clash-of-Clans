package postavy;

import budovy.Budovy;
import fri.shapesge.Kruh;
import fri.shapesge.Manazer;

import main.Segment;
import mapa.Mapa;
import obraz.Obrazovka;

//Trieda reprezentujúca nepriateľskú jednotku – barbara.
//Barbara možno považovať za "melee" jednotku, ktorá sa presúva k najbližšej budove a útočí na ňu opakovane, kým ju nezničí.
public class Barbar implements Jednotka {
    private final Kruh kruh;
    private double x;
    private double y;
    private double cielX;
    private double cielY;
    private final double rychlost;
    private boolean dosiaholCiel;
    private long casKedDosiahol;

    private int zivoty;
    private boolean zivy;

    private final Mapa mapa;
    private main.Segment cielovySegment;

    //Inicializuje barbara na daných súradniciach, nastavuje jeho cieľ (najbližšiu budovu) a vizualizáciu v prostredí fri.shapesge.
    //
    //startX, startY – počiatočná pozícia na mape
    //
    //mapa – referencia na mapu s budovami
    //
    //manazer – správca grafických objektov
    public Barbar(double startX, double startY, Mapa mapa, Manazer manazer) {
        this.mapa = mapa;
        this.x = startX;
        this.y = startY;

        this.dosiaholCiel = false;
        this.casKedDosiahol = 0;

        this.zivoty = 20;
        this.zivy = true;

        this.cielovySegment = this.najdiNajblizsiSegment();

        int segmentX = -1;
        int segmentY = -1;

        for (int i = 0; i < mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < mapa.getPocetSegmentov(); j++) {
                if (mapa.getSegment(i, j) == this.cielovySegment) {
                    segmentY = i;
                    segmentX = j;
                }
            }
        }

        this.cielX = segmentX * Obrazovka.ROZMER_SEGMENTU + (double)Obrazovka.ROZMER_SEGMENTU / 2;
        this.cielY = segmentY * Obrazovka.ROZMER_SEGMENTU + 70 + (double)Obrazovka.ROZMER_SEGMENTU / 2;

        this.rychlost = 2.0 + Math.random() * 1.5;
        this.kruh = new Kruh((int)this.x, (int)this.y);
        this.kruh.zmenPriemer(10);
        this.kruh.zmenFarbu("brown");
        this.kruh.zobraz();

        manazer.spravujObjekt(this);
    }

    //Vracia, či je jednotka nepriateľ.
    //Používa sa napr. pre obranné veže (kanón), aby vedeli po kom útočiť.
    @Override
    public boolean jeNepriatel() {
        return true;
    }

    //Metóda volaná v každom hernom kroku.
    //Barbara sa buď presúva k cieľu, alebo ak ho už dosiahol, útočí každú sekundu na budovu, kým ju nezničí.
    @Override
    public void tik() {
        if (!this.zivy) {
            return;
        }

        if (!this.dosiaholCiel) {
            this.pohybujSa();
            return;
        }

        if (this.cielovySegment.getBudovy().isEmpty()) {
            Segment novaBudova = this.najdiNajblizsiSegment();
            if (novaBudova != null) {
                this.cielovySegment = novaBudova;

                int segmentX = -1;
                int segmentY = -1;

                for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
                    for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                        if (this.mapa.getSegment(i, j) == this.cielovySegment) {
                            segmentX = j;
                            segmentY = i;
                        }
                    }
                }

                this.cielX = segmentX * Obrazovka.ROZMER_SEGMENTU + Obrazovka.ROZMER_SEGMENTU / 2.0;
                this.cielY = segmentY * Obrazovka.ROZMER_SEGMENTU + 70 + Obrazovka.ROZMER_SEGMENTU / 2.0;
                this.dosiaholCiel = false;
            }
            return;
        }

        long casTeraz = System.currentTimeMillis();
        if (casTeraz - this.casKedDosiahol >= 1000) {
            this.casKedDosiahol = casTeraz;

            Budovy budova = this.cielovySegment.getBudovy().get();
            int noveZivoty = budova.getZivoty() - 5;
            if (noveZivoty <= 0) {
                this.cielovySegment.zruseneBudovy();
            } else {
                budova.setZivoty(noveZivoty);
            }
        }
    }

    private void pohybujSa() {
        double dx = this.cielX - this.x;
        double dy = this.cielY - this.y;
        double vzdialenost = Math.sqrt(dx * dx + dy * dy);

        if (vzdialenost < 2) {
            this.dosiaholCiel = true;
            this.casKedDosiahol = System.currentTimeMillis();
            return;
        }

        double krokX = (dx / vzdialenost) * this.rychlost;
        double krokY = (dy / vzdialenost) * this.rychlost;

        this.x += krokX;
        this.y += krokY;

        this.kruh.posunVodorovne((int)krokX);
        this.kruh.posunZvisle((int)krokY);
    }

    //Hladá nablizsi mozny segment, na ktory sa da zautocit
    private Segment najdiNajblizsiSegment() {
        Segment najblizsi = null;
        double najblizsiaVzdialenost = Double.MAX_VALUE;

        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                Segment s = this.mapa.getSegment(i, j);

                if (s.getBudovy().isEmpty()) {
                    continue;
                }

                double stredX = j * Obrazovka.ROZMER_SEGMENTU + (double)Obrazovka.ROZMER_SEGMENTU / 2;
                double stredY = i * Obrazovka.ROZMER_SEGMENTU + 70 + (double)Obrazovka.ROZMER_SEGMENTU / 2;

                double dx = this.x - stredX;
                double dy = this.y - stredY;
                double vzd = Math.sqrt(dx * dx + dy * dy);

                if (vzd < najblizsiaVzdialenost) {
                    najblizsiaVzdialenost = vzd;
                    najblizsi = s;
                }
            }
        }
        return najblizsi;
    }

    //Znižuje životy barbara.
    //Ak dosiahnu nulu alebo menej, barbar sa označí za mŕtveho a jeho kruh sa skryje.
    @Override
    public void zran(int zranenie) {
        if (!this.zivy) {
            return;
        }

        this.zivoty -= zranenie;
        if (this.zivoty <= 0) {
            this.zivy = false;
            this.kruh.skry();
        }
    }

    //Zisťuje, či je barbar stále nažive.
    @Override
    public boolean jeZivy() {
        return this.zivy;
    }

    //Vracia aktuálne X/Y súradnice barbara.
    //Používa sa napr. pre výpočet vzdialenosti pri útoku kanóna.
    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }
}
