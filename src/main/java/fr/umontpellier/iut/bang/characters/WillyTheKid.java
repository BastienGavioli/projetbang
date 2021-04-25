package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Bang;

public class WillyTheKid extends BangCharacter {

    public WillyTheKid() {
        super("Willy the Kid", 4);
    }

    /*
     * Willy the Kid peut jouer autant de fois de cartes Bang! qu'il possède, au lieu d'une seule fois pour
     * les autres personnages du jeu (cf. classe Bang, méthode canPlayFromHand(Player player)).
     */


}
