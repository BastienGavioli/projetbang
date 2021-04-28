package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Beer extends OrangeCard {

    private boolean healEffectActive;

    public Beer(int value, CardSuit suit) {
        super("Beer", value, suit);
        healEffectActive = false;
    }

    public void heal(Player player) {
        if(healEffectActive)
            player.incrementHealth(1);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        if(player.getGame().getPlayers().size()>2)
            healEffectActive = true;
        this.heal(player);
    }
}
