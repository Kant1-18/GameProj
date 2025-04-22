package fr.supdevinci.game_proj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.supdevinci.game_proj.Round;

import java.util.ArrayList;

class RoundTest {

    private Round round;
    private ArrayList<Player> players;

    @BeforeEach
    void setUp() {
        players = new ArrayList<>();
        players.add(new Player("Alice", false));
        players.add(new Player("BotBob", true));

        round = new Round(players);
    }

    @Test
    void testInitialTurnIsZero() {
        assertEquals(0, round.getTurn());
    }

    @Test
    void testIncrementTurn() {
        round.incrementTurn();
        assertEquals(1, round.getTurn());
    }

    @Test
    void testInitRoundSetsTurnAndGivesCards() {
        round.initRound();
        assertEquals(1, round.getTurn());

        for (Player player : round.getPlayers()) {
            assertNotNull(player.getSandyCard(), "Player should have a sandy card after initRound");
            assertNotNull(player.getBloodyCard(), "Player should have a bloody card after initRound");
        }
    }

    @Test
    void testRemovePlayer() {
        Player player = players.get(0);
        round.removePlayer(player);
        assertFalse(round.getPlayers().contains(player), "Player should be removed from the round");
    }

    @Test
    void testPlayTurnIncrementsTurn() {
        int initialTurn = round.getTurn();
        round.initRound(); // important pour que les decks soient remplis
        round.playTurn();
        assertEquals(initialTurn + 1, round.getTurn());
    }
}
