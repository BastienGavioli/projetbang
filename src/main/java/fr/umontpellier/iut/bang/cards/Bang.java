package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Game;
import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Bang extends OrangeCard {

    /**
     * true quand un effet bang est en cour de jeu. Sert à autoriser les ratés, les planques et les pouvoirs
     */
    private static boolean bangEffetActive;

    public Bang(int value, CardSuit suit) {
        super("Bang!", value, suit);
        bangEffetActive = false;
    }

    public static boolean isBangEffetActive() {
        return bangEffetActive;
    }

    public static void bangEffect(Player target, Player attacker){

        bangEffetActive = true; //Permet de jouer des ratés

        //Si le joueur a une planque, elle s'active
        if(!target.hasBarel() || (target.hasBarel() && !Barrel.savePlayer(target.getGame()))) {
            boolean b = Barrel.savePlayer(target.getGame());
            //On enlève toutes les cartes qui ne sont pas des ratés de la main
            List<Card> missCards = new ArrayList<>(target.getHand());
            missCards.removeIf(c -> !c.getName().equals("Missed!"));

            //Le joueur choisi si il veut jouer un ratés
            Card missed = target.chooseCard("Jouez une carte Missed! ou passez",
                    missCards, false, true);
            if (missed == null) {
                target.decrementHealth(1, attacker);
            } else {
                target.playFromHand(missed);
            }
        }
        bangEffetActive = false;
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

        bangEffect(target, player);
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
