package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.characters.BangCharacter;

public class Barrel extends BlueCard {

    public Barrel(int value, CardSuit suit) {
        super("Barrel", value, suit);
    }

    public static boolean savePlayer(Player target){
        Card card = target.getBangCharacter().randomDraw(target);
        return card.getSuit().toJSON().equals("H");
    }

}
