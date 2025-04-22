package fr.supdevinci.game_proj;
import java.util.ArrayList;

public class Round {
    private Integer Turn;
    private ArrayList<Player> players;
    private Deck sandyDeck;
    private Deck bloodyDeck;
    private Deck sandyDiscard;
    private Deck bloodyDiscard;

    public Round(ArrayList<Player> players) {
        this.players = players;
        this.sandyDeck = Logic.deckInit("sandy");
        this.bloodyDeck = Logic.deckInit("bloody");
        this.sandyDiscard = Logic.deckInit("sandy");
        this.bloodyDiscard = Logic.deckInit("bloody");
        this.Turn = 0;
    }

    public Integer getTurn() { return Turn; }

    public void incrementTurn() { this.Turn += 1; }

    public ArrayList<Player> getPlayers() { return players; }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void initRound() {
        this.Turn = 1;
        this.sandyDeck = Logic.deckInit("sandy");
        this.bloodyDeck = Logic.deckInit("bloody");

        for (Player player : this.players) {
            player.setSandyCard(this.sandyDeck.pickCard());
            player.setBloodyCard(this.bloodyDeck.pickCard());
        }
    }

    public void playTurn() {
        for (Player player : this.players) {
            if (player.getIsBot()) {
                String result = Logic.botLogic(player);
                switch (result) {
                    case "draw sandy":
                        this.sandyDiscard.addCard(player.getSandyCard());
                        player.setSandyCard(this.sandyDeck.pickCard());
                        continue;
                    case "draw bloody":
                        this.bloodyDiscard.addCard(player.getBloodyCard());
                        player.setBloodyCard(this.bloodyDeck.pickCard());
                        continue;
                    case "pass turn":
                        continue;
                }
            }
            else {
                break;
            }
        }
        this.incrementTurn();
    }
}
