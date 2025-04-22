package fr.supdevinci.game_proj;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class DeckTest {
    class TestCard implements Card {}

    private Deck deck;

    @BeforeEach
    void setUp() {
        ArrayList<TestCard> initialCards = new ArrayList<TestCard>();
        initialCards.add(new TestCard());
        initialCards.add(new TestCard());
        initialCards.add(new TestCard());
        deck = new Deck(initialCards);
    }

    @Test
    void testGetCardsReturnsCorrectList() {
        assertEquals(3, deck.getCards().size(), "Deck should contain 3 cards initially");
    }

    @Test
    void testPickCardReturnsAndRemovesCard() {
        Card firstCard = deck.getCards().get(0);
        Card pickedCard = deck.pickCard();

        assertEquals(firstCard, pickedCard, "Picked card should be the first card in the deck");
        assertEquals(2, deck.getCards().size(), "Deck should now contain 2 cards");
    }

    @Test
    void testAddCardIncreasesDeckSize() {
        Card newCard = new TestCard();
        deck.addCard(newCard);

        assertEquals(4, deck.getCards().size(), "Deck should now contain 4 cards after adding one");
        assertTrue(deck.getCards().contains(newCard), "Deck should contain the newly added card");
    }

    @Test
    void testShuffleChangesOrder() {
        ArrayList<TestCard> originalOrder = new ArrayList<>(deck.getCards());
        deck.shuffle();
        // Attention, il existe une chance que le shuffle ne change pas l'ordre par hasard
        boolean orderChanged = false;
        for (int i = 0; i < originalOrder.size(); i++) {
            if (!originalOrder.get(i).equals(deck.getCards().get(i))) {
                orderChanged = true;
                break;
            }
        }
        assertTrue(orderChanged || deck.getCards().size() <= 1, "Deck should be shuffled if more than 1 card");
    }
}
