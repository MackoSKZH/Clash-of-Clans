package budovy;

public class TovarenBudov {
    public static Budovy vytvorPodlaTypu(TypBudovy typ, int level, int zivoty) {
        return switch (typ) {
            case KANON -> new Kanon(level, zivoty);
            case ZLATYDOL -> new ZlatyDol(level, zivoty);
            case RADNICA, DOM_STAVITELA -> new Budovy(typ, level, zivoty);
        };
    }
}
