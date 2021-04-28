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

        String choix = player.choose("Que voulez vous supprimer ?",
                new ArrayList<>(stringAutorized), true, false);

        player.getGame().addToDiscard(panic.removeCardFromPlayer(player, choix));
    }

}
