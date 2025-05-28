package main;
//Inšpirované Zemeplocha
import akcie.Akcia;
import budovy.Budovy;

import java.util.ArrayList;
import java.util.Optional;

import mapa.Mapa;

//Trieda reprezentujúca jeden segment (políčko) na hernej mape, ktorý môže obsahovať budovu a poskytuje interakcie.
public class Segment {
    private Budovy budovy;
    private final Hra hra;

    //Konštruktor segmentu. Inicializuje segment a preberá referenciu na hru z mapy.
    public Segment(Mapa mapa) {
        this.budovy = null;
        this.hra = mapa.getHra();
    }

    //Vracia objekt budovy, ak v segmente nejaká je, zabalený v Optional.
    public Optional<Budovy> getBudovy() {
        return Optional.ofNullable(this.budovy);
    }

    //Priradí budovu tomuto segmentu a aktualizuje kapacitu zlata.
    public void setBudovy(Budovy budovy) {
        this.budovy = budovy;
        this.hra.nastavKapacituZlata();
    }

    //Odstráni budovu zo segmentu (nastaví ju na null).
    public void zruseneBudovy() {
        this.budovy = null;
    }

    //Vracia názov obrázka budovy, ak nejaká existuje, inak null.
    public String getObrazok() {
        return this.budovy == null ? null : this.budovy.getObrazok();
    }

    //Vracia textový popis segmentu, obsahujúci názov a level budovy, počet životov a stav zlata.
    public String getText() {
        String text = "";
        if (this.budovy == null) {
            text += "PRÁZDNO";
        } else {
            text = this.budovy.getNazov() + " (lvl. " + this.budovy.getLevel() + ")";
            //text += "\n" + this.budovy.getPopis();
            text += "\nŽivoty: " + this.budovy.getZivoty();
        }

        text += "\nZlato: " + this.hra.getZlato() + "/" + this.hra.getMaxZlato();
        return text;
    }

    //Odpočíta dané množstvo zlata z aktuálneho stavu a aktualizuje kapacitu zlata.
    public void nastavZlato(int noveZlato) {
        int novyStavZlata = this.hra.getZlato() - noveZlato;
        this.hra.nastavZlato(novyStavZlata);
        this.hra.nastavKapacituZlata();
    }

    //Zvýši úroveň budovy, ak má hráč dostatok zlata, a odpočíta jeho hodnotu.
    public void zvysLevel(int suma) {
        int dostupneZlato = this.hra.getZlato();
        if (dostupneZlato >= suma) {
            this.budovy.zvysLevel();
            this.nastavZlato(suma);
        }
    }

    //Vracia aktuálny stav zlata hráča.
    public int getDostupneZlato() {
        return this.hra.getZlato();
    }

    //Nastaví nové hodnoty životov budovy.
    public void zmenZivoty(int noveZivoty) {
        this.budovy.setZivoty(noveZivoty);
    }

    //Vracia zoznam možných akcií, ktoré možno vykonať z tohto segmentu na druhý segment.
    public ArrayList<Akcia> dajAkcieNa(Segment druhySegment) {
        if (this.budovy == null) {
            return new ArrayList<>();
        }

        return this.budovy.dajAkcieNa(this, druhySegment);
    }
}
