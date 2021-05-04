package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class Jail extends BlueCard {
    public Jail(int value, CardSuit suit) {
        super("Jail", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        if(!player.getInPlay().contains(this)){
            Player target;
            ArrayList<Player> targets = new ArrayList<>();
            //retire le shériff des possibilités
            for(Player p : player.getOtherPlayers()){
                if(!p.getRole().toString().equals("SHERIFF")){
                    targets.add(p);
                }
            }
            //Choisir une cible parmi les joueurs non-shériff
            target = player.choosePlayer("Qui voulez-vous mettre en prison ? ",
                    targets, false);
            //Ajout de la carte au joueur choisi
            target.addToInPlay(this);
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
