package fr.supdevinci.game_proj;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import fr.supdevinci.game_proj.Card;
import fr.supdevinci.game_proj.Deck;

class DeckTest {

    @Test
    void testDeckInitialization() {
        Deck deck = new Deck("sandy");
        deck.deckInit();
        ArrayList<Card> cards = deck.getCards();

        assertEquals(21, cards.size(), "Le deck doit contenir 21 cartes après initialisation");

        for (Card card : cards) {
            assertEquals("sandy", card.getColor());
            assertTrue(card.getValue() >= 1 && card.getValue() <= 7, "La valeur de la carte doit être entre 1 et 7");
        }
    }

    @Test
    void testPickCardReducesDeckSize() {
        Deck deck = new Deck("bloody");
        deck.deckInit();
        int initialSize = deck.getCards().size();

        Card picked = deck.pickCard();

        assertNotNull(picked, "La carte piochée ne doit pas être nulle");
        assertEquals(initialSize - 1, deck.getCards().size(), "La taille du deck doit diminuer de 1 après un pick");
    }

    @Test
    void testAddCardIncreasesDeckSize() {
        Deck deck = new Deck("bloody");
        deck.deckInit();
        int initialSize = deck.getCards().size();

        Card newCard = new Card(3, "bloody");
        deck.addCard(newCard);

        assertEquals(initialSize + 1, deck.getCards().size(), "La taille du deck doit augmenter de 1 après ajout");
        assertTrue(deck.getCards().contains(newCard), "Le deck doit contenir la carte ajoutée");
    }
}
