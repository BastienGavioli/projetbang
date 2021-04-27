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
        //Choix de la cible et vérification de la portée
        else{
            target = player.choosePlayer("À qui voulez-vous prendre une carte ? " +
                            "(portée non modifiée par les armes)",
                    player.getPlayersInRange(player.getBaseRange()), false);
        }

        String chosenPlayer = choseDiscardCard(player, target);

        Card chosenCard=null;
        if(chosenPlayer.equals(target.getName()))
            chosenCard=target.removeRandomCardFromHand();
        else{
            if(target.getWeapon()!=null && target.getWeapon().getName().equals(chosenPlayer))
                chosenCard=target.getWeapon();
            else
                for(Card c : target.getInPlay())
                   if(c.getName().equals(chosenPlayer))
                        chosenCard=c;
        }



        //La donner au player et la supprime du target
        //Si la carte est bleu on la joue

        if(chosenCard!=null && target.getHand().contains(chosenCard)) {
            target.removeFromHand(chosenCard);
            player.addToHand(chosenCard);

        }
        else{
            if (chosenCard.getCardColor().equals("Blue")) {
                player.addToHand(chosenCard);
                target.removeFromInPlay((BlueCard) chosenCard);
            } else {
                player.addToHand(chosenCard);
                target.setWeapon(null);
            }
        }

        player.getGame().addToDiscard(this);

    }

    public String choseDiscardCard(Player player, Player target){

        //Choix de la carte
        //Choix main ou exposé
        ArrayList<String> targetCardsInHand = new ArrayList<>();
        if(target.getHand().size()>0)
            targetCardsInHand.add(target.getName());

        //Choix des cartes devant le joueur
        ArrayList<Card> targetCardsInPlay = new ArrayList<>(target.getInPlay());
        if(target.getWeapon()!=null)
            targetCardsInPlay.add(target.getWeapon());

        for(Card c : targetCardsInPlay)
            targetCardsInHand.add(c.getName());

        return player.choose("Voulez-vous prendre dans la main de "+target.getName()+
                        " (donnez alors son nom) ou devant lui (Cliquez sur la carte) ?",
                new ArrayList<>(targetCardsInHand), true, false);

    }
}
