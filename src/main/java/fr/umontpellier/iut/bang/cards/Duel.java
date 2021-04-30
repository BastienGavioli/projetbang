package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Duel extends OrangeCard {

    public Duel(int value, CardSuit suit) {
        super("Duel", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Player target;
        target = player.choosePlayer("Qui voulez vous défier ?", player.getOtherPlayers(), false);

        //On enlève toutes les cartes qui ne sont pas des bang de la main de la cible
        List<Card> bangCards = new ArrayList<>(target.getHand());
        bangCards.removeIf(c -> !c.getName().equals("Bang!"));

        //On fait pareil pour le joueur
        List<Card> bangCardsP = new ArrayList<>(player.getHand());
        bangCardsP.removeIf(c -> !c.getName().equals("Bang!"));

        //La cible choisit si elle veut jouer le 1er Bang! ou non
        Card bang;
                bang = target.chooseCard("Jouez une carte Bang! ou passez",
                bangCards, false, true);

        if (bang == null) { //si elle choisit de ne pas lancer le duel elle perd un pts de vie
            target.decrementHealth(1, player);
        }else { //sinon le duel est lance
            target.discardFromHand(bang);
                bangCards = new ArrayList<>(target.getHand());
                bangCards.removeIf(c -> !c.getName().equals("Bang!"));
            //Le joueur choisit s'il veut jouer un 1er Bang! ou non
            Card bangP;
                    bangP = player.chooseCard("Jouez une carte Bang! ou passez",
                    bangCardsP, false, true);
            if(bangP == null)
                player.decrementHealth(1, target);
            else {
                player.discardFromHand(bangP);
                    bangCardsP = new ArrayList<>(player.getHand());
                    bangCardsP.removeIf(c -> !c.getName().equals("Bang!"));

            //tant que les 2 peuvent/veulent jouer des bang
            while (true) {
                //La cible choisit si elle veut jouer un Bang! ou non
                bangCards.removeIf(c -> !c.getName().equals("Bang!"));
                if(bangCards.size()!=0){
                    bang = target.chooseCard("Jouez une carte Bang! ou passez",
                            bangCards, false, true);
                    if(bang != null)
                        target.discardFromHand(bang);
                }
                else
                    break;

                bangCardsP.removeIf(c -> !c.getName().equals("Bang!"));
                if(bangCardsP.size() != 0){
                    //Le joueur choisit s'il veut jouer un Bang! ou non
                    bangP = player.chooseCard("Jouez une carte Bang! ou passez",
                            bangCardsP, false, true);
                    if(bangP != null)
                        player.discardFromHand(bangP);
                }
                else
                    break;
            }

            //a la sortie de la boucle on verifie qui n'a plus de bang / qui a refuser de jouer un bang
            if (bang == null) {
                target.decrementHealth(1, player);
            } else {
                player.decrementHealth(1, target);
            }
        }
        }
    }
}
