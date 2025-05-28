package postavy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpravcaJednotiek {
    private final List<Jednotka> jednotky;

    public SpravcaJednotiek() {
        this.jednotky = new ArrayList<>();
    }

    public void pridajJednotku(Jednotka jednotka) {
        this.jednotky.add(jednotka);
    }

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

    public List<Jednotka> getJednotky() {
        return this.jednotky;
    }
}
