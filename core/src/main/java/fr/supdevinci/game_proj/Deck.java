package fr.supdevinci.game_proj;

import java.util.ArrayList;

public class Deck {
    private ArrayList<SabaccCard> cards;

    public Deck(ArrayList<SabaccCard> cards) {
        this.cards = new ArrayList<SabaccCard>(cards);
    }

    public ArrayList<SabaccCard> getCards() { return cards; }

    public SabaccCard pickCard() {
        SabaccCard card = this.cards.get(0);
        this.cards.remove(0);
        return card;
    }

    public void addCard(SabaccCard card) {
        this.cards.add(card);
    }

    public void shuffle() {
        ArrayList<SabaccCard> shuffledCards = new ArrayList<>();
        while (this.cards.size() > 0) {
            int randomIndex = (int) (Math.random() * this.cards.size());
            shuffledCards.add(this.cards.get(randomIndex));
            this.cards.remove(randomIndex);
        }
        this.cards = shuffledCards;
    }
}
