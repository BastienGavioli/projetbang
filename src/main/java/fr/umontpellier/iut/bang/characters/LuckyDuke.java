package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class LuckyDuke extends BangCharacter {

    public LuckyDuke() {
        super("Lucky Duke", 4);
    }

    /**
     * Cette méthode est exécutée lorsque Lucky Duke "dégaine" (il retourne deux cartes du dessus de la pioche de façon
     * aléatoire (Jail, Barrel, etc.)).
     * Le joueur choisit parmi les deux premières cartes tirées le résultat qu'il préfère. Il défausse ensuite les
     * deux cartes.
     * @param player le joueur associé au personnage
     * @return la carte qui a été tirée
     */

    @Override
    public Card randomDraw(Player player) {
        List<String> valueBothCards = new ArrayList<>();
        Card firstCard = player.drawCard();
        Card secondCard = player.drawCard();
        valueBothCards.add(firstCard.getPokerString());
        valueBothCards.add(secondCard.getPokerString());
        String chosenValue = player.choose("Quelle valeur Poker préférez-vous ?", valueBothCards, true, false);
        player.discard(firstCard);
        player.discard(secondCard);
        if(firstCard.getPokerString().equals(chosenValue))
            return firstCard;
        return secondCard;
    }
}
