package akcie;

import budovy.Budovy;
import budovy.TovarenBudov;
import budovy.TypBudovy;
import main.Segment;
import mapa.Mapa;

public class Postavit implements Akcia {
    private final Segment tentoSegment;
    private final TypBudovy typBudovy;
    public Postavit(Segment tentoSegment, TypBudovy typBudovy) {
        this.tentoSegment = tentoSegment;
        this.typBudovy = typBudovy;
    }

    @Override
    public String getNazov() {
        return "Postavit " +
                this.typBudovy.name().replace("_", " ") +
                " | " + this.typBudovy.getCena();
    }

    @Override
    public void vykonaj(Mapa mapa) {
        this.tentoSegment.nastavZlato(this.typBudovy.getCena());
        Budovy budova = TovarenBudov.vytvorPodlaTypu(this.typBudovy, 1, this.typBudovy.getZivoty());
        this.tentoSegment.setBudovy(budova);
    }
}
