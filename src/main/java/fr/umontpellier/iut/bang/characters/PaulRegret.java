package fr.umontpellier.iut.bang.characters;

public class PaulRegret extends BangCharacter {

    /**
     * Paul Regret a un Mustang en jeu à tout moment : tous les autres joueurs doivent
     * ajouter 1 à la distance qui les sépare de lui. S’il a un autre Mustang réel en jeu, il
     * peut utiliser les deux, ce qui augmente la distance de 2 en tout.
     * (cf. Player.distanceTo()
     */

    public PaulRegret() {
        super("Paul Regret", 3);
    }

}
