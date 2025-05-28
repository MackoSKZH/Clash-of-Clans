package akcie;

import budovy.TovarenBudov;
import main.Segment;
import budovy.Budovy;
import mapa.Mapa;

public class Premiestnenie implements Akcia {
    private final Segment tentoSegment;
    private final Segment druhySegment;

    public Premiestnenie(Segment tentoSegment, Segment druhySegment) {
        this.tentoSegment = tentoSegment;
        this.druhySegment = druhySegment;
    }

    @Override
    public String getNazov() {
        return "Premiestnit";
    }

    @Override
    public void vykonaj(Mapa mapa) {
        Budovy budova = this.tentoSegment.getBudovy().orElseThrow();
        Budovy nova = TovarenBudov.vytvorPodlaTypu(budova.getTypBudovy(), budova.getLevel(), budova.getZivoty());
        this.druhySegment.setBudovy(nova);
        this.tentoSegment.zruseneBudovy();

    }
}
