package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class CatBalou extends OrangeCard {

    public CatBalou(int value, CardSuit suit) {
        super("Cat Balou", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        Panic panic = new Panic(getValue(), getSuit());

        ArrayList<String> stringAutorized = panic.othersCardsAndPlayers(player.getOtherPlayers());

        String choix = player.choose("Que voulez vous supprimer ? (Nom du joueur = carte random dans la main)",
                new ArrayList<>(stringAutorized), true, false);

        Card choisie = null;
        for(Card c : panic.cardsOwnedByPlayers(player.getPlayersInRange(player.getBaseRange()))) {
            if(choix.equals("" + c.getName() + c.getCardColor())){
                choisie = c;
                break;
            }
        }
        if(choisie==null)
            player.getGame().addToDiscard(panic.removeCardFromPlayer(player, choix));
        else{
            player.getGame().addToDiscard(choisie);
        }
    }

}
