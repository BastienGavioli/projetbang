package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class PedroRamirez extends BangCharacter {

    public PedroRamirez() {
        super("Pedro Ramirez", 4);
    }

    /*Durant la phase 1 de son tour, il peut choisir de piocher la première carte de la défausse
    au lieu de la prendre dans la pioche. Il pioche sa seconde carte normalement, dans la pioche.
    Détails : Au début de son tour et si la défausse n'est pas vide le joueur peut entrer le nom
    de la carte qui se trouve sur le dessus de la défausse pour la piocher en main, ou passer pour
    prendre sa première carte dans la pioche. La seconde carte est automatiquement prise dans la pioche.
    Remarques :
    Si la défausse est vide, le joueur pioche automatiquement ses deux cartes dans la pioche*/

    @Override
    public void onStartTurn(Player player) {
        List<Card> possibilite = new ArrayList<>();
        possibilite.add(player.getGame().getTopOfDiscardPile());
        Card choix = player.chooseCard("Voulez vous prendre la carte de la défausse ?", possibilite, true, true);
        if (choix != null)
            player.addToHand(possibilite.get(0));
        else
            player.drawToHand();
        player.drawToHand();
    }
}
