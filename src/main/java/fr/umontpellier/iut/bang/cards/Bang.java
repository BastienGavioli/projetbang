package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

public class Bang extends OrangeCard {

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
    }


    public void effectBang(Player shooter){
        System.out.println("A player activate a bang effect");
    }


    @Override
    public void playedBy(Player player) {
        effectBang(player);
        player.setBangPlayed(true);
        player.discard(this);
    }


    @Override
    public boolean canPlayFromHand(Player player) {
        return (player.getWeapon()!=null &&
                player.getWeapon().getName().equals("Volcanic"))
                || !player.isBangPlayed();
    }


}
