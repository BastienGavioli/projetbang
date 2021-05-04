package fr.umontpellier.iut.bang;

import fr.umontpellier.iut.bang.cards.*;
import fr.umontpellier.iut.bang.characters.BangCharacter;

import java.util.*;

public class Player {
    /**
     * Nom du joueur
     */
    private String name;
    /**
     * Points de vie courants
     */
    private int healthPoints;
    /**
     * Rôle dans la partie (Shériff, adjoint, hors-la-loi ou renégat)
     */
    private Role role;

    /**
     * Personnage
     */
    private BangCharacter bangCharacter;
    /**
     * Partie à laquelle le joueur appartient
     */
    private Game game;
    /**
     * Cartes en main
     */
    private List<Card> hand;
    /**
     * Cartes bleues actuellement posées devant le joueur (hors arme)
     */
    private List<BlueCard> inPlay;
    /**
     * Arme posée devant le joueur
     */
    private WeaponCard weapon;

    /**
     * true si le joueur a déjà joue un bang
     */
    private boolean bangPlayed;

    //true si le joueur a une carte Mustang en jeu
    //On peut le simplifier en vérifiant si le joueur a une carte Mustang exposée devant lui (voir hasBarrel)
    private boolean hasMustang;


    public Player(String name, BangCharacter bangCharacter, Role role) {
        this.name = name;
        this.role = role;
        this.bangCharacter = bangCharacter;
        healthPoints = getHealthPointsMax();
        inPlay = new ArrayList<>();
        hand = new ArrayList<>();
        bangPlayed = false;
        hasMustang = false;
    }


    public boolean hasBlueCardName(String name){
        for(Card c : inPlay){
            if(c.getName().equals(name))
                return true;
        }
        return false;
    }

    public BangCharacter getBangCharacter() {
        return bangCharacter;
    }

    public String getName() {
        return name;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public Role getRole() {
        return role;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getHand() {
        return hand;
    }

    public List<BlueCard> getInPlay() {
        return inPlay;
    }

    public WeaponCard getWeapon() {
        return weapon;
    }

    public void setBangPlayed(boolean bangPlayed) {
        this.bangPlayed = bangPlayed;
    }

    public boolean isBangPlayed() {
        return bangPlayed;
    }

    /**
     * @return la portée de l'arme équipée (1 si aucune arme équipée)
     */
    public int getWeaponRange() {
        if (weapon == null) {
            return 1;
        }
        return weapon.getRange();
    }

    /**
     * Retourne la portée des joueurs pour les effets ne prenant pas en compte l'arme, modifiée par le viseur
     */
    public int getBaseRange(){
        int range = 1;
        if(hasBlueCardName("Scope")){
            range++;
        }
        if(bangCharacter.getName().equals("Rose Doolan")){
            range++;
        }
        return range;
    }

    /**
     * @return la range d'un personnage avec son arme plus ses modificateurs
     */
    public int getRangeMax(){
        return getWeaponRange()+getBaseRange()-1;
    }
    /**
     * @return le nombre maximum de points de vie que le joueur peut avoir. Dépend des points de vie de son personnage
     * et de son rôle (le Shériff a un point de vie max de plus que les autres)
     */
    public int getHealthPointsMax() {
        int hp = bangCharacter.getHealthPoints();
        if (role == Role.SHERIFF)
            return hp + 1;
        return hp;
    }

    /**
     * @return la liste des autres joueurs encore en jeu, dans l'ordre de jeu (le premier est le joueur
     * immédiatement après le joueur courant)
     */
    public List<Player> getOtherPlayers() {
        ArrayList<Player> playerList = new ArrayList<>();

        int currentPlayerIndex = game.getPlayers().indexOf(this) + 1;
        if(currentPlayerIndex == game.getPlayers().size()){
            currentPlayerIndex = 0;
        }
        while(playerList.size() != game.getPlayers().size()){
            playerList.add(game.getPlayers().get(currentPlayerIndex));
            currentPlayerIndex ++;
            if(currentPlayerIndex == game.getPlayers().size()){
                currentPlayerIndex = 0;
            }
        }
        playerList.remove(this);
        return playerList;
    }

    

    /**
     * @param range portée considérée
     * @return la liste des autres joueurs encore en jeu que le joueur courant voit à une distance inférieure ou égale
     * à {@code range}.
     */
    public List<Player> getPlayersInRange(int range) {
        List<Player> playersInRange = new ArrayList<>();

        for(Player p : getOtherPlayers()){
            if(this.distanceTo(p)<=range){
                playersInRange.add(p);
            }
        }

        return playersInRange;
    }

    /**
     * @return true si le joueur est mort
     */
    public boolean isDead() {
        return healthPoints <= 0;
    }

    /**
     * Change l'arme du joueur
     * Si le joueur a déjà une arme, celle-ci est défaussée
     * <p>
     * Remarque : pour retirer l'arme du joueur, il faut appeler {@code p.setWeapon(null);}
     *
     * @param weapon nouvelle arme à équiper
     */
    public void setWeapon(WeaponCard weapon) {
        if(this.getWeapon() != null){
            this.getGame().addToDiscard(this.weapon);
        }
        this.weapon = weapon;
    }

    /**
     * @param cardName nom de la carte à renvoyer
     * @return la première carte parmi les cartes en jeu du joueur ayant le nom passé en argument, ou {@code null}
     * si aucune carte ne correspond
     */
    public BlueCard getCardInPlay(String cardName) {
        for (BlueCard c : inPlay) {
            if (c.getName().equals(cardName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Augmente le nombre de points de vie du joueur de la valeur indiquée, sans toutefois dépasser la valeur maximale
     *
     * @param n nombre de points de vie à ajouter (doit être positif)
     */
    public void incrementHealth(int n) {
        healthPoints += n;
        if (healthPoints > getHealthPointsMax()) {
            healthPoints = getHealthPointsMax();
        }
    }

    /**
     * Décrémente le nombre de points de vie du joueur de la valeur indiquée. Si les points de vie du joueur passent à
     * 0 ou moins mais que le joueur a des Bières en main et qu'il peut les utiliser, elles sont automatiquement
     * utilisées pour le maintenir en vie.
     * <p>
     * Si les points de vie du joueur sont à 0 ou moins, le joueur est mort et retiré du jeu.
     *
     * @param n        nombre de points de vie à retirer (doit être positif)
     * @param attacker joueur qui est responsable des dégâts subis ou {@code null} si aucun joueur n'est directement
     *                 responsable (p.ex. Dynamite)
     */
    public void decrementHealth(int n, Player attacker) {
        healthPoints-=n;
        //Pouvoir de Bart Cassidy
        if(bangCharacter.getName().equals("Bart Cassidy")) {
            for(int i=n; i>0; i--)
                drawToHand();
        }
        //Pouvoir de Gringo
        if(attacker != null && bangCharacter.getName().equals("El Gringo")){
            addToHand(attacker.removeRandomCardFromHand());
        }

        //utilise autant de bière qu'il faut pour revenir a 1 pv (si il y en a)
        while(isDead() && getCardInHand("Beer") != null) {
            getCardInHand("Beer").playedBy(this);
        }
        if(isDead()) {
            game.removePlayer(this);
            //Si le joueur est tué par quelqu'un
            if (attacker != null) {
                //Si joueur tué est un Hors-La-Loi, le tueur pioche 3 cartes
                if (this.getRole() == Role.OUTLAW)
                    for (int i = 0; i < 3; i++)
                        attacker.drawToHand();
                else if (this.getRole() == Role.DEPUTY)
                    //Si le shériff tue un adjoint, le shériff perd toutes ses cartes de la main et devant lui
                    if (attacker.getRole() == Role.SHERIFF) {
                        attacker.hand.removeAll(attacker.getHand());
                        attacker.inPlay.removeAll(attacker.getInPlay());
                        if(attacker.getWeapon()!=null)
                            attacker.discard(attacker.weapon);
                    }
                Player sam = null; //par defaut sam nexiste pas
                for(Player b : this.getOtherPlayers()){ //on fait le tour des joueurs
                    if(b.getBangCharacter().getName().equals("Vulture Sam")){
                         sam = b; //si sam existe il prend la valeur du joueur
                    }

                    //si sam existe il prend les cartes
                    if(sam != null){
                        for (BlueCard c : inPlay) {
                            sam.addToHand(c);
                        }
                        for (Card c : hand) {
                            sam.addToHand(c);
                        }
                    }

                    //dans les autres cas, les cartes sont retirées et mises dans la défausse
                    for (Iterator<BlueCard> it = inPlay.iterator(); it.hasNext();) {
                        BlueCard o = it.next();
                        it.remove(); //Supprime o de c
                    }
                    for (Iterator<Card> it = hand.iterator(); it.hasNext();) {
                        Card o = it.next();
                        it.remove(); //Supprime o de c
                    }
                }
            }
        }
    }

    /**
     * prend en parametre le nom de la carte à renvoyer
     * retourne la première carte parmi les cartes dans la main du joueur ayant le nom passé en argument, ou null
     * si aucune carte ne correspond
     */
    public Card getCardInHand(String cardName) {
        for (Card c : hand) {
            if (c.getName().equals(cardName)) {
                return c;
            }
        }
        return null;
    }

    /**
     * @param player autre joueur
     * @return distance à laquelle le joueur courant voit le joueur passé en paramètre
     */
    public int distanceTo(Player player) {
        int distance = game.getPlayerDistance(this, player);

        if(player.hasBlueCardName("Mustang"))
            distance++;
        if(hasBlueCardName("Scope"))
            distance--;
        if(player.getBangCharacter().getName().equals("Paul Regret"))
            distance++;
        if(getBangCharacter().getName().equals("Rose Doolan"))
            distance--;
        if(distance<1)
            distance=1;
        return distance;
    }

    /**
     * Pioche une carte
     *
     * @return la carte qui a été piochée
     */
    public Card drawCard() {
        return game.drawCard();
    }

    /**
     * Défausse une carte
     *
     * @param card carte à défausser
     */
    public void discard(Card card) {
        if(card.getName().equals("Mustang")){
            setHasMustang(false);
        } //modifie par Mathis
        game.addToDiscard(card);
    }

    /**
     * Ajoute une carte à la main du joueur
     * @param card carte à ajouter
     */
    public void addToHand(Card card) {
        hand.add(card);
    }

    /**
     * Pioche une carte, qui est mise dans la main du joueur
     *
     * @return la carte qui a été piochée
     */
    public Card drawToHand() {
        Card card = game.drawCard();
        hand.add(card);
        return card;
    }

    /**
     * Retire une carte de la main du joueur
     *
     * @param card carte à retirer
     * @return true si la carte a bien été retirée, false sinon (la carte n'était pas dans la main du joueur)
     */
    public boolean removeFromHand(Card card) {
        if(this.getBangCharacter().getName().equals("Suzy Lafayette") && hand.size()<=1)
            drawToHand();
        return hand.remove(card);
    }

    /**
     * Défausse une carte de la main du joueur
     * <p>
     * La fonction ne fait rien si la carte n'est pas dans la main du joueur
     *
     * @param c carte à défausser
     */
    public void discardFromHand(Card c) {
        if (removeFromHand(c)) {
            game.addToDiscard(c);
        }
    }

    /**
     * Retire une carte aléatoire de la main du joueur
     *
     * @return la carte qui a été retirée (ou null si le joueur n'a pas de carte en main)
     */
    public Card removeRandomCardFromHand() {
        if (hand.size() > 0) {
            Random random = new Random();
            Card card = hand.get(random.nextInt(hand.size()));
            removeFromHand(card);
            return card;
        }
        return null;
    }

    /**
     * Renvoie une carte retournée sur la pioche pour un tirage aléatoire (action "dégainer").
     * Cette action peut être modifiée par le personnage du joueur.
     *
     * @return la carte qui a été dégainée
     */
    public Card randomDraw() {
        return bangCharacter.randomDraw(this);
    }

    /**
     * Attend une entrée de la part du joueur (au clavier) et renvoie le choix du joueur.
     * Cette méthode lit l'entrée clavier jusqu'à ce qu'un choix valide (un élément de {@code choices}
     * ou éventuellement la chaîne vide si l'utilisateur est autorisé à passer) soit
     * entré par l'utilisateur. Lorsqu'un choix valide est obtenu, il est renvoyé par la fonction.
     * <p>
     * Si l'ensemble {@code choices} ne comporte qu'un seul élément et que {@code canPass} est faux,
     * l'unique choix valide est automatiquement renvoyé sans lire l'entrée de l'utilisateur.
     * <p>
     * Si l'ensemble des choix est vide, la chaîne vide ("") est automatiquement renvoyée par la méthode
     * (indépendamment de la valeur de {@code canPass}).
     * <p>
     * Exemple d'utilisation pour demander à un joueur de répondre à une question par "oui" ou "non" (en affichant
     * des boutons pour les deux options) :
     * <p>
     * {@code
     * List<String> choices = Arrays.asList("oui", "non");
     * String input = p.choose("Voulez vous faire ceci ?", choices, true, false);
     * }
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur
     *                    la nature du choix qui est attendu
     * @param choices     une liste de chaînes de caractères correspondant aux
     *                    choix valides attendus du joueur (la liste sera convertie en ensemble
     *                    par la fonction pour éliminer les doublons, ce qui permet de compter
     *                    correctement le nombre d'options disponibles)
     * @param showButtons indique s'il faut afficher des boutons pour permettre au joueur de choisir une des options.
     *                    Si c'est le cas, un bouton sera affiché pour chaque valeur dans `choices` (en mode console
     *                    les noms des boutons sont affichés après l'instruction séparés par "/" pour indiquer au
     *                    joueur les choix valides)
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans
     *                    faire de choix. S'il est autorisé à passer, c'est la chaîne de
     *                    caractères vide ("") qui signifie qu'il désire passer.
     * @return le choix de l'utilisateur.
     */
    public String choose(String instruction, List<String> choices, boolean showButtons, boolean canPass) {
        // on retire les doublons de la liste des choix
        HashSet<String> distinctChoices = new HashSet<>(choices);
        // Aucun choix disponible
        if (distinctChoices.isEmpty())
            return "";
        else // Un seul choix possible (renvoyer cet unique élément)
            if (distinctChoices.size() == 1 && !canPass)
                return distinctChoices.iterator().next();
            else {
                String input;
                List<String> buttons = showButtons ? choices : new ArrayList<>();
                // Lit l'entrée de l'utilisateur jusqu'à obtenir un choix valide
                while (true) {
                    game.prompt(this, instruction, buttons, canPass);
                    input = game.readLine();
                    // si une réponse valide est obtenue, elle est renvoyée
                    if (distinctChoices.contains(input) || (canPass && input.equals("")))
                        return input;
                }
            }
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * Dans cette méthode, la liste des choix est donnée sous la forme d'une liste de cartes et le joueur doit
     * choisir le nom d'une de ces cartes.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la nature du choix qui est attendu
     * @param choices     liste de cartes parmi lesquelles l'utilisateur doit choisir
     * @param showButtons indique s'il faut afficher des boutons pour permettre au joueur de choisir une des options.
     *                    Si c'est le cas, un bouton sera affiché pour chaque valeur dans `choices` (en mode console
     *                    les noms des boutons sont affichés après l'instruction séparés par "/" pour indiquer au
     *                    joueur les choix valides)
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans faire de choix.
     *                    S'il est autorisé à passer, c'est la chaîne de caractères vide ("") qui signifie
     *                    qu'il désire passer (dans ce cas la fonction renvoie {@code null})
     * @return une carte de la liste {@code choices} dont le nom correspond au nom entré par le joueur (ou {@code null}
     * si le joueur passe ou si aucun choix n'est donné).
     */
    public Card chooseCard(String instruction, List<Card> choices, boolean showButtons, boolean canPass) {
        List<String> cardNames = new ArrayList<>();
        for (Card card : choices) {
            cardNames.add(card.getName());
        }
        String cardName = choose(
                instruction,
                cardNames,
                showButtons,
                canPass);
        for (Card card : choices) {
            if (card.getName().equals(cardName)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Attend une entrée de la part du joueur et renvoie le choix du joueur.
     * Dans cette méthode, la liste des choix est donnée sous la forme d'une liste de joueurs et le joueur doit
     * choisir le nom d'un de ces joueurs.
     *
     * @param instruction message à afficher à l'écran pour indiquer au joueur la nature du choix qui est attendu
     * @param choices     liste de joueurs parmi lesquels l'utilisateur doit choisir
     * @param canPass     booléen indiquant si le joueur a le droit de passer sans faire de choix.
     *                    S'il est autorisé à passer, c'est la chaîne de caractères vide ("") qui signifie
     *                    qu'il désire passer (dans ce cas la fonction renvoie {@code null})
     * @return un joueur de la liste {@code choices} dont le nom correspond au nom entré par le joueur (ou {@code null}
     * si le joueur passe ou si aucun choix n'est donné).
     */
    public Player choosePlayer(String instruction, List<Player> choices, boolean canPass) {
        List<String> playerNames = new ArrayList<>();
        for (Player player : choices) {
            playerNames.add(player.getName());
        }
        String playerName = choose(
                instruction,
                playerNames,
                false,
                canPass);
        for (Player player : choices) {
            if (player.getName().equals(playerName)) {
                return player;
            }
        }
        return null;
    }

    /**
     * Renvoie une représentation de l'état du joueur sous forme d'une chaîne de caractères.
     * <p>
     * Cette représentation comporte
     * <ul>
     *   <li> le nom du joueur et le nom de son personnage
     *   <li> son rôle et son nombre de points de vie
     *   <li> la liste des cartes qu'il a en jeu
     *   <li> la liste des cartes dans sa main
     * </ul>
     * <p>
     * On pourrait par exemple avoir l'affichage suivant:
     * <pre>
     *   - John (Paul Regret)
     *   Rôle: SHERIFF      HP: ♥♥♥♥
     *   Arme: Remington (3)     En Jeu: Barrel (K♠)
     *   Main: Bang! (A♦), Bang! (6♣), Duel (8♣), Panic! (A♥)
     * </pre>
     *
     * @return une chaîne de caractères décrivant le joueur
     */
    @Override
    public String toString() {
        char[] hpChars = new char[healthPoints];
        Arrays.fill(hpChars, '♥');
        char[] missingHpChars = new char[getHealthPointsMax() - healthPoints];
        Arrays.fill(missingHpChars, '.');
        String weaponString = weapon == null ? "--" : weapon.getName();

        StringJoiner inPlayJoiner = new StringJoiner(", ");
        for (Card card : inPlay) {
            inPlayJoiner.add(card.toString());
        }
        StringJoiner handJoiner = new StringJoiner(", ");
        for (Card card : hand) {
            handJoiner.add(card.toString());
        }
        return String.format("  - %s (%s)\n", name, bangCharacter.getName())
                + String.format("  Rôle: %s      HP: %s%s\n", role, new String(hpChars), new String(missingHpChars))
                + String.format("  Arme: %s (%d)     En Jeu: %s\n", weaponString, getWeaponRange(), inPlayJoiner.toString())
                + String.format("  Main: %s\n", handJoiner.toString());
    }

    /**
     * @return une chaîne de caractères représentant les informations du joueur au format JSON
     * (utilisée par l'interface graphique)
     */
    public String toJSON() {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(String.format("\"name\": \"%s\"", name));
        joiner.add(String.format("\"hp\": %d", healthPoints));
        joiner.add(String.format("\"hp_max\": %d", getHealthPointsMax()));
        joiner.add(String.format("\"character\": \"%s\"", bangCharacter.getName()));
        joiner.add(String.format("\"role\": \"%s\"", role));
        if (weapon != null) {
            joiner.add(String.format("\"weapon\": %s", weapon.toJSON()));
        }

        StringJoiner cardsJoiner;
        // main du joueur
        cardsJoiner = new StringJoiner(", ");
        for (Card c : hand) {
            cardsJoiner.add(c.toJSON());
        }
        joiner.add(String.format("\"hand\": [%s]", cardsJoiner.toString()));

        // cartes en jeu
        cardsJoiner = new StringJoiner(", ");
        for (Card c : inPlay) {
            cardsJoiner.add(c.toJSON());
        }
        joiner.add(String.format("\"in_play\": [%s]", cardsJoiner.toString()));

        return "{" + joiner.toString() + "}";
    }

    /**
     * Joue une carte de la main du joueur (ne fait rien si la carte n'est pas dans la main du joueur)
     *
     * @param card la carte à jouer
     */
    public void playFromHand(Card card) {
        if (removeFromHand(card)) {
            card.playedBy(this);
        }
    }

    /**
     * Ajoute une carte à la liste des cartes que le joueur a devant lui ("en jeu")
     *
     * @param card carte à ajouter à la liste
     */
    public void addToInPlay(BlueCard card) {
        inPlay.add(card);
    }

    /**
     * Retire une carte de la liste des cartes que le joueur a devant lui ("en jeu")
     *
     * @param card carte à retirer de la liste
     */
    public void removeFromInPlay(BlueCard card) {
        inPlay.remove(card);
    }

    //defausse une carte de la liste des cartes que le joueur a devant lui
    public void discardFromInPlay(BlueCard c) {
        removeFromInPlay(c);
        game.addToDiscard(c);
    }

    /**
     * Exécute un tour complet du joueur. Le tour comprend plusieurs phases :
     * <ul>
     *     <li> Résolution des effets préliminaires (Dynamite et Jail)
     *     <li> Piocher des cartes en main (en général 2 cartes, mais peut être modifié par le personnage)
     *     <li> Jouer des cartes que le joueur a en main
     *     <li> Défausser des cartes si le joueur a plus de cartes qu'il a de points de vie restants
     * </ul>
     */
    public void playTurn() {
        // phase 0: setup et résolution des effets préliminaires (dynamite, prison, etc...)
        // dynamite : tire une carte qui sera ensuite défausser si entre 2 et 9 de pique la dynamite explose
        //et fait 3 dégâts sinon passe au joueur à sa gauche
        BlueCard dynamite = this.getCardInPlay("Dynamite");
        BlueCard jail = this.getCardInPlay("Jail");
        while (dynamite != null){
            dynamite.playedBy(this);
            dynamite = this.getCardInPlay("Dynamite");
        }

        //tire une carte si coeur la carte est défaussée il joue normalement, sinon le joueur la défausse et passe son tour
        //ne peut pas être utilisé contre le shériff
        if(jail != null){
            jail.playedBy(this);
            return;
        }

        // phase 1: piocher des cartes
        bangCharacter.onStartTurn(this);

        // phase 2: jouer des cartes
        while (true) {
            List<Card> possibleCards = new ArrayList<>();
            for (Card c : hand) {
                if (c.canPlayFromHand(this)) {
                    possibleCards.add(c);
                }
            }
            Card card = chooseCard("Choisissez une carte à jouer", possibleCards, false, true);
            if (card == null) break;
            playFromHand(card);
        }

        // phase 3: défausser les cartes en trop
        while (hand.size() > healthPoints) {
            Card card = chooseCard(String.format("Défaussez pour n'avoir que %d carte(s) en main", healthPoints), hand, false, false);
            if (card != null) {
                discardFromHand(card);
            }
        }
        //On remet la possibilité de jouer des bang pour les duels et le tour d'après
        bangPlayed = false;
    }



    public void setHasMustang(boolean hasMustang) {
        this.hasMustang = hasMustang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (!name.equals(player.name)) return false;
        if (role != player.role) return false;
        if (!bangCharacter.equals(player.bangCharacter)) return false;
        return game.equals(player.game);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + role.hashCode();
        result = 31 * result + bangCharacter.hashCode();
        result = 31 * result + game.hashCode();
        return result;
    }
}
