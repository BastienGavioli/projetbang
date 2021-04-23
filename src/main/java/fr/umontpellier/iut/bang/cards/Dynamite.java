package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Dynamite extends BlueCard {

    public Dynamite(int value, CardSuit suit) {
        super("Dynamite", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        player.addToInPlay(this);
        Card pioche = player.randomDraw();
        if(pioche.getValue() >= 2 && pioche.getValue() <=9 && pioche.getSuit().toJSON().equals("S")){
            player.decrementHealth(3, null);
            player.discardFromInPlay(player.getCardInPlay("Dynamite"));
        }
        else{
            player.getOtherPlayers();
        }
    }

}
