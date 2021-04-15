package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Missed extends OrangeCard {

    public Missed(int value, CardSuit suit) {
        super("Missed!", value, suit);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return Bang.isBangEffetActive();
    }
}
