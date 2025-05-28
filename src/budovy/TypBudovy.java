package budovy;

//Enum, ktorý reprezentuje rôzne druhy budov dostupné v hre, ako RADNICA, KANON, ZLATYDOL a DOM_STAVITELA. Každý typ má preddefinovaný obrázok, počet životov, počiatočný level a cenu.
public enum TypBudovy {
    RADNICA("RADNICA1.png", 500, 1, 1500),
    KANON("KANON1.png", 50, 1, 400),
    ZLATYDOL("ZLATYDOL1.png", 40, 1, 500),
    DOM_STAVITELA("DOM_STAVITELA1.png", 50, 0, 0);

    private final String obrazok;
    private final int zivoty;
    private final int level;
    private final int cena;

    TypBudovy(String obrazok, int zivoty, int level, int cena) {
        this.obrazok = obrazok;
        this.zivoty = zivoty;
        this.level = level;
        this.cena = cena;
    }

    //Vracia názov súboru s obrázkom reprezentujúcim daný typ budovy.
    public String getObrazok() {
        return this.obrazok;
    }
    //Vracia počiatočný počet životov danej budovy.
    public int getZivoty() {
        return this.zivoty;
    }
    //Vracia počiatočnú úroveň (level) budovy.
    public int getLevel() {
        return this.level;
    }

    //Vracia cenu budovy (v zlate) potrebnú na jej postavenie.
    public int getCena() {
        return this.cena;
    }
}