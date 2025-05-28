package budovy;

import mapa.Mapa;
import main.Hra;

public class ZlatyDol extends Budovy {
    public ZlatyDol(int level, int zivoty) {
        super(TypBudovy.ZLATYDOL, level, zivoty);
    }

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