package obraz;

import fri.shapesge.BlokTextu;
import fri.shapesge.Obdlznik;
import fri.shapesge.Obrazok;
import fri.shapesge.StylFontu;

public class HernePole {
    private Obrazok obrazok;
    private final Obdlznik bg;
    private final Obdlznik ram;
    private BlokTextu text;

    private final int x;
    private final int y;
    private final int velkost;

    private String farba;
    private boolean jeZvyraznene;

    public HernePole(int x, int y, int velkost) {
        this.x = x;
        this.y = y;
        this.velkost = velkost;

        this.farba = "#53a318";
        this.jeZvyraznene = false;

        this.ram = new Obdlznik(this.x, this.y);
        this.ram.zmenStrany(velkost, velkost);
        this.ram.zmenFarbu(this.farba);
        this.ram.zobraz();

        this.bg = new Obdlznik(this.x + 5, this.y + 5);
        this.bg.zmenStrany(velkost - 10, velkost - 10);
        this.bg.zmenFarbu(this.farba);
        this.bg.zobraz();
    }

    public void zmenFarbu(String farba) {
        this.farba = farba;
        this.bg.zmenFarbu(this.farba);
        if (!this.jeZvyraznene) {
            this.ram.zmenFarbu(this.farba);
        }
    }

    void nastavObrazok(String cestaKObrazku) {
        if (this.obrazok == null && cestaKObrazku != null) {
            this.obrazok = new Obrazok(cestaKObrazku, this.x + 3, this.y + 3);
            this.obrazok.zobraz();
        } else if (this.obrazok != null && cestaKObrazku != null) {
            this.obrazok.zmenObrazok(cestaKObrazku);
        } else if (this.obrazok != null) {
            this.obrazok.skry();
            this.obrazok = null;
        }
    }

    public void nastavText(String text) {
        if (text == null) {
            text = "";
        }

        if (text.length() > 10) {
            text = String.join("\n", text.split(" "));
        }

        if (this.text != null) {
            this.text.skry();
        }
        this.text = new BlokTextu(text, this.x + 5, this.y + this.velkost - (4 + 10 * (text.split("\n").length - 1)));
        this.text.zmenFont("Consolas", StylFontu.BOLD, 10);
    }

    public boolean obsahujeSuranicu(int x, int y) {
        return x >= this.x && x <= this.x + this.velkost && y >= this.y && y <= this.y + this.velkost;
    }

    public void zvyrazniSegment(boolean zvyraznenie) {
        this.jeZvyraznene = zvyraznenie;
        if (this.jeZvyraznene) {
            this.ram.zmenFarbu("#f1c40f");
        } else {
            this.ram.zmenFarbu(this.farba);
        }
    }

}
