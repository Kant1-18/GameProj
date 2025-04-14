package fr.supdevinci.game_proj;

import java.util.ArrayList;
import java.util.Arrays;

public class Deck {
    private String color;
    private ArrayList<Card> cards;

    public Deck(String color) {
        this.color = color;
        this.cards = new ArrayList<Card>();
    }

    public ArrayList<Card> getCards() { return cards; }

    public Card drawCard() {
        Card card = this.cards.get(0);
        this.cards.remove(0);
        return card;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    private void shuffle() {
        ArrayList<Card> shuffledCards = new ArrayList<>();
        while (this.cards.size() > 0) {
            int randomIndex = (int) (Math.random() * this.cards.size());
            shuffledCards.add(this.cards.get(randomIndex));
            this.cards.remove(randomIndex);
        }
        this.cards = shuffledCards;
    }

    public void deckInit() {
        this.cards.add(new Card(null, color));
        for (int i = 1; i <= 7; i++) {
            this.cards.add(new Card(i, this.color));
        }

        this.shuffle();
    }
}
