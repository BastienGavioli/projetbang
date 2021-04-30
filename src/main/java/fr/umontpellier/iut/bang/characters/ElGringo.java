package fr.umontpellier.iut.bang.characters;

public class ElGringo extends BangCharacter {

    /**
     * Chaque fois qu’il perd un point de vie à cause d’une carte jouée par un autre joueur, il tire une carte
     * au hasard dans la main de ce dernier (une carte par point de vie). Si ce joueur n’a plus de cartes, dommage,
     * il ne peut pas lui en prendre ! N’oubliez pas que les points vie perdus à cause de la dynamite ne sont pas
     * considérés comme étant causés par un joueur.
     * (cf. Player.decrementHealth())
     */

    public ElGringo() {
        super("El Gringo", 3);
    }

}
