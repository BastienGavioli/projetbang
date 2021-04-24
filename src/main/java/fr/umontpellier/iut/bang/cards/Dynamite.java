package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

public class Dynamite extends BlueCard {
    private boolean active;


    public Dynamite(int value, CardSuit suit) {
        super("Dynamite", value, suit);
        active = false;
    }

    @Override
    public void playedBy(Player player) {
        if(!active) {
            player.addToInPlay(this);
            active=true;
        }
        else{
            tryToExplodeOn(player);
        }
    }

    public void tryToExplodeOn(Player player){
        Card pioche = player.randomDraw();
        if(pioche.getValue() >= 2 && pioche.getValue() <=9 && pioche.getSuit().toJSON().equals("S")){
            player.decrementHealth(3, null);
            player.discardFromInPlay(player.getCardInPlay("Dynamite"));
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
