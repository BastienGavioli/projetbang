package fr.umontpellier.iut.bang.characters;

public class BartCassidy extends BangCharacter {

    /**
     * Chaque fois qu’il perd un point de vie, Bart Cassidy pioche immédiatement une carte.
     * (cf. Player decrementHealth())
     */

    public BartCassidy() {
        super("Bart Cassidy", 4);
    }

}
