package budovy;

import mapa.Mapa;
import main.Hra;

//Podtrieda Budovy, reprezentuje ekonomickú budovu, ktorá produkuje zlato pre hráča počas hry.
public class ZlatyDol extends Budovy {
    //Konštruktor, ktorý vytvára inštanciu ZlatyDol s daným levelom a počtom životov. Nastavuje typ budovy na ZLATYDOL.
    public ZlatyDol(int level, int zivoty) {
        super(TypBudovy.ZLATYDOL, level, zivoty);
    }

    //Metóda vykonávaná v každom hernom kroku. Pridáva zlato hráčovi podľa úrovne budovy, ale len ak ešte nebola dosiahnutá maximálna kapacita zlata.
    @Override
    public void vykonajKrok(Mapa mapa, int x, int y) {
        Hra hra = mapa.getHra();
        int maxZlato = hra.getMaxZlato();
        int zlato = hra.getZlato();
        if (maxZlato > zlato) {
            hra.nastavZlato(hra.getZlato() + this.getLevel() * 2);
        } else if (zlato > maxZlato) {
            hra.nastavZlato(maxZlato);
        }
    }
}