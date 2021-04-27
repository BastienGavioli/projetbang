package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class Panic extends OrangeCard {

    public Panic(int value, CardSuit suit) {
        super("Panic!", value, suit);
    }

    @Override
    public void playedBy(Player player) {
        //Choisir une cible


        //Je garde la variable target pour le cas d'une cible unique
        /*
        //Choix de la cible et vérification de la portée
        else{
            target = player.choosePlayer("À qui voulez-vous prendre une carte ? " +
                            "(portée non modifiée par les armes)",
                    player.getPlayersInRange(player.getBaseRange()), false);
        }*/


        //String choix = choseDiscardCard(player, target);
        ArrayList<String> stringAutorized = othersCardsAndPlayers(player);

        String choix = player.choose("Que voulez vous selectioner ?",
                new ArrayList<>(stringAutorized), true, false);

        Card chosenCard=null;
        /*
        if(choix.equals(target.getName()))
            chosenCard=target.removeRandomCardFromHand();
        */
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
        player.addToHand(chosenCard);





    }

    public ArrayList<String> othersCardsAndPlayers(Player player){
        ArrayList<String> stringAutorized = new ArrayList<>();

        for(Player target : player.getOtherPlayers()){
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

    public String choseDiscardCard(Player player, Player target){

        //Choix de la carte
        //Choix main ou exposé
        ArrayList<String> targetCardsInHand = new ArrayList<>();
        if(target.getHand().size()>0)
            targetCardsInHand.add(target.getName());

        //Choix des cartes devant le joueur
        ArrayList<Card> targetCardsInPlay = new ArrayList<>(target.getInPlay());
        if(target.getWeapon()!=null)
            targetCardsInPlay.add(target.getWeapon());

        //choix des joueurs
        for(Player p : player.getOtherPlayers()){
            targetCardsInHand.add(p.getName());
        }

        for(Card c : targetCardsInPlay)
            targetCardsInHand.add(c.getName());


        return player.choose("Voulez-vous prendre dans la main de "+target.getName()+
                        " (donnez alors son nom) ou devant lui (Cliquez sur la carte) ?",
                new ArrayList<>(targetCardsInHand), true, false);

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
