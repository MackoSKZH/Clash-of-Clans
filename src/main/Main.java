package main;

//Trieda obsahujúca vstupný bod programu. Spúšťa hru.
public class Main {
    //Hlavná metóda programu. Vytvára novú inštanciu hry so zadaným počtom segmentov (9×9 mapa).
    public static void main(String[] args) {
        Hra hra = new Hra(9);
    }
}