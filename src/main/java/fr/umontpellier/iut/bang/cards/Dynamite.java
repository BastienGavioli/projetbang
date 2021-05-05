package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Dynamite extends BlueCard {

    public Dynamite(int value, CardSuit suit) {
        super("Dynamite", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        if(!player.getInPlay().contains(this))
            player.addToInPlay(this);
        else
            tryToExplodeOn(player);
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return true;
    }

    public void tryToExplodeOn(Player player){
        Card pioche = player.randomDraw();
        if(pioche.getValue() >= 2 && pioche.getValue() <=9 && pioche.getSuit().toJSON().equals("S")){
            player.discardFromInPlay(player.getCardInPlay("Dynamite"));
            player.decrementHealth(3, null);
            }
        else{
            //On passe la dynamite au suivant
            int index = player.getGame().getPlayers().indexOf(player);
            index += 1;
            index %= player.getGame().getPlayers().size();
            Player target = player.getGame().getPlayers().get(index);

            player.removeFromInPlay(this);
            target.addToInPlay(this);
        }
    }

}
