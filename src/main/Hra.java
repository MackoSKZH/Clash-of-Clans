package main;

import budovy.TypBudovy;
import mapa.Mapa;
import obraz.Obrazovka;

import fri.shapesge.Manazer;
import postavy.Barbar;
import postavy.Mag;
import postavy.SpravcaJednotiek;

public class Hra {
    private final Mapa mapa;
    private final Obrazovka obraz;
    private int zlato;
    private int maxZlato;
    private final Manazer manazer;

    private final SpravcaJednotiek spravcaJednotiek;

    private long poslednaVlna;
    private final long intervalVlny;
    private final int pocetNaVlnu;

    public Hra(int pocetSegmentov) {
        this.mapa = new Mapa(this, pocetSegmentov);
        this.mapa.nastavSegmenty();
        this.obraz = new Obrazovka(this.mapa, this);
        this.obraz.zobraz();

        this.zlato = 0;
        this.maxZlato = 0;

        this.poslednaVlna = 0;
        this.intervalVlny = 60_000;
        this.pocetNaVlnu = 5;

        this.spravcaJednotiek = new SpravcaJednotiek();

        this.manazer = new Manazer();
        this.manazer.spravujObjekt(this);

        this.nastavKapacituZlata();
    }

    public void tik() {
        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                var segment = this.mapa.getSegment(i, j);
                if (segment.getBudovy().isPresent()) {
                    segment.getBudovy().get().vykonajKrok(this.mapa, i, j); //polymorfizmus
                }
            }
        }
        this.obraz.zobraz();

        this.spravcaJednotiek.tik();

        long teraz = System.currentTimeMillis();
        if (teraz - this.poslednaVlna >= this.intervalVlny) {
            this.poslednaVlna = teraz;

            for (int i = 0; i < this.pocetNaVlnu; i++) {
                double[] pozicia = this.generujNahodnyOkrajovyBod();
                Barbar barbar = new Barbar(pozicia[0], pozicia[1], this.mapa, this.manazer);
                this.spravcaJednotiek.pridajJednotku(barbar);
            }
        }

        if (teraz - this.poslednaVlna >= 10_000) {
            this.poslednaVlna = teraz;
            double[] pos = this.generujNahodnyOkrajovyBod();
            Mag mag = new Mag(pos[0], pos[1], this.mapa, this.manazer);
            this.spravcaJednotiek.pridajJednotku(mag);
        }
    }

    public void nastavKapacituZlata() {
        int kapacita = 0;
        for (int i = 0; i < this.mapa.getPocetSegmentov(); i++) {
            for (int j = 0; j < this.mapa.getPocetSegmentov(); j++) {
                if (this.mapa.getSegment(i, j).getBudovy().isPresent()) {
                    if (this.mapa.getSegment(i, j).getBudovy().get().getTypBudovy() == TypBudovy.RADNICA) {
                        kapacita += this.mapa.getSegment(i, j).getBudovy().get().getLevel() * 500;
                    } else if (this.mapa.getSegment(i, j).getBudovy().get().getTypBudovy() == TypBudovy.ZLATYDOL) {
                        kapacita += this.mapa.getSegment(i, j).getBudovy().get().getLevel() * 100;
                    }
                }
            }
        }
        this.maxZlato = kapacita;
    }

    public void nastavZlato(int noveZlato) {
        this.zlato = noveZlato;
    }

    public int getZlato() {
        return this.zlato;
    }

    public int getMaxZlato() {
        return this.maxZlato;
    }

    public SpravcaJednotiek getSpravcaJednotiek() {
        return this.spravcaJednotiek;
    }

    private double[] generujNahodnyOkrajovyBod() {
        int mapaSize = this.mapa.getPocetSegmentov();
        int maxPixel = mapaSize * 70;

        double x;
        double y;
        int strana = (int)(Math.random() * 4);

        switch (strana) {
            case 0 -> {
                x = Math.random() * maxPixel;
                y = 70;
            }
            case 1 -> {
                x = Math.random() * maxPixel;
                y = 70 + maxPixel;
            }
            case 2 -> {
                x = 0;
                y = 70 + Math.random() * maxPixel;
            }
            case 3 -> {
                x = maxPixel;
                y = 70 + Math.random() * maxPixel;
            }
            default -> throw new IllegalStateException("Neplatná strana");
        }
        return new double[]{x, y};
    }

    public Obrazovka getObrazovka() {
        return this.obraz;
    }

}