package fr.umontpellier.iut.bang.characters;

public class VultureSam extends BangCharacter {

    /**
     * Dès qu’un personnage est éliminé de la partie, Sam prend toutes les cartes que ce joueur avait
     * en main et en jeu, et il les ajoute à sa propre main.
     * (cf. Player.decrementHealth())
     */

    public VultureSam() {
        super("Vulture Sam", 4);
    }
}
