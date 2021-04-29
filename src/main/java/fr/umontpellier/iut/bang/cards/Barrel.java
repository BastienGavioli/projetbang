package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;

public class Barrel extends BlueCard {

    public Barrel(int value, CardSuit suit) {
        super("Barrel", value, suit);
    }

    public static boolean savePlayer(Game game){
        Card card = game.drawCard();
        game.addToDiscard(card);
        return card.getSuit().toJSON().equals("H");
    }

}
