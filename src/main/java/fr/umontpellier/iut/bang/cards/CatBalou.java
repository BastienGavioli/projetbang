package fr.umontpellier.iut.bang.cards;

import fr.umontpellier.iut.bang.Player;

import java.util.ArrayList;

public class CatBalou extends OrangeCard {

    public CatBalou(int value, CardSuit suit) {
        super("Cat Balou", value, suit);
    }

    @Override
    public void playedBy(Player player) {

        //Choisir une cible
        Player target;

        if(player.getOtherPlayers().size()==1){
            target = player.getOtherPlayers().get(0);
        }
        else{
            target = player.choosePlayer("A qui voulez vous supprimer une carte ? " +
                            "(portée illimitée)",
                    player.getOtherPlayers(), false);
        }

        //Choix de la carte
        //Choix main ou exposé
        ArrayList<String> possibilites = new ArrayList<>();
        if(target.getHand().size()>0)
            possibilites.add(target.getName());

        //Choix des cartes devant le joueur
        ArrayList<Card> cartesTarget = new ArrayList<>(target.getInPlay());
        if(target.getWeapon()!=null)
            cartesTarget.add(target.getWeapon());

        for(Card c : cartesTarget)
            possibilites.add(c.getName());

        String choix = player.choose("Voulez vous prendre dans la main de "+target.getName()+
                        " (donnez alors son nom) ou devant lui (CLiquez sur la carte) ?",
                new ArrayList<>(possibilites), true, false);

        Card choisie=null;
        if(choix.equals(target.getName()))
            choisie=target.removeRandomCardFromHand();
        else {
            if (target.getWeapon() != null && target.getWeapon().getName().equals(choix)){
                choisie = target.getWeapon();
                target.setWeapon(null);
            }
            else
                for(BlueCard c : target.getInPlay())
                    if(c.getName().equals(choix)) {
                        choisie = c;
                        target.removeFromInPlay(c);
                        break;
                    }
        }
        player.getGame().addToDiscard(choisie);
        player.getGame().addToDiscard(this);
    }

    /**
     * Cette fonction est juste un test pour refactoriser la fonction playedBy,
     * si ca ne marche pas je la supprimerais (Gatien)
     */
    public void testFOnctionPlusOptiPlayedBy(Player player){
        //Toutes les cartes exposées
        ArrayList<String> possibilites = new ArrayList<>();

        //Pour chaque autre joueur, on rajoute son inPlay, son arme et son nom
        for(Player p : player.getOtherPlayers()){
            //inPlay
            for (BlueCard bc : p.getInPlay())
                possibilites.add(bc.getName());
            //Weapon
            if(p.getWeapon()!=null)
                possibilites.add(p.getWeapon().getName());
            //name
            possibilites.add(p.getName());
        }

        String choix = player.choose("Cliquez sur la carte à prendre ou sur le joueur pour prendre dans la main",
                new ArrayList<>(possibilites), true, false);



    }
}
