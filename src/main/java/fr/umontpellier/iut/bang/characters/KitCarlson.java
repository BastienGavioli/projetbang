package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class KitCarlson extends BangCharacter {

    public KitCarlson() {
        super("Kit Carlson", 4);
    }

    /*Durant la phase 1 de son tour, il regarde les trois premières cartes de la pioche,
    en choisit 2 qu’il garde et repose la troisième sur la pioche, face cachée.
    Détails : Au début du tour, le joueur doit entrer le nom de la carte qu'il veut replacer sur la pioche
    (c'est plus simple que de lui faire choisir deux fois les cartes qu'il veut garder).
    La carte est replacée sur le dessus de la pioche et les deux autres cartes tirées sont placées dans sa main.
    Remarques :
    Vous pouvez passer true comme valeur à l'argument showButtons de la méthode Player.chooseCard()
    pour afficher à l'écran les trois cartes qui ont été piochées (dans l'interface graphique et en mode console)*/

    @Override
    public void onStartTurn(Player player) {
        List<Card> possibilites = new ArrayList<>();
        possibilites.add(player.drawCard());
        possibilites.add(player.drawCard());
        possibilites.add(player.drawCard());
        Card reposer = player.chooseCard("Quelle carte voulez vous reposer ?", possibilites, true, false);

        player.getGame().getDrawPile().push(reposer);
        possibilites.remove(reposer);

        for(int i = 0; i<=1; i++)
            player.addToHand(possibilites.get(i));


    }
}
