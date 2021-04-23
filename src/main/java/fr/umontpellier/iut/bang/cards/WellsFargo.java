package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class WellsFargo extends OrangeCard {
    public WellsFargo(int value, CardSuit suit) {
        super("Wells Fargo", value, suit);
    }

    public void playedBy(Player player){
        player.drawToHand();
        player.drawToHand();
        player.drawToHand();
        player.discard(this);
    }
}
