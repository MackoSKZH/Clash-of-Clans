package mapa;
import budovy.TypBudovy;
import main.Segment;
import budovy.TovarenBudov;

import main.Hra;
import obraz.HernePole;

public class Mapa {
    private final int pocetSegmentov;
    private final Segment[][] segmenty;
    private final Hra hra;

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

    public void nastavSegmenty() {
        int stred = this.pocetSegmentov / 2;

        this.segmenty[stred][stred].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.RADNICA, 1, TypBudovy.RADNICA.getZivoty()));
        this.segmenty[stred - 1][stred].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.DOM_STAVITELA, 1, TypBudovy.DOM_STAVITELA.getZivoty()));
        this.segmenty[stred][stred + 1].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.ZLATYDOL, 1, TypBudovy.ZLATYDOL.getZivoty()));
        this.segmenty[stred][stred - 1].setBudovy(TovarenBudov.vytvorPodlaTypu(TypBudovy.KANON, 1, TypBudovy.KANON.getZivoty()));
    }

    public Segment getSegment(int x, int y) {
        return this.segmenty[x][y];
    }

    public int getPocetSegmentov() {
        return this.pocetSegmentov;
    }

    public Hra getHra() {
        return this.hra;
    }

    public HernePole getHernePole(int riadok, int stlpec) {
        return this.getHra().getObrazovka().getHernePole(riadok, stlpec);
    }
}
