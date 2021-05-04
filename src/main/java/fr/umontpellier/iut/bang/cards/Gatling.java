package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Gatling extends OrangeCard {

    public Gatling(int value, CardSuit suit) {
        super("Gatling", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Bang.setGatlingEffect(true);
        //La gateling fait un effet bang sur chaque joueur
        for(Player p : player.getOtherPlayers()){
            Bang.bangEffect(p, player);
        }
        Bang.setGatlingEffect(false);
    }
}
