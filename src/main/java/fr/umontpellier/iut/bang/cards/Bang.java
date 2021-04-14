package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

public class Bang extends OrangeCard {

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
    }


    public void effectBang(){
        System.out.println("Un bang vient d'être joués");
    }


    @Override
    public boolean canPlayFromHand(Player player) {
        return player.getWeapon().getName().equals("Volcanic")
                || player.isBangPlayed();
    }


}
