package akcie;

import budovy.Budovy;
import budovy.TovarenBudov;
import main.Segment;
import mapa.Mapa;

//Trieda reprezentuje akciu výmeny dvoch budov medzi dvoma segmentmi. Implementuje rozhranie Akcia.
public class Vymenit implements Akcia {
    private final Segment tentoSegment;
    private final Segment druhySegment;
    private final Budovy budova1;
    private final Budovy budova2;

    //Konštruktor inicializuje akciu výmeny. Získava inštancie budov z oboch segmentov, ktoré sa budú vymieňať.
    public Vymenit(Segment tentoSegment, Segment druhySegment) {
        this.tentoSegment = tentoSegment;
        this.druhySegment = druhySegment;
        this.budova1 = this.tentoSegment.getBudovy().orElseThrow();
        this.budova2 = this.druhySegment.getBudovy().orElseThrow();
    }

    //Vracia názov akcie s názvami budov, ktoré sa budú meniť. Používa sa pre zobrazenie v menu akcií.
    @Override
    public String getNazov() {
        return "Vymeniť: " +
                this.budova1.getNazov() + " | " +
                this.budova2.getNazov();
    }

    //Vykoná výmenu budov medzi dvoma segmentmi. Vytvorí nové inštancie oboch budov so zachovanými vlastnosťami (typ, level, životy).
    @Override
    public void vykonaj(Mapa mapa) {
        Budovy nova1 = TovarenBudov.vytvorPodlaTypu(this.budova1.getTypBudovy(), this.budova1.getLevel(), this.budova1.getZivoty());
        Budovy nova2 = TovarenBudov.vytvorPodlaTypu(this.budova2.getTypBudovy(), this.budova2.getLevel(), this.budova2.getZivoty());
        this.druhySegment.setBudovy(nova1);
        this.tentoSegment.setBudovy(nova2);

    }
}
