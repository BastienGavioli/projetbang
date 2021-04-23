package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class Jail extends BlueCard {
    private boolean active;
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
        active = false;
    }

    @Override
    public void playedBy(Player player) {
        if(!active){
            Player target;
            ArrayList<Player> targets = new ArrayList<>();
            //retire le sheriff des possibilites
            for(Player p : player.getOtherPlayers()){
                if(!p.getRole().toString().equals("SHERIFF")){
                    targets.add(p);
                }
            }
            //Choisir une cible parmi les joueurs non sheriff
            target = player.choosePlayer("Qui voulez vous mettre en prison ? ",
                    targets, false);
            //ajout de la carte au joueur choisi
            target.addToInPlay(this);
            active = true;
        }
        else
            tryExitJail(player);
    }

    public void tryExitJail(Player player){
        player.discardFromInPlay(this);
        Card pioche = player.randomDraw();
        if(pioche.getSuit().toJSON().equals("H")){
            player.playTurn();
        }
    }
}
