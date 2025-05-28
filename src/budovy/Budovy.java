package budovy;

import akcie.Akcia;
import akcie.Postavit;
import akcie.Premiestnenie;
import akcie.Vylepsenie;
import akcie.Vymenit;

import main.Segment;
import mapa.Mapa;

import java.util.ArrayList;

public class Budovy {
    private final TypBudovy typBudovy;
    private int zivoty;
    private int level;
    private String obrazok;
    private final int cena;

    public Budovy(TypBudovy typBudovy, int level, int zivoty) {
        this.typBudovy = typBudovy;
        this.zivoty = this.typBudovy.getZivoty();
        this.level = this.typBudovy.getLevel();
        this.obrazok = this.typBudovy.getObrazok();
        this.cena = this.typBudovy.getCena();

        this.nastavLevel(level);
        this.setZivoty(zivoty);
    }

    public String getNazov() {
        return this.typBudovy.name().replace("_", " ");
    }

    public TypBudovy getTypBudovy() {
        return this.typBudovy;
    }

    public String getObrazok() {
        return this.obrazok;
    }

    public void zmenObrazok() {
        this.obrazok = this.typBudovy.name() + this.level + ".png";
    }

    public int getZivoty() {
        return this.zivoty;
    }

    public void setZivoty(int noveZivoty) {
        this.zivoty = noveZivoty;
    }

    public void zvysLevel() {
        if (5 >= (this.level + 1)) {
            this.level++;
            this.zmenObrazok();
        }
    }

    private void nastavLevel(int level) {
        this.level = level;
        this.zmenObrazok();
    }

    public int getLevel() {
        return this.level;
    }

    public int getCena() {
        return this.cena;
    }

    public ArrayList<Akcia> dajAkcieNa(Segment tentoSegment, Segment druhySegment) {
        ArrayList<Akcia> akcie = new ArrayList<>();
        if (tentoSegment.getBudovy().isPresent() && tentoSegment.getBudovy().get().getTypBudovy() == TypBudovy.DOM_STAVITELA) {
            TypBudovy[] nepovoleneBudovy = {TypBudovy.RADNICA, TypBudovy.DOM_STAVITELA};

            for (TypBudovy typBudovy1 : TypBudovy.values()) {
                boolean jeNepovolena = false;
                for (TypBudovy zakazany : nepovoleneBudovy) {
                    if (typBudovy1 == zakazany) {
                        jeNepovolena = true;
                        break;
                    }
                }
                if (jeNepovolena) {
                    continue;
                }
                if (typBudovy1.getCena() <= tentoSegment.getDostupneZlato()) {
                    if (druhySegment.getBudovy().isEmpty()) {
                        akcie.add(new Postavit(druhySegment, typBudovy1));
                    }
                }
            }
        }
        if (tentoSegment.getBudovy().isPresent() && tentoSegment == druhySegment) {
            if (tentoSegment.getBudovy().orElseThrow().getLevel() < 5) {
                if (!tentoSegment.getBudovy().orElseThrow().getNazov().equals("DOM STAVITELA")) {
                    akcie.add(new Vylepsenie(tentoSegment));
                }
            }
        }
        if (tentoSegment.getBudovy().isPresent() && druhySegment.getBudovy().isEmpty()) {
            if (tentoSegment.getBudovy().orElseThrow().getTypBudovy() != TypBudovy.KANON) {
                akcie.add(new Premiestnenie(tentoSegment, druhySegment));
            }
        }
        if (tentoSegment.getBudovy().isPresent() && druhySegment.getBudovy().isPresent()) {
            if (tentoSegment != druhySegment) {
                akcie.add(new Vymenit(tentoSegment, druhySegment));
            }
        }
        return akcie;
    }

    public void vykonajKrok(Mapa mapa, int x, int y) {
        //zakladny typ budovy nic nerobi
    }
}