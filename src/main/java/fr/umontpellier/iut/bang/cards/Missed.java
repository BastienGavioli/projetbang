package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Missed extends OrangeCard {

    public Missed(int value, CardSuit suit) {
        super("Missed!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        if(player.getBangCharacter().getName().equals("Calamity Janet")){
            new Bang(getValue(), getSuit()).playedBy(player);
        }
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return Bang.isBangEffetActive()||
                (player.getBangCharacter().getName().equals("Calamity Janet")
                        && new Bang(getValue(), getSuit()).canPlayFromHand(player));
    }
}