package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Indians extends OrangeCard {

    public Indians(int value, CardSuit suit) {
        super("Indians!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        //fait un effet bang sur les autres joueurs
        for(Player p : player.getOtherPlayers()){
            //On enlève toutes les cartes qui ne sont pas des bang de la main
            List<Card> bangCards = new ArrayList<>(p.getHand());
            bangCards.removeIf(c -> (!c.getName().equals("Bang!") &&
                    !(p.getBangCharacter().getName().equals("Calamity Janet") && c.getName().equals("Missed!"))));

            //Le joueur choisit s'il veut jouer un Bang! ou non
            Card bang = p.chooseCard("Jouez une carte Bang! ou passez",
                    bangCards, false, true);
            if (bang == null) {
                p.decrementHealth(1, player);
            } else {
                p.discardFromHand(bang);
            }
        }
    }
}
