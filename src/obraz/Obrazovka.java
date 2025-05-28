package obraz;

//Inšpirované Zemeplocha
import akcie.Akcia;
import fri.shapesge.BlokTextu;
import fri.shapesge.Manazer;
import fri.shapesge.StylFontu;
import main.Segment;
import mapa.Mapa;
import main.Hra;

import java.util.ArrayList;

//Zodpovedá za vizuálne vykreslenie a interakciu herného poľa a UI prvkov (akcií, výber segmentov).
public class Obrazovka {
    //Konštanta určujúca veľkosť jedného segmentu v pixeloch.
    public static final int ROZMER_SEGMENTU = 70;

    private final HernePole[][] herneSegmenty;
    private final Manazer manazer;

    private HernePole zvolenyHernySegment;
    private Segment zvolenySegment;

    private final BlokTextu zvolenySegmentText;
    private final Mapa mapa;
    private final Hra hra;

    private ArrayList<Tlacidlo> akcie;

    //Konštruktor vytvárajúci hernú obrazovku vrátane mriežky políčok, textových a grafických prvkov.
    //
    //mapa – referenčná mapa hry
    //
    //hra – samotná hra (logika)
    public Obrazovka(Mapa mapa, Hra hra) {
        this.mapa = mapa;
        this.hra = hra;
        this.manazer = new Manazer();
        this.herneSegmenty = new HernePole[this.mapa.getPocetSegmentov()][this.mapa.getPocetSegmentov()];

        for (int i = 0; i < this.herneSegmenty.length; i++) {
            for (int j = 0; j < this.herneSegmenty[i].length; j++) {
                this.herneSegmenty[i][j] = new HernePole(j * ROZMER_SEGMENTU, i * ROZMER_SEGMENTU + 70, ROZMER_SEGMENTU);
            }
        }

        this.zvolenySegmentText = new BlokTextu("", 10, 20);
        this.zvolenySegmentText.zmenFont("Consolas", StylFontu.BOLD, 12);
        this.zvolenySegmentText.zobraz();

        this.akcie = new ArrayList<>();
        this.manazer.spravujObjekt(this);
    }

    //Aktualizuje grafické zobrazenie všetkých segmentov vrátane ich obrázkov a textov.
    public void zobraz() {
        for (int i = 0; i < this.herneSegmenty.length; i++) {
            for (int j = 0; j < this.herneSegmenty[i].length; j++) {
                Segment segment = this.mapa.getSegment(i, j);
                this.herneSegmenty[i][j].nastavObrazok(segment.getObrazok());
                this.herneSegmenty[i][j].nastavText(segment.getText());
            }
        }

        if (this.zvolenySegment != null) {
            this.zvolenySegmentText.zmenText(this.zvolenySegment.getText());
        }
    }

    //Spracuje kliknutie na súradnice – vyberie segment alebo vykoná príslušnú akciu.
    //
    //x, y – súradnice kliknutia
    public void vyberSuradnice(int x, int y) {
        for (Tlacidlo akcia : this.akcie) {
            if (akcia.obsahujeSuradnicu(x, y)) {
                akcia.vykonaj(this.mapa);
            }
        }
        if (!this.akcie.isEmpty()) {
            this.skryAkcie();
            this.zobraz();
            return;
        }

        for (int i = 0; i < this.herneSegmenty.length; i++) {
            for (int j = 0; j < this.herneSegmenty[i].length; j++) {
                HernePole segment = this.herneSegmenty[i][j];
                if (segment.obsahujeSuranicu(x, y)) {
                    if (this.zvolenyHernySegment != null) {
                        this.zvolenyHernySegment.zvyrazniSegment(false);
                    }

                    this.zvolenySegment = this.mapa.getSegment(i, j);
                    this.zvolenyHernySegment = segment;
                    this.zvolenyHernySegment.zvyrazniSegment(true);
                    this.zvolenySegmentText.zmenText(this.zvolenySegment.getText());
                }
            }
        }
        this.skryAkcie();
    }


    //Otvorí menu akcií (tlačidiel) na základe výberu druhého segmentu.
    public void otvorAkcie(int x, int y) {
        this.skryAkcie();
        if (this.zvolenyHernySegment == null) {
            return;
        }
        for (int i = 0; i < this.herneSegmenty.length; i++) {
            for (int j = 0; j < this.herneSegmenty[i].length; j++) {
                HernePole segment = this.herneSegmenty[i][j];
                if (segment.obsahujeSuranicu(x, y)) {
                    var aktualnaAkcia = this.zvolenySegment.dajAkcieNa(this.mapa.getSegment(i, j));
                    for (Akcia akcia : aktualnaAkcia) {
                        var tlacidlo = new Tlacidlo(x, y + this.akcie.size() * 18, akcia);
                        this.akcie.add(tlacidlo);
                    }
                }
            }
        }
    }

    //Reaguje na pohyb myši nad akciami (zvýraznenie aktívneho tlačidla).
    public void mouseMove(int x, int y) {
        for (Tlacidlo tlacidlo : this.akcie) {
            tlacidlo.zrusZvyraznenie();
            if (tlacidlo.obsahujeSuradnicu(x, y)) {
                tlacidlo.zvyrazni();
            }
        }
    }

    //Skryje a odstráni všetky aktuálne zobrazené tlačidlá akcií.
    public void skryAkcie() {
        for (Tlacidlo tlacidlo : this.akcie) {
            tlacidlo.skry();
        }
        this.akcie.clear();
    }

    //Vráti grafické herné pole na daných súradniciach v mriežke.
    //
    //i, j – riadok a stĺpec segmentu
    public HernePole getHernePole(int i, int j) {
        return this.herneSegmenty[i][j];
    }
}
