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
            ArrayList<String> stringAuthorized = othersCardsAndPlayers(player.getPlayersInRange(1));

            String choix = player.choose("Que voulez vous sélectionner ? (Cliquer sur le joueur pour prendre dans la main)",
                    new ArrayList<>(stringAuthorized), true, false);

            Card choisie=null;
            //Si la carte viens de la main, on connaitra son nom, sinon on connaitra son nom et sa valeur poker

            for(Card c : cardsOwnedByPlayers(player.getPlayersInRange(1))) {
                if(choix.equals("" + c.getName() + c.getCardColor())){
                    choisie = c;
                    break;
                }
            }
            //Si ça n'est aucune carte, c'est un joueur
            if(choisie==null)
                player.addToHand(removeCardFromPlayer(player, choix));
            else
                player.addToHand(choisie);
    }

    public ArrayList<String> othersCardsAndPlayers(List<Player> players){
        ArrayList<String> stringAuthorized = new ArrayList<>();

        for(Player target : players){
            //Main du joueur
            if(target.getHand().size()>0)
                stringAuthorized.add(target.getName());
            //Arme du joueur
            if(target.getWeapon()!=null)
                stringAuthorized.add(""+target.getWeapon().getName()+target.getWeapon().getPokerString());
            //Cartes bleus
            for(BlueCard bc : target.getInPlay())
                stringAuthorized.add(""+bc.getName()+bc.getPokerString());

        }

        return stringAuthorized;
    }


    public List<Card> cardsOwnedByPlayers(List<Player> players){
        ArrayList<Card> cardAuthorized = new ArrayList<>();

        for(Player target : players){
            //Main du joueur
            cardAuthorized.addAll(target.getHand());
            //Arme du joueur
            if(target.getWeapon()!=null)
                cardAuthorized.add(target.getWeapon());
            //Cartes bleus
            for(BlueCard bc : target.getInPlay())
                cardAuthorized.addAll(target.getInPlay());
        }

        return cardAuthorized;
    }

    public Card removeCardFromPlayer(Player player, String choice){
        Card chosenCard=null;
        Player target=null;

        for(Player localTarget : player.getOtherPlayers()){
            //Si le choix est un joueur
            if(choice.equals(localTarget.getName())){
                chosenCard=chosePlayerHand(choice, player).removeRandomCardFromHand();
                localTarget.removeFromHand(chosenCard);
                break;
            }

            //Si le choix est une carte exposée devant le joueur
            else if(choseCardInPlayByName(choice, localTarget)!=null){
                chosenCard=choseCardInPlayByName(choice, localTarget);
                localTarget.removeFromInPlay((BlueCard) chosenCard);
                break;
            }

            else if(localTarget.getWeapon()!=null && choice.equals(""+localTarget.getWeapon().getName()+
                    localTarget.getWeapon().getPokerString())){
                chosenCard=localTarget.getWeapon();
                localTarget.setWeapon(null);
                break;
            }
        }
        return chosenCard;
    }
    /**
     * Retourne le joueur dont le nom correspond au choix, null si aucun n'existe
     * @param choice correspond au nom à essayer
     * @param player correspond au joueur que cherche à faire la comparaison
     * @return le player choisit, null si inexistant
     */
    public Player chosePlayerHand(String choice, Player player){
        for(Player p : player.getOtherPlayers()){
            if(p.getName().equals(choice))
                return p;
        }
        return null;
    }


    /**
     * Retourne le joueur dont le nom correspond au choix, null si aucun n'existe
     * @param choice correspond au nom à essayer avec son pokerString
     * @param player correspond au joueur que cherche à faire la comparaison
     * @return le player choisit, null si inexistant
     */
    public Card choseCardInPlayByName(String choice, Player player){
        for(BlueCard bc : player.getInPlay()){
            if(choice.equals(""+bc.getName()+bc.getPokerString()))
                return bc;
        }
        return null;
    }

    @Override
    public boolean canPlayFromHand(Player player) {
        return super.canPlayFromHand(player) && !player.getPlayersInRange(1).isEmpty();
    }
}
