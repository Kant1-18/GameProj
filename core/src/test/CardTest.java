package fr.supdevinci.game_proj;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import fr.supdevinci.game_proj.Card;

class CardTest {

    @Test
    void testValidSandyCardCreation() {
        Card card = new Card(3, "sandy");
        assertEquals(3, card.getValue());
        assertEquals("sandy", card.getColor());
        assertNotNull(card.getTextureRegion());
    }

    @Test
    void testValidBloodyCardCreation() {
        Card card = new Card(5, "bloody");
        assertEquals(5, card.getValue());
        assertNotNull(card.getTextureRegion());
    }

    @Test
    void testInvalidValueThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card(8, "sandy");
        });
        assertTrue(exception.getMessage().contains("Value must be between 0 and 7"));
    }

    @Test
    void testInvalidColorThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Card(3, "blue");
        });
        assertTrue(exception.getMessage().contains("Color must be either 'sandy' or 'bloody'"));
    }
}
