package main;

import akcie.Akcia;
import budovy.Budovy;

import java.util.ArrayList;
import java.util.Optional;

import mapa.Mapa;

public class Segment {
    private Budovy budovy;
    private final Hra hra;

    public Segment(Mapa mapa) {
        this.budovy = null;
        this.hra = mapa.getHra();
    }

    public Optional<Budovy> getBudovy() {
        return Optional.ofNullable(this.budovy);
    }

    public void setBudovy(Budovy budovy) {
        this.budovy = budovy;
        this.hra.nastavKapacituZlata();
    }

    public void zruseneBudovy() {
        this.budovy = null;
    }

    public String getObrazok() {
        return this.budovy == null ? null : this.budovy.getObrazok();
    }

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

    public void nastavZlato(int noveZlato) {
        int novyStavZlata = this.hra.getZlato() - noveZlato;
        this.hra.nastavZlato(novyStavZlata);
        this.hra.nastavKapacituZlata();
    }

    public void zvysLevel(int suma) {
        int dostupneZlato = this.hra.getZlato();
        if (dostupneZlato >= suma) {
            this.budovy.zvysLevel();
            this.nastavZlato(suma);
        }
    }

    public int getDostupneZlato() {
        return this.hra.getZlato();
    }

    public void zmenZivoty(int noveZivoty) {
        this.budovy.setZivoty(noveZivoty);
    }

    public ArrayList<Akcia> dajAkcieNa(Segment druhySegment) {
        if (this.budovy == null) {
            return new ArrayList<>();
        }

        return this.budovy.dajAkcieNa(this, druhySegment);
    }
}
