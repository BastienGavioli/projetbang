package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Bang extends OrangeCard {

    /**
     * true quand un effet bang est en cours de jeu. Sert à autoriser les ratés, les planques et les pouvoirs
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
        if((!target.hasBlueCardName("Barrel") || (target.hasBlueCardName("Barrel")
                && !Barrel.savePlayer(target))) && !(target.getBangCharacter().getName().equals("Jourdonnais") && Barrel.savePlayer(target))) {
            //On enlève toutes les cartes qui ne sont pas des ratés de la main
            List<Card> missCards = new ArrayList<>(target.getHand());
            missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                    !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));

            //Le joueur choisit s'il veut jouer un Missed!
            Card missed = target.chooseCard("Jouez une carte Missed! ou passez",
                    missCards, false, true);
            if (missed == null) {
                target.decrementHealth(1, attacker);
            } else {
                target.discardFromHand(missed);
                target.getGame().addToDiscard(missed);
            }
        }
        bangEffetActive = false;
    }


    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        //On sait qu'il y a au moins 1 joueur à portée
        Player target;
        int range = player.getRangeMax();
        //Sélection automatique de la cible si elle est unique
        if(player.getPlayersInRange(range).size()==1){
            target = player.getPlayersInRange(range).get(0);
        }
        //Choix de la cible et vérification de la portée
        else{
            target = player.choosePlayer("Sur qui voulez-vous tirer ?",
                    player.getPlayersInRange(range), false);
        }

        bangEffect(target, player);
        player.setBangPlayed(true);

    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return ((player.getWeapon()!=null &&
                player.getWeapon().getName().equals("Volcanic"))
                ||!player.isBangPlayed() || player.getBangCharacter().getName().equals("Willy the Kid"))
                && player.getPlayersInRange(player.getRangeMax()).size()>0;
    }


}
