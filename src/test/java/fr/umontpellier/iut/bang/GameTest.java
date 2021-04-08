package fr.umontpellier.iut.bang;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game minimalGame;
    private Player p1, p2, p3, p4;

    @BeforeEach
    void disableConsole() {
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int arg0) {

            }
        }));

    }

    @BeforeEach
    void setUp() {
        List<Player> players = Game.makePlayers(new String[]{"Toto", "Titi", "Tutu", "Tata"});
        minimalGame = new Game(players);

        p1 = minimalGame.getPlayers().get(0);
        p2 = minimalGame.getPlayers().get(1);
        p3 = minimalGame.getPlayers().get(2);
        p4 = minimalGame.getPlayers().get(3);
    }

    @Disabled
    @Test
    void testGetPlayerDistance() {
        assertEquals(1, minimalGame.getPlayerDistance(p1, p2));
        assertEquals(2, minimalGame.getPlayerDistance(p1, p3));
        assertEquals(1, minimalGame.getPlayerDistance(p1, p4));
        assertEquals(2, minimalGame.getPlayerDistance(p2, p4));
    }

}