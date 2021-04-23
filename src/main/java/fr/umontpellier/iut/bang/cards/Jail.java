package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Jail extends BlueCard {
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        player.addToInPlay(this);
        Card pioche = player.randomDraw();
        player.discardFromInPlay(player.getCardInPlay("Jail"));
        if(pioche.getSuit().toJSON().equals("H")){
           player.playTurn();
        }
    }
}
