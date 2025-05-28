package obraz;

//Inšpirované Zemeplocha
import akcie.Akcia;
import fri.shapesge.StylFontu;
import fri.shapesge.Obdlznik;
import fri.shapesge.BlokTextu;
import mapa.Mapa;


public class Tlacidlo {
    private int sirkaTlacidla;
    //Konštanta určujúca výšku tlačidla (v pixeloch). Používa sa na výpočet rozmerov grafických komponentov tlačidla.
    public static final int VYSKA_TLACIDLA = 19;
    private final int x;
    private final int y;
    private final Akcia akcia;
    private final Obdlznik bg;
    private Obdlznik ram;
    private BlokTextu text;

    Tlacidlo(int x, int y, Akcia akcia) {
        this.x = x;
        this.y = y;
        this.akcia = akcia;

        String nazov = this.akcia.getNazov();
        int pocetZnakov = nazov.length();
        this.sirkaTlacidla = Math.max(80, pocetZnakov * 8);
        if (x + this.sirkaTlacidla > 630) {
            x = 630 - this.sirkaTlacidla;
        }

        this.ram = new Obdlznik(this.x, this.y);
        this.ram.zmenStrany(this.sirkaTlacidla, VYSKA_TLACIDLA);
        this.ram.zmenFarbu("#24495c");

        this.bg = new Obdlznik(this.x + 1, this.y + 1);
        this.bg.zmenStrany(this.sirkaTlacidla - 2, VYSKA_TLACIDLA - 2);
        this.bg.zmenFarbu("#ddf1f1");
        this.bg.zobraz();

        this.text = new BlokTextu(this.akcia.getNazov(), this.x + 5, this.y + 13);
        this.text.zmenFont("Consolas", StylFontu.BOLD, 15);
        this.text.zobraz();
    }

    //Skryje všetky grafické prvky tlačidla (pozadie, rám, text).
    public void skry() {
        this.ram.skry();
        this.bg.skry();
        this.text.skry();
    }

    //Obnoví farbu textu tlačidla na predvolenú čiernu (indikácia neaktívneho stavu).
    public void zrusZvyraznenie() {
        this.text.zmenFarbu("black");
    }

    //Zmení farbu textu tlačidla na červenú, čím vizuálne zvýrazní tlačidlo pod kurzorom.
    public void zvyrazni() {
        this.text.zmenFarbu("#e74c3c");
    }

    boolean obsahujeSuradnicu(int x, int y) {
        return x >= this.x && x <= this.x + this.sirkaTlacidla &&
                y >= this.y && y <= this.y + VYSKA_TLACIDLA;
    }

    //Spustí akciu priradenú tomuto tlačidlu, ktorá upravuje stav mapy.
    //
    //mapa – aktuálna mapa hry, nad ktorou sa akcia vykonáva
    public void vykonaj(Mapa mapa) {
        this.akcia.vykonaj(mapa);
    }
}