package postavy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//Trieda zodpovedná za správu všetkých jednotiek na hernej mape.
//Zabezpečuje ich aktualizáciu (tik), pridávanie a správu životnosti.
public class SpravcaJednotiek {
    private final List<Jednotka> jednotky;

    //Konštruktor správcu jednotiek.
    //Inicializuje interný zoznam jednotiek ako prázdny ArrayList.
    public SpravcaJednotiek() {
        this.jednotky = new ArrayList<>();
    }

    //Pridá novú jednotku do zoznamu.
    //
    //jednotka – objekt, ktorý implementuje rozhranie Jednotka
    public void pridajJednotku(Jednotka jednotka) {
        this.jednotky.add(jednotka);
    }

    //s pomocou AI
    //Aktualizuje stav všetkých jednotiek.
    //Volá metódu tik() pre každú jednotku.
    //Zoznam automaticky odstráni všetky jednotky, ktoré už nie sú živé.
    public void tik() {
        Iterator<Jednotka> iterator = this.jednotky.iterator();
        while (iterator.hasNext()) {
            Jednotka jednotka = iterator.next();
            jednotka.tik(); //polymorfizmus

            if (!jednotka.jeZivy()) {
                iterator.remove();
            }
        }
    }

    //Vráti aktuálny zoznam všetkých jednotiek.
    //Používa sa napríklad pri výbere cieľa pre kanón.
    public List<Jednotka> getJednotky() {
        return this.jednotky;
    }
}
