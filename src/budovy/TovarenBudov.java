package budovy;

//Továreň (fabrika) na vytváranie inštancií rôznych typov budov na základe enumerácie TypBudovy. Poskytuje centralizované miesto na ich inštancovanie.
public class TovarenBudov {
    //s pomocou AI
    //Statická metóda, ktorá vytvorí a vráti konkrétnu podtriedu Budovy podľa zadaného typu (KANON, ZLATYDOL atď.). Využíva switch na výber konkrétnej triedy.
    public static Budovy vytvorPodlaTypu(TypBudovy typ, int level, int zivoty) {
        return switch (typ) {
            case KANON -> new Kanon(level, zivoty);
            case ZLATYDOL -> new ZlatyDol(level, zivoty);
            case RADNICA, DOM_STAVITELA -> new Budovy(typ, level, zivoty);
        };
    }
}
