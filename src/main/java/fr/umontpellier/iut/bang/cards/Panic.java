package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class Panic extends OrangeCard {

    public Panic(int value, CardSuit suit) {
        super("Panic!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        //Choisir une cible
        Player target;

        if(player.getPlayersInRange(player.getBaseRange()).size()==1){
            target = player.getPlayersInRange(player.getBaseRange()).get(0);
        }
        //Choix de la cible et verification de la porté
        else{
            target = player.choosePlayer("A qui voulez vous prendre une carte ? " +
                            "(portée non modifié par les armes)",
                    player.getPlayersInRange(player.getBaseRange()), false);
        }

        //Choix de la carte
        ArrayList<Card> cartesTarget = new ArrayList<>(target.getHand());
        cartesTarget.addAll(target.getInPlay());
        cartesTarget.add(target.getWeapon());
        Card choisie = player.chooseCard("Choisissez une carte de " + target.getName(), cartesTarget,
                false, false);


        //La donner au player et la supprime du target
        player.addToHand(choisie);
        //Si la carte est bleu on la joue
        if(choisie.getCardColor().equals("Blue")){
            choisie.playedBy(player);
            target.removeFromInPlay((BlueCard) choisie);
        }
        else if(choisie.getCardColor().equals("Weapon")){
            choisie.playedBy(player);
            target.setWeapon(null);
        }
        else{
            target.removeFromHand(choisie);
        }


        //Supprimer la carte de la cible

    }
}
