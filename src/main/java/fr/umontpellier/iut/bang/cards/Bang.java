package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Bang extends OrangeCard {

    /**
     * true quand un effet bang est en cour de jeu. Sert à autoriser les ratés, les planqes et les pouvoirs
     */
    private static boolean bangEffetActive;

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
        bangEffetActive = false;
    }

    public static boolean isBangEffetActive() {
        return bangEffetActive;
    }

    public void bangEffect(Player target){
        bangEffetActive = true;
        System.out.println("A player activate a bang effect");
        List<String> missEffect = new ArrayList<>();
        missEffect.add("Missed");
        target.choose("Jouez un raté ou cliquez sur pass", missEffect, true, true);
        bangEffetActive = false;
    }

    //Méthode non implémenté
    private boolean savedByABarrel(Player target){
        return false;
    }


    @Override
    public void playedBy(Player player) {
        //On sait qu'il y a au moins 1 joueur à porté
        Player target;
        //Selection automatique de la cible si elle est unique
        if(player.getPlayersInRange(player.getWeaponRange()).size()==1){
            target = player.getPlayersInRange(player.getWeaponRange()).get(0);
        }
        //Choix de la cible et verification de la porté
        else{
            target = player.choosePlayer("Sur qui voulez vous tirer ?",
                    player.getPlayersInRange(player.getWeaponRange()), false);
        }

        bangEffect(target);
        player.setBangPlayed(true);
        player.discard(this);

    }

/* //A réactiver quand la méthode getWeapon sera implémenté
    @Override
    public boolean canPlayFromHand(Player player) {
        return ((player.getWeapon()!=null &&
                player.getWeapon().getName().equals("Volcanic"))
                || !player.isBangPlayed())
                && player.getPlayersInRange(player.getWeaponRange()).size()>0;
    }
*/

}
