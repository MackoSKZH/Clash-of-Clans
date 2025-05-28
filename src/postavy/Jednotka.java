package postavy;

public interface Jednotka {
    boolean jeNepriatel();
    void tik();
    boolean jeZivy();
    void zran(int kolko);
    double getX();
    double getY();
}
