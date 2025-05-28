package akcie;

import budovy.TovarenBudov;
import main.Segment;
import budovy.Budovy;
import mapa.Mapa;

//Trieda predstavuje hernú akciu premiestnenia budovy z jedného segmentu na iný. Implementuje rozhranie Akcia.
public class Premiestnenie implements Akcia {
    private final Segment tentoSegment;
    private final Segment druhySegment;

    //Verejný konštruktor. Inicializuje akciu s pôvodným a cieľovým segmentom, medzi ktorými sa budova premiestni.
    public Premiestnenie(Segment tentoSegment, Segment druhySegment) {
        this.tentoSegment = tentoSegment;
        this.druhySegment = druhySegment;
    }

    //Vracia reťazec s názvom akcie, ktorý sa zobrazí v používateľskom rozhraní.
    @Override
    public String getNazov() {
        return "Premiestnit";
    }

    //Vykoná akciu premiestnenia: vytvorí novú inštanciu budovy v cieľovom segmente a odstráni pôvodnú zo starého segmentu.
    @Override
    public void vykonaj(Mapa mapa) {
        Budovy budova = this.tentoSegment.getBudovy().orElseThrow();
        Budovy nova = TovarenBudov.vytvorPodlaTypu(budova.getTypBudovy(), budova.getLevel(), budova.getZivoty());
        this.druhySegment.setBudovy(nova);
        this.tentoSegment.zruseneBudovy();

    }
}
