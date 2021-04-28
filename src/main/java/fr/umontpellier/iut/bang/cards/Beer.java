package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Beer extends OrangeCard {

    public Beer(int value, CardSuit suit) {
        super("Beer", value, suit);
    }

    public void heal(Player player) {
        player.incrementHealth(1);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        this.heal(player);
    }
}
