package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;
import java.util.List;

public class Panic extends OrangeCard {

    public Panic(int value, CardSuit suit) {
        super("Panic!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        super.playedBy(player);
        ArrayList<String> stringAutorized = othersCardsAndPlayers(player.getPlayersInRange(player.getBaseRange()));

        String choix = player.choose("Que voulez vous selectioner ?",
                new ArrayList<>(stringAutorized), true, false);


        player.addToHand(removeCardFromPlayer(player, choix));
        player.getGame().addToDiscard(this);
    }

    public ArrayList<String> othersCardsAndPlayers(List<Player> players){
        ArrayList<String> stringAutorized = new ArrayList<>();

        for(Player target : players){
            //Main du joueur
            if(target.getHand().size()>0)
                stringAutorized.add(target.getName());
            //Arme du joueur
            if(target.getWeapon()!=null)
                stringAutorized.add(target.getWeapon().getName());
            //Cartes bleus
            for(BlueCard bc : target.getInPlay())
                stringAutorized.add(bc.getName());

        }

        return stringAutorized;
    }


    public Card removeCardFromPlayer(Player player, String choix){
        Card chosenCard=null;
        Player target=null;

        for(Player localtarget : player.getOtherPlayers()){
            //Si le choix est un joueur
            if(choix.equals(localtarget.getName())){
                chosenCard=chosePlayerHand(choix, player).removeRandomCardFromHand();
                localtarget.removeFromHand(chosenCard);
                break;
            }

            //Si le choix est une carte exposée devant le joueur
            else if(choseCardInPlayByName(choix, localtarget)!=null){
                chosenCard=choseCardInPlayByName(choix, localtarget);
                localtarget.removeFromInPlay((BlueCard) chosenCard);
                break;
            }

            else if(localtarget.getWeapon()!=null && choix.equals(localtarget.getWeapon().getName())){
                chosenCard=localtarget.getWeapon();
                localtarget.setWeapon(null);
                break;
            }
        }
        return chosenCard;
    }
    /**
     * Retourne le joueur dont le nom correspond au choix, null si aucun n'existe
     * @param choix correspond au nom à essayer
     * @param player correspond au joueur que cherche à faire la comparaison
     * @return le player choisit, null si inexistant
     */
    public Player chosePlayerHand(String choix, Player player){
        for(Player p : player.getOtherPlayers()){
            if(p.getName().equals(choix))
                return p;
        }
        return null;
    }


    /**
     * Retourne le joueur dont le nom correspond au choix, null si aucun n'existe
     * @param name correspond au nom à essayer
     * @param player correspond au joueur que cherche à faire la comparaison
     * @return le player choisit, null si inexistant
     */
    public Card choseCardInPlayByName(String name, Player player){
        for(BlueCard bc : player.getInPlay()){
            if(bc.getName().equals(name))
                return bc;
        }
        return null;
    }
}
