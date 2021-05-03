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
                && !Barrel.savePlayer(target))) && !(target.getBangCharacter().getName().equals("Jourdonnais") && Barrel.savePlayer(target)) && !attacker.getBangCharacter().getName().equals("Slab the Killer")) {
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
        else if(attacker.getBangCharacter().getName().equals("Slab the Killer")){


            if(target.getBangCharacter().getName().equals("Jourdonnais")){

                //cas ou la cible est Jourdonnais et son activation de planque marche
                if(Barrel.savePlayer(target)){

                    //verification qu'il n'a pas une planque supplementaire lui permettant d'atteindre les Missed! requis
                    if(!target.hasBlueCardName("Barrel") || (target.hasBlueCardName("Barrel") && !Barrel.savePlayer(target))){
                        List<Card> missCards = new ArrayList<>(target.getHand());
                        missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                                !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));
                        Card missed = target.chooseCard("Jouez une carte Missed! ou passez",
                                missCards, false, true);
                        if (missed == null) {
                            target.decrementHealth(1, attacker);
                        } else {
                            target.discardFromHand(missed);
                            target.getGame().addToDiscard(missed);
                        }
                    }
                }

                //cas ou la cible est Jourdonnais mais que son activation de planque ne marche pas
                else{

                    //cas ou il a une planque et que celle-ci marche
                    if(target.hasBlueCardName("Barrel") && Barrel.savePlayer(target)){
                        List<Card> missCards = new ArrayList<>(target.getHand());
                        missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                                !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));
                        Card missed = target.chooseCard("Jouez une carte Missed! ou passez",
                                missCards, false, true);
                        if (missed == null) {
                            target.decrementHealth(1, attacker);
                        } else {
                            target.discardFromHand(missed);
                            target.getGame().addToDiscard(missed);
                        }
                    }

                    //cas ou il faut jouer les 2 Missed!
                    else{
                        List<Card> missCards = new ArrayList<>(target.getHand());
                        missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                                !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));
                        if(missCards.size() < 2){
                            target.decrementHealth(1,attacker);
                        }
                        else{
                            Card missed1 = target.chooseCard("Jouez deux carte Missed! ou passez",
                                    missCards, false, true);
                            if (missed1 == null) {
                                target.decrementHealth(1, attacker);
                            } else {
                                target.discardFromHand(missed1);
                                target.getGame().addToDiscard(missed1);
                                missCards.remove(missed1);
                                Card missed2 = target.chooseCard("Jouez la deuxieme carte Missed!",
                                        missCards,false,false);
                                target.discardFromHand(missed2);
                                target.getGame().addToDiscard(missed2);
                            }
                        }
                    }
                }
            }

            //cas ou la cible n'est pas Jourdonnais mais dispose d'une planque et que celle-ci marche
            else if(target.hasBlueCardName("Barrel") && Barrel.savePlayer(target)){
                List<Card> missCards = new ArrayList<>(target.getHand());
                missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                        !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));
                Card missed = target.chooseCard("Jouez une carte Missed! ou passez",
                        missCards, false, true);
                if (missed == null) {
                    target.decrementHealth(1, attacker);
                } else {
                    target.discardFromHand(missed);
                    target.getGame().addToDiscard(missed);
                }
            }

            //cas ou aucune planque ne marche et il faut jouer 2 Missed!
            else{
                List<Card> missCards = new ArrayList<>(target.getHand());
                missCards.removeIf(c -> (!c.getName().equals("Missed!") &&
                        !(target.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Bang!"))));
                if(missCards.size() < 2){
                    target.decrementHealth(1,attacker);
                }
                else{
                    Card missed1 = target.chooseCard("Jouez deux carte Missed! ou passez",
                            missCards, false, true);
                    if (missed1 == null) {
                        target.decrementHealth(1, attacker);
                    } else {
                        target.discardFromHand(missed1);
                        target.getGame().addToDiscard(missed1);
                        missCards.remove(missed1);
                        Card missed2 = target.chooseCard("Jouez la deuxieme carte Missed!",
                                missCards,false,false);
                        target.discardFromHand(missed2);
                        target.getGame().addToDiscard(missed2);

                    }
                }
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
