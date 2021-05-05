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
        ArrayList<String> stringAutorized = othersCardsAndPlayers(player.getPlayersInRange(1));

        String choix = player.choose("Que voulez vous selectioner ? (Cliquer sur le joueur pour prendre dans la main)",
                new ArrayList<>(stringAutorized), true, false);

        Card choisie=null;
        //Si la carte viens de la main, on connaitra son nom, sinon on connaitra son nom et sa valeur pocker

        for(Card c : cardsOwnedByPlayers(player.getPlayersInRange(1))) {
            if(choix.equals("" + c.getName() + c.getCardColor())){
                choisie = c;
                break;
            }
        }
        //Si ça n'est aucune carte, c'est un joueur
        if(choisie==null)
            player.addToHand(removeCardFromPlayer(player, choix));
        else{
            player.addToHand(choisie);

        }


    }

    public ArrayList<String> othersCardsAndPlayers(List<Player> players){
        ArrayList<String> stringAutorized = new ArrayList<>();

        for(Player target : players){
            //Main du joueur
            if(target.getHand().size()>0)
                stringAutorized.add(target.getName());
            //Arme du joueur
            if(target.getWeapon()!=null)
                stringAutorized.add(""+target.getWeapon().getName()+target.getWeapon().getPokerString());
            //Cartes bleus
            for(BlueCard bc : target.getInPlay())
                stringAutorized.add(""+bc.getName()+bc.getPokerString());

        }

        return stringAutorized;
    }


    public List<Card> cardsOwnedByPlayers(List<Player> players){
        ArrayList<Card> cardAutorized = new ArrayList<>();

        for(Player target : players){
            //Main du joueur
            cardAutorized.addAll(target.getHand());
            //Arme du joueur
            if(target.getWeapon()!=null)
                cardAutorized.add(target.getWeapon());
            //Cartes bleus
            for(BlueCard bc : target.getInPlay())
                cardAutorized.addAll(target.getInPlay());
        }

        return cardAutorized;
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

            else if(localtarget.getWeapon()!=null && choix.equals(""+localtarget.getWeapon().getName()+
                    localtarget.getWeapon().getPokerString())){
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
     * @param choix correspond au nom à essayer avec son pokerString
     * @param player correspond au joueur que cherche à faire la comparaison
     * @return le player choisit, null si inexistant
     */
    public Card choseCardInPlayByName(String choix, Player player){
        for(BlueCard bc : player.getInPlay()){
            if(choix.equals(""+bc.getName()+bc.getPokerString()))
                return bc;
        }
        return null;
    }
}
