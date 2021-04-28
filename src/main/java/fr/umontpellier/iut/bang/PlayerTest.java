package fr.umontpellier.iut.bang;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import fr.umontpellier.iut.bang.characters.BangCharacter;
import fr.umontpellier.iut.bang.characters.BartCassidy;
import fr.umontpellier.iut.bang.characters.LuckyDuke;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {
    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;
    private Player player5;
    private Player player6;
    private Player player7;
    private List<Player> playerList;
    @BeforeEach
    void initialize(){
        player1 = new Player("A", new LuckyDuke(), Role.OUTLAW);
        player2 = new Player("B",new BartCassidy(),Role.OUTLAW);
        player3 = new Player("C",new LuckyDuke(),Role.OUTLAW);
        player4 = new Player("D",new LuckyDuke(),Role.OUTLAW);
        player5 = new Player("E",new LuckyDuke(),Role.DEPUTY);
        player6 = new Player("F",new LuckyDuke(),Role.RENEGADE);
        player7 = new Player("G",new LuckyDuke(),Role.SHERIFF);
        playerList = new ArrayList<>();
        playerList.add(player1);
        playerList.add(player2);
        playerList.add(player3);
        playerList.add(player4);
        playerList.add(player5);
        playerList.add(player6);
        playerList.add(player7);
        game = new Game(playerList);
    }

    @Test
    void getOtherPlayerSizeTest(){
        assertEquals(6, player1.getOtherPlayers().size());
    }

    /**
     * Tests unitaires de la fonction {@code Player.decrementHealth}
     * attacker = null -> personne n'est à l'origine de la blessure
     */

    @Test
    void decrement_one_to_an_outlaw_test() {
        player1.decrementHealth(1, null);
        assertEquals(3, player1.getHealthPoints());
    }

    @Test
    void decrement_a_deputy_who_dies_test() {
        player5.decrementHealth(4, null);
        assertEquals(0, player5.getHealthPoints());
    }

    @Test
    void decrement_two_to_a_renegade_test() {
        player6.decrementHealth(2, null);
        assertEquals(2, player6.getHealthPoints());
    }

    @Test
    void decrement_one_to_a_sheriff_test() {
        player7.decrementHealth(1, null);
        assertEquals(4, player7.getHealthPoints());
    }

    /**
     * Tests unitaires de la fonction {@code Player.decrementHealth}
     * attacker != null -> quelqu'un est à l'origine de la blessure
     */

    @Test
    void kill_an_outlaw_with_attacker_is_not_null_test() {
        int nbCardsInHand = player2.getHand().size();
        player1.decrementHealth(4, player2);
        assertEquals(0, player1.getHealthPoints());
        assertEquals(nbCardsInHand+3, player2.getHand().size());
    }

    @Test
    void kill_a_deputy_with_attacker_is_a_sheriff_test() {
        player7.drawToHand();
        int nb = player7.getHand().size();
        player5.decrementHealth(4, player7);
        assertEquals(0, player5.getHealthPoints());
        assertEquals(0, player7.getHand().size());
        assertNotEquals(nb, player7.getHand().size());
    }
}
