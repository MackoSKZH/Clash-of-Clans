package akcie;

import budovy.Budovy;
import budovy.TovarenBudov;
import budovy.TypBudovy;
import main.Segment;
import mapa.Mapa;

//Trieda reprezentuje akciu. Implementuje rozhranie Akcia, čím zabezpečuje spoločné rozhranie pre rôzne herné akcie.
public class Postavit implements Akcia {
    private final Segment tentoSegment;
    private final TypBudovy typBudovy;
    public Postavit(Segment tentoSegment, TypBudovy typBudovy) {
        this.tentoSegment = tentoSegment;
        this.typBudovy = typBudovy;
    }

    //Vracia názov akcie, ktorý sa zobrazí v hernom rozhraní. Obsahuje typ budovy a jej cenu.
    @Override
    public String getNazov() {
        return "Postavit " +
                this.typBudovy.name().replace("_", " ") +
                " | " + this.typBudovy.getCena();
    }

    //Vykoná samotnú akciu: odpočíta cenu budovy zo zlata a pridá novú inštanciu budovy do zvoleného segmentu.
    @Override
    public void vykonaj(Mapa mapa) {
        this.tentoSegment.nastavZlato(this.typBudovy.getCena());
        Budovy budova = TovarenBudov.vytvorPodlaTypu(this.typBudovy, 1, this.typBudovy.getZivoty());
        this.tentoSegment.setBudovy(budova);
    }
}
