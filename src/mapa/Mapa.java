package mapa;
import budovy.TypBudovy;
import main.Segment;
import budovy.TovarenBudov;

import main.Hra;
import obraz.HernePole;

//Trieda reprezentujúca hernú mapu, ktorá obsahuje dvojrozmerné pole segmentov a referenciu na hru.
public class Mapa {
    private final int pocetSegmentov;
    private final Segment[][] segmenty;
    private final Hra hra;

    //Konštruktor mapy. Inicializuje mriežku segmentov podľa zadaného počtu a priradí odkaz na hru.
    public Mapa(Hra hra, int pocet) {
        this.hra = hra;
        this.pocetSegmentov = pocet;
        this.segmenty = new Segment[this.pocetSegmentov][this.pocetSegmentov];
        for (int i = 0; i < this.segmenty.length; i++) {
            for (int j = 0; j < this.segmenty[i].length; j++) {
                this.segmenty[i][j] = new Segment(this);
            }
        }
    }

    //Nastaví predvolené budovy do stredových segmentov mapy (radnica, dom staviteľa, zlatý dôl, kanón).
    public void nastavSegmenty() {
        int stred = this.pocetSegmentov / 2;

        this.segmenty[stred][stred].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.RADNICA, 1, TypBudovy.RADNICA.getZivoty()));
        this.segmenty[stred - 1][stred].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.DOM_STAVITELA, 1, TypBudovy.DOM_STAVITELA.getZivoty()));
        this.segmenty[stred][stred + 1].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.ZLATYDOL, 1, TypBudovy.ZLATYDOL.getZivoty()));
        this.segmenty[stred][stred - 1].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.KANON, 1, TypBudovy.KANON.getZivoty()));
    }

    //Vracia segment na daných súradniciach mapy.
    public Segment getSegment(int x, int y) {
        return this.segmenty[x][y];
    }

    //Vracia počet segmentov mapy v jednom rozmere (mapa je štvorcová).
    public int getPocetSegmentov() {
        return this.pocetSegmentov;
    }

    //Vracia referenciu na aktuálnu inštanciu hry.
    public Hra getHra() {
        return this.hra;
    }

    //Vracia herné pole z obrazovky na základe pozície segmentu v mape.
    public HernePole getHernePole(int riadok, int stlpec) {
        return this.getHra().getObrazovka().getHernePole(riadok, stlpec);
    }
}
