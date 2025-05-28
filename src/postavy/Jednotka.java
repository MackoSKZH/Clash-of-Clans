package postavy;

//Rozhranie reprezentujúce jednotku v hre – nepriateľskú alebo inú entitu, ktorá sa pohybuje, vykonáva akcie a môže byť zničená.
public interface Jednotka {
    boolean jeNepriatel();
    void tik();
    boolean jeZivy();
    void zran(int kolko);
    double getX();
    double getY();
}
