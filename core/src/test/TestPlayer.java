import fr.supdevinci.game_proj.Card;
import fr.supdevinci.game_proj.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    @Test
    public void betShouldDecreaseTokensAndIncreaseStake() {
        // Arrange
        var player = new Player(false, "Alice", 5, 0, new Card(4, "sandy"), new Card(2, "bloody"));

        // Act
        var result = player.bet();

        // Assert
        assertTrue(result);
        assertEquals(4, player.getTokens());
        assertEquals(1, player.getStake());
    }

    @Test
    public void betShouldReturnFalseWhenNoTokens() {
        // Arrange
        var player = new Player(false, "Bob", 0, 0, new Card(3, "sandy"), new Card(5, "bloody"));

        // Act
        var result = player.bet();

        // Assert
        assertFalse(result);
        assertEquals(0, player.getTokens());
        assertEquals(0, player.getStake());
    }

    @Test
    public void loseShouldRemoveOneTokenAndResetStake() {
        // Arrange
        var player = new Player(false, "Charly", 3, 2, new Card(5, "sandy"), new Card(4, "bloody"));

        // Act
        player.lose();

        // Assert
        assertEquals(2, player.getTokens());
        assertEquals(0, player.getStake());
    }

    @Test
    public void winShouldAddStakeToTokensAndResetStake() {
        // Arrange
        var player = new Player(false, "Daisy", 2, 3, new Card(6, "sandy"), new Card(1, "bloody"));

        // Act
        player.win();

        // Assert
        assertEquals(5, player.getTokens());
        assertEquals(0, player.getStake());
    }

    @Test
    public void handValueShouldComputeCorrectly() {
        // Arrange
        var player = new Player(false, "Eve", 3, 0, new Card(6, "sandy"), new Card(3, "bloody"));

        // Act
        int result = player.handValue();

        // Assert
        assertEquals(3, result); // 6 - 3
    }

    @Test
    public void handValueWithZeroShouldReturnZero() {
        // Arrange
        var player = new Player(false, "Zero", 3, 0, new Card(0, "sandy"), new Card(0, "bloody"));

        // Act
        int result = player.handValue();

        // Assert
        assertEquals(0, result);
    }

    @Test
    public void setSandyCardShouldWorkForCorrectColor() {
        // Arrange
        var player = new Player(false, "SandyTest", 3, 0, new Card(3, "sandy"), new Card(2, "bloody"));

        // Act
        player.setSandyCard(new Card(6, "sandy"));

        // Assert
        assertEquals(6, player.getSandyCard().getValue());
    }

    @Test
    public void setSandyCardShouldThrowForWrongColor() {
        // Arrange
        var player = new Player(false, "SandyWrong", 3, 0, new Card(3, "sandy"), new Card(2, "bloody"));

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            player.setSandyCard(new Card(2, "bloody"));
        });
    }

    @Test
    public void setBloodyCardShouldWorkForCorrectColor() {
        // Arrange
        var player = new Player(false, "BloodyTest", 3, 0, new Card(4, "sandy"), new Card(5, "bloody"));

        // Act
        player.setBloodyCard(new Card(6, "bloody"));

        // Assert
        assertEquals(6, player.getBloodyCard().getValue());
    }

    @Test
    public void setBloodyCardShouldThrowForWrongColor() {
        // Arrange
        var player = new Player(false, "BloodyWrong", 3, 0, new Card(4, "sandy"), new Card(5, "bloody"));

        // Assert
        assertThrows(IllegalArgumentException.class, () -> {
            player.setBloodyCard(new Card(3, "sandy"));
        });
    }
}
