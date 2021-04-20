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
        //Choix main ou exposé
        ArrayList<String> possibilites = new ArrayList<>();
        if(target.getHand().size()>0)
            possibilites.add("Hand");

        //Choix des cartes devant le joueur
        ArrayList<Card> cartesTarget = new ArrayList<>(target.getInPlay());
        if(target.getWeapon()!=null)
            cartesTarget.add(target.getWeapon());

        for(Card c : cartesTarget)
            possibilites.add(c.getName());

        String choix = player.choose("Voulez vous prendre dans la main de "+target.getName()+" ou devant lui",
                new ArrayList<>(possibilites), true, false);

        Card choisie=null;
        if(choix.equals("Hand"))
            choisie=target.removeRandomCardFromHand();
        else{
            if(target.getWeapon().getName().equals(choix))
                choisie=target.getWeapon();
            else
                for(Card c : target.getInPlay())
                   if(c.getName().equals(choix))
                        choisie=c;
        }



        //La donner au player et la supprime du target
        //Si la carte est bleu on la joue

        if(target.getHand().contains(choisie)) {
            target.removeFromHand(choisie);

            if(choisie.getCardColor().equals("Blue"))
                player.addToInPlay((BlueCard) choisie);
            else
                player.addToHand(choisie);

        }
        else{
            if (choisie.getCardColor().equals("Blue")) {
                choisie.playedBy(player);
                target.removeFromInPlay((BlueCard) choisie);
            } else {
                player.addToHand(choisie);
                target.setWeapon(null);
            }
        }

        target.removeFromHand(choisie);


    }
}
