package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Jail extends BlueCard {
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        Card pioche = player.randomDraw();
        player.discardFromInPlay(player.getCardInPlay("Jail"));
        if(pioche.getSuit().toJSON().equals("H")){
           player.playTurn();
        }
    }
}
