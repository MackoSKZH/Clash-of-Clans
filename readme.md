# Strategická hra: Clash of Clans

Tento projekt predstavuje strategickú hru vytvorenú v 
jazyku Java s využitím grafickej knižnice **ShapesGE**. 
Cieľom hry je budovanie dediny a jej obrana proti vlnám nepriateľov, ako sú **barbari** a **mágovia**.

## Hraná mechanika

- Hráč buduje a vylepšuje budovy (radnica, zlatý dol, kanón).
- Nepriatelia útočia v pravidelných vlnách každých 20 sekúnd.
- Hráč riadi rozloženie dediny, zber zdrojov a obranu.
- Cieľom je prežiť čo najdlhšie a efektívne manažovať zdroje.

## Herné prvky

| Prvok         | Popis                               |
|---------------|-------------------------------------|
| **Radnica**   | Zvyšuje maximálnu kapacitu zlata.   |
| **Zlatý dol** | Produkuje zlato podľa jeho úrovne.  |
| **Kanón**     | Automaticky útočí na nepriateľov.   |
| **Barbar**    | Blízky útok na najbližiu budovu.    |
| **Mag**       | Útočí z diaľky pomocou projektilov. |

## Architektúra kódu

Projekt je rozdelený do niekoľkých balíčkov.

- `main` - vstupný bod aplikácie.
- `mapa` - správa mapy a segmentov.
- `budovy` - implementácia budov (Radnica, ZlatyDol, Kanon...).
- `postavy` - triedy pre nepriateľov a hráčove jednotky.
- `obraz` - vizualizácia a vykresľovanie.
- `akcie` - akcie vykonávanie hráčom (stavba, vylepšenie, presun...).

Použitý je **polymorfizmus** na správu jednotiek, akcií aj budov.

## Spustenie hry

1. Naklonujte repozitár:
   ```bash
   git clone https://github.com/MackoSKZH/Clash-of-Clans

2. Importujte projekt do IDE (napr. IntelliJ IDEA alebo Eclipse).
3. Pridajte knižnicu **ShapesGE** do classpath.
4. Spustite súbor: `main/Main.java`.

## Ovládanie

- Kliknutím na segment mapy ho vyberiete.
- Druhým klikom na iný segment zobrazíte dostupné akcie.
- Kliknite na tlačidlo pre stavbu, vylepšenie, premiestnenie atď.
- Hra prebieha automaticky (nepriatelia prichádzajú každých 20 sekúnd).

## Herné tipy

- Budujte **zlaté doly** kvôli stabilnému príjmu.
- Vylepšujte **radnicu**, aby ste mohli uchovať viac zlata.
- Strategicky rozmiestňujte kanóny - chránia okolie.
- Sledujte útoky a **opravujte alebo premiestňujte budovy podľa potreby.

## Licencia

Tento projekt bol vytvorený na vzdelávacie účely.