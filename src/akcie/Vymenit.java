package akcie;

import budovy.Budovy;
import budovy.TovarenBudov;
import main.Segment;
import mapa.Mapa;

public class Vymenit implements Akcia {
    private final Segment tentoSegment;
    private final Segment druhySegment;
    private final Budovy budova1;
    private final Budovy budova2;

    public Vymenit(Segment tentoSegment, Segment druhySegment) {
        this.tentoSegment = tentoSegment;
        this.druhySegment = druhySegment;
        this.budova1 = this.tentoSegment.getBudovy().orElseThrow();
        this.budova2 = this.druhySegment.getBudovy().orElseThrow();
    }

    @Override
    public String getNazov() {
        return "Vymeniť: " +
                this.budova1.getNazov() + " | " +
                this.budova2.getNazov();
    }

    @Override
    public void vykonaj(Mapa mapa) {
        Budovy nova1 = TovarenBudov.vytvorPodlaTypu(this.budova1.getTypBudovy(), this.budova1.getLevel(), this.budova1.getZivoty());
        Budovy nova2 = TovarenBudov.vytvorPodlaTypu(this.budova2.getTypBudovy(), this.budova2.getLevel(), this.budova2.getZivoty());
        this.druhySegment.setBudovy(nova1);
        this.tentoSegment.setBudovy(nova2);

    }
}
