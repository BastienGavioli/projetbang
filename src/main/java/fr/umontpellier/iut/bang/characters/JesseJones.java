package fr.umontpellier.iut.bang.characters;

import fr.umontpellier.iut.bang.Player;
import fr.umontpellier.iut.bang.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class JesseJones extends BangCharacter {

    public JesseJones() {
        super("Jesse Jones", 4);
    }

    @Override
    public void onStartTurn(Player player) {
        Player target;

        List<Player> targetChoice = new ArrayList<>(player.getOtherPlayers());
        targetChoice.removeIf(p -> p.getHand().size() ==0);

        target = player.choosePlayer("Dans quelle main souhaitez vous piocher votre première carte ?"+
                "Si vous passez une carte sera automatiquement piochée dans la pioche", targetChoice, true);

        if(target!=null)
            player.addToHand(target.removeRandomCardFromHand());
        else
            player.drawToHand();
        player.drawToHand();
    }
}
