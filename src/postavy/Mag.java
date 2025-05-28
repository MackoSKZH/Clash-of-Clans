package postavy;

import budovy.Budovy;
import fri.shapesge.Kruh;
import fri.shapesge.Manazer;
import mapa.Mapa;
import obraz.Obrazovka;

public class Mag implements Jednotka {
    private static final int POCET_KROKOV_PROJEKTILU = 10;
    private final Mapa mapa;
    private final Kruh mag;
    private final Manazer manazer;
    private double x;
    private double y;

    private long poslednyVystrel;

    private double cielX;
    private double cielY;
    private boolean pohybujeSa;
    private Budovy cielBudova;

    private boolean jeZivy;
    private int zivoty;

    public Mag(double x, double y, Mapa mapa, Manazer manazer) {
        this.x = x;
        this.y = y;
        this.mapa = mapa;
        this.manazer = manazer;

        this.poslednyVystrel = 0;

        this.jeZivy = true;
        this.pohybujeSa = true;
        this.zivoty = 150;

        this.mag = new Kruh((int)x, (int)y);
        this.mag.zmenPriemer(12);
        this.mag.zmenFarbu("#7932a8");
        this.mag.zobraz();

        this.manazer.spravujObjekt(this);

        this.cielBudova = this.najdiNajblizsiuBudovu();
        this.nastavCieloveSuradnice();

    }

    @Override
    public boolean jeNepriatel() {
        return true;
    }

    @Override
    public boolean jeZivy() {
        return this.jeZivy;
    }

    @Override
    public void zran(int zranenie) {
        if (!this.jeZivy) {
            return;
        }

        this.zivoty -= zranenie;
        if (this.zivoty <= 0) {
            this.jeZivy = false;
            this.mag.skry();
        }
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void tik() {
        if (!this.jeZivy) {
            return;
        }

        if (this.cielBudova == null || !this.existuje(this.cielBudova)) {
            this.cielBudova = this.najdiNajblizsiuBudovu();
            if (this.cielBudova == null) {
                return;
            }
            this.nastavCieloveSuradnice();
            this.pohybujeSa = true;
        }

        double vzd = this.vzdialenost(this.x, this.y, this.cielX, this.cielY);

        int dosah = 120;
        if (vzd > dosah) {
            this.pohniSa();
            return;
        }

        this.pohybujeSa = false;

        long teraz = System.currentTimeMillis();
        int cooldown = 2000;
        if (teraz - this.poslednyVystrel < cooldown) {
            return;
        }

        this.poslednyVystrel = teraz;
        this.vystrelNaCiel();
    }

    private void pohniSa() {
        double dx = this.cielX - this.x;
        double dy = this.cielY - this.y;
        double vzd = Math.sqrt(dx * dx + dy * dy);
        if (vzd == 0) {
            return;
        }

        double rychlost = 1.5;
        double krokX = (dx / vzd) * rychlost;
        double krokY = (dy / vzd) * rychlost;

        this.x += krokX;
        this.y += krokY;

        this.mag.posunVodorovne((int)krokX);
        this.mag.posunZvisle((int)krokY);
    }

    private void nastavCieloveSuradnice() {
        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                if (this.mapa.getSegment(i, j).getBudovy().isPresent()
                        && this.mapa.getSegment(i, j).getBudovy().get() == this.cielBudova) {

                    this.cielX = j * Obrazovka.ROZMER_SEGMENTU
                            + Obrazovka.ROZMER_SEGMENTU / 2.0;
                    this.cielY = i * Obrazovka.ROZMER_SEGMENTU
                            + 70 + Obrazovka.ROZMER_SEGMENTU / 2.0;
                    return;
                }
            }
        }
    }

    private boolean existuje(Budovy budova) {
        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                if (this.mapa.getSegment(i, j).getBudovy().isPresent()
                        && this.mapa.getSegment(i, j).getBudovy().get() == budova) {
                    return true;
                }
            }
        }
        return false;
    }

    private Budovy najdiNajblizsiuBudovu() {
        Budovy najblizsia = null;
        double najblizsiaVzdialenost = Double.MAX_VALUE;

        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                if (this.mapa.getSegment(i, j).getBudovy().isEmpty()) {
                    continue;
                }

                Budovy b = this.mapa.getSegment(i, j).getBudovy().get();
                double stredX = j * Obrazovka.ROZMER_SEGMENTU
                        + Obrazovka.ROZMER_SEGMENTU / 2.0;
                double stredY = i * Obrazovka.ROZMER_SEGMENTU
                        + 70 + Obrazovka.ROZMER_SEGMENTU / 2.0;

                double vzd = this.vzdialenost(this.x, this.y, stredX, stredY);
                if (vzd < najblizsiaVzdialenost) {
                    najblizsiaVzdialenost = vzd;
                    najblizsia = b;
                }
            }
        }

        return najblizsia;
    }

    private void vystrelNaCiel() {
        Kruh strela = new Kruh((int)this.x, (int)this.y);
        strela.zmenPriemer(6);
        strela.zmenFarbu("blue");
        strela.zobraz();

        double dx = this.cielX - this.x;
        double dy = this.cielY - this.y;
        double vzd = Math.sqrt(dx * dx + dy * dy);
        if (vzd == 0) {
            return;
        }

        double krokX = dx / POCET_KROKOV_PROJEKTILU;
        double krokY = dy / POCET_KROKOV_PROJEKTILU;

        new Thread(() -> {
            for (int i = 0; i < POCET_KROKOV_PROJEKTILU; i++) {
                strela.posunVodorovne((int)krokX);
                strela.posunZvisle((int)krokY);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
            }
            strela.skry();

            if (this.cielBudova != null && this.existuje(this.cielBudova)) {
                int noveZivoty = this.cielBudova.getZivoty() - 7;
                if (noveZivoty <= 0) {
                    for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
                        for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                            if (this.mapa.getSegment(i, j).getBudovy().isPresent()
                                    && this.mapa.getSegment(i, j).getBudovy().get() == this.cielBudova) {
                                this.mapa.getSegment(i, j).zruseneBudovy();
                            }
                        }
                    }
                } else {
                    this.cielBudova.setZivoty(noveZivoty);
                }
            }
        }).start();
    }

    private double vzdialenost(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

}