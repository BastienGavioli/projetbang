package fr.umontpellier.iut.bang.characters;

public class WillyTheKid extends BangCharacter {

    /**
     * Willy the Kid peut jouer autant de fois de cartes Bang! qu'il possède, au lieu d'une seule fois pour
     * les autres personnages du jeu (cf. classe Bang, méthode canPlayFromHand(Player player)).
     */

    public WillyTheKid() {
        super("Willy the Kid", 4);
    }
}
