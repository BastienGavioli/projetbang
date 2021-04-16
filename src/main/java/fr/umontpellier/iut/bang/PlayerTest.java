package fr.umontpellier.iut.bang;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
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
        player1 = new Player("A",null,null);
        player2 = new Player("B",null,null);
        player3 = new Player("C",null,null);
        player4 = new Player("D",null,null);
        player5 = new Player("E",null,null);
        player6 = new Player("F",null,null);
        player7 = new Player("G",null,null);
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
    @Disabled
    void getOtherPlayerSizeTest(){
        assertEquals(6, player1.getOtherPlayers().size());
    }
}
