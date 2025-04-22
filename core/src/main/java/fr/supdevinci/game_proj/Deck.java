package fr.supdevinci.game_proj;

import java.util.ArrayList;

public class Deck <T extends Card> {
    private ArrayList<T> cards;

    public Deck(ArrayList<T> cards) {
        this.cards = new ArrayList<T>(cards);
    }

    public ArrayList<T> getCards() { return cards; }

    public T pickCard() {
        T card = this.cards.get(0);
        this.cards.remove(0);
        return card;
    }

    public void addCard(T card) {
        this.cards.add(card);
    }

    public void shuffle() {
        ArrayList<T> shuffledCards = new ArrayList<>();
        while (this.cards.size() > 0) {
            int randomIndex = (int) (Math.random() * this.cards.size());
            shuffledCards.add(this.cards.get(randomIndex));
            this.cards.remove(randomIndex);
        }
        this.cards = shuffledCards;
    }
}
