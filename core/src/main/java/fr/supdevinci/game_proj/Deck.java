package fr.supdevinci.game_proj;

import java.util.Arrays;

public class Deck {
    private String color;
    private Card[] cards;

    public Deck(String color, Card[] cards) {
        this.color = color;
        this.cards = cards;
    }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }

    public Card[] getCards() { return cards; }

    public void setCards(Card[] cards) { this.cards = cards; }

    public Card drawCard() {
        Card card = cards[-1];
        this.cards = Arrays.copyOfRange(cards, 0, cards.length - 1);
        return card;
    }

    public void addCard(Card card) {
        this.cards = Arrays.copyOf(cards, cards.length + 1);
        this.cards[cards.length - 1] = card;
    }

    public void shuffle() {
        int i, j;
        Card temp;
        for (i = 0; i < cards.length; i++) {
            j = i + (int) (Math.random() * (cards.length - i));
            temp = cards[i];
            cards[i] = cards[j];
            cards[j] = temp;
        }
    }
}
