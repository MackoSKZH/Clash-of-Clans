package akcie;

import mapa.Mapa;

public interface Akcia {
    String getNazov();
    void vykonaj(Mapa mapa);
}
