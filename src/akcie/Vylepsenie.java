package akcie;

import budovy.Budovy;
import main.Segment;
import mapa.Mapa;

public class Vylepsenie implements Akcia {
    private final Segment tentoSegment;
    private final int suma;
    private final Budovy budova;

    public Vylepsenie(Segment tentoSegment) {
        this.tentoSegment = tentoSegment;
        this.budova = this.tentoSegment.getBudovy().orElseThrow();
        this.suma = this.budova.getLevel() * this.budova.getCena();
    }

    @Override
    public String getNazov() {
        return "Vylepsit : " + this.suma;
    }

    @Override
    public void vykonaj(Mapa mapa) {
        this.tentoSegment.zvysLevel(this.suma);
    }
}
