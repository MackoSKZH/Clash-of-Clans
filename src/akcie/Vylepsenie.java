package akcie;

import budovy.Budovy;
import main.Segment;
import mapa.Mapa;

//Trieda predstavuje akciu vylepšenia budovy na vyššiu úroveň. Implementuje rozhranie Akcia.
public class Vylepsenie implements Akcia {
    private final Segment tentoSegment;
    private final int suma;
    private final Budovy budova;

    //Verejný konštruktor. Inicializuje akciu pre konkrétny segment. Vypočíta cenu vylepšenia na základe úrovne a základnej ceny budovy.
    public Vylepsenie(Segment tentoSegment) {
        this.tentoSegment = tentoSegment;
        this.budova = this.tentoSegment.getBudovy().orElseThrow();
        this.suma = this.budova.getLevel() * this.budova.getCena();
    }

    //Vracia názov akcie s informáciou o cene vylepšenia, ktorý sa zobrazuje v používateľskom rozhraní.
    @Override
    public String getNazov() {
        return "Vylepsit : " + this.suma;
    }

    //Vykoná vylepšenie budovy v danom segmente, ak má hráč dostatok zlata.
    @Override
    public void vykonaj(Mapa mapa) {
        this.tentoSegment.zvysLevel(this.suma);
    }
}
