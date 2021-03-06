package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Saloon extends OrangeCard {

    public Saloon(int value, CardSuit suit) {
        super("Saloon", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        player.incrementHealth(1);
        for(Player p : player.getOtherPlayers()){
            p.incrementHealth(1);
        }
    }
}
