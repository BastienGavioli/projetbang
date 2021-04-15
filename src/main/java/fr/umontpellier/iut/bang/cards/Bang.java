package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Bang extends OrangeCard {

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
    }


    public void bangEffect(Player defender){
        System.out.println("A player activate a bang effect");
        List<String> missEffect = new ArrayList<>();
        missEffect.add("miss");
        defender.choose("Jouez un raté ou cliquez sur pass", missEffect, true, true);

    }


    @Override
    public void playedBy(Player player) {
        //On sait qu'il y a au moins 1 joueur à porté
        Player defender;
        //Selection automatique de la cible si elle est unique
        if(player.getPlayersInRange(player.getWeaponRange()).size()==1){
            defender = player.getPlayersInRange(player.getWeaponRange()).get(0);
        }
        //Choix de la cible et verification de la porté
        else{
            defender = player.choosePlayer("Sur qui voulez vous tirer ?",
                    player.getPlayersInRange(player.getWeaponRange()), false);
        }

        bangEffect(defender);
        player.setBangPlayed(true);
        player.discard(this);

    }


    @Override
    public boolean canPlayFromHand(Player player) {
        return ((player.getWeapon()!=null &&
                player.getWeapon().getName().equals("Volcanic"))
                || !player.isBangPlayed())
                && player.getPlayersInRange(player.getWeaponRange()).size()>0;
    }


}
