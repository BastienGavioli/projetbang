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

        //tant que les 2 peuvent/veulent jouer des bang
        int compteurTest = 0;
        while (true) {
            compteurTest++;
            if(compteurTest==10){
                System.out.println(compteurTest);
            }
            //La cible choisit si elle veut jouer un Bang! ou non
            if(!discardBang(target)) {
                target.decrementHealth(1, player);
                break;
            }
            if(!discardBang(player)) {
                player.decrementHealth(1, target);
                break;
            }
        }
    }

    public boolean discardBang(Player target){
        Card bang = null;
        List<Card> bangCards = new ArrayList<>(target.getHand());
        bangCards.removeIf(c -> !c.getName().equals("Bang!"));
        if(bangCards.size()!=0){
            bang = target.chooseCard("Jouez une carte Bang! ou passez",
                    bangCards, false, true);
            if(bang != null) {
                target.discardFromHand(bang);
                return true;
            }
        }
        return false;
    }

}
