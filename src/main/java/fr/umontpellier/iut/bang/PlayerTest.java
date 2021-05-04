package fr.umontpellier.iut.bang;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import fr.umontpellier.iut.bang.cards.*;
import fr.umontpellier.iut.bang.characters.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
        player1 = new Player("A", new LuckyDuke(),Role.OUTLAW);
        player2 = new Player("B",new BartCassidy(),Role.OUTLAW);
        player3 = new Player("C",new PaulRegret(),Role.OUTLAW);
        player4 = new Player("D",new RoseDoolan(),Role.OUTLAW);
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
        assertFalse(player7.getHand().isEmpty());
        player5.decrementHealth(4, player7);
        assertEquals(0, player5.getHealthPoints());
        assertTrue(player7.getHand().isEmpty());
    }

    /**
     * Tests unitaires de la fonction {@code Player.distanceTo}
     */

    @Test
    void distanceTo_test() {
        assertEquals(1, player1.distanceTo(player2));
        assertEquals(3, player1.distanceTo(player4));
        assertEquals(3, player1.distanceTo(player5));
        assertEquals(2, player1.distanceTo(player6));
        assertEquals(1, player1.distanceTo(player7));
    }

    @Test
    void distanceTo_with_PaulRegret_test() {
        assertEquals(3, player1.distanceTo(player3));
        assertEquals(2, player2.distanceTo(player3));
        assertEquals(3, player5.distanceTo(player3));
        assertEquals(4, player6.distanceTo(player3));
        assertEquals(4, player7.distanceTo(player3));
    }

    @Test
    void distanceTo_with_PaulRegret_and_Mustang_test() {
        BlueCard mustang = new Mustang(1, CardSuit.SPADE);
        player3.getInPlay().add(mustang);
        assertEquals(4, player1.distanceTo(player3));
    }

    @Test
    void distanceTo_with_RoseDoolan_test() {
        assertEquals(2, player4.distanceTo(player1));
        assertEquals(1, player4.distanceTo(player2));
        assertEquals(1, player4.distanceTo(player3));
        assertEquals(1, player4.distanceTo(player5));
        assertEquals(1, player4.distanceTo(player6));
        assertEquals(2, player4.distanceTo(player7));
    }

    @Test
    void distanceTo_with_RoseDoolan_and_Mustang_test() {
        BlueCard mustang = new Mustang(1, CardSuit.SPADE);
        player1.getInPlay().add(mustang);
        assertEquals(3, player4.distanceTo(player1));
    }

    @Test
    void getOtherPlayers_test() {
        List<Player> listOtherPlayers = new ArrayList<>(playerList);
        listOtherPlayers.remove(player1);
        assertIterableEquals(listOtherPlayers, player1.getOtherPlayers());
    }

    @Test
    void removeFromHand_test() {
        assertEquals(0, player1.getHand().size());
        Bang bang = new Bang(1, CardSuit.SPADE);
        player1.getHand().add(bang);
        assertEquals(1, player1.getHand().size());
        player1.removeFromHand(bang);
        assertEquals(0, player1.getHand().size());
    }

    @Test
    void removeFromInPlay_test() {
        assertEquals(0, player1.getInPlay().size());
        BlueCard mustang = new Mustang(1, CardSuit.SPADE);
        player1.getInPlay().add(mustang);
        assertEquals(1, player1.getInPlay().size());
        assertTrue(player1.getInPlay().contains(mustang));
        player1.removeFromInPlay(mustang);
        assertEquals(0, player1.getInPlay().size());
        assertFalse(player1.getInPlay().contains(mustang));
    }

    @Test
    void setWeapon_test() {
        Winchester pioupiou = new Winchester(10, CardSuit.HEART);
        player1.setWeapon(pioupiou);
        assertEquals(pioupiou, player1.getWeapon());
    }
}
