package fr.umontpellier.iut.bang;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

import fr.umontpellier.iut.bang.characters.BangCharacter;
import fr.umontpellier.iut.bang.characters.LuckyDuke;
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
        player1 = new Player("A", new LuckyDuke(), Role.OUTLAW);
        player2 = new Player("B",new LuckyDuke(),Role.OUTLAW);
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
}
