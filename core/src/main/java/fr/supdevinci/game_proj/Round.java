package fr.supdevinci.game_proj;
import java.util.ArrayList;

public class Round {
    private Integer Turn;
    private ArrayList<Player> players;
    private Deck sandDeck;
    private Deck bloodDeck;
    private Deck sandDiscard;
    private Deck bloodDiscard;

    public Round(ArrayList<Player> players) {
        this.players = players;
        this.sandDeck = new Deck("sand");
        this.bloodDeck = new Deck("blood");
        this.sandDiscard = new Deck("sand");
        this.bloodDiscard = new Deck("blood");
        this.Turn = 0;
    }

    public Integer getTurn() { return Turn; }

    public void incrementTurn() { this.Turn += 1; }

    public ArrayList<Player> getPlayers() { return players; }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    private void initRound() {
        this.Turn = 0;
        this.sandDeck.deckInit();
        this.bloodDeck.deckInit();

        for (Player player : this.players) {
            player.setSandCard(this.sandDeck.pickCard());
            player.setBloodCard(this.bloodDeck.pickCard());
        }
    }

    public void play() {
        this.initRound();
        while (this.Turn <= 3) {
            for (Player player : this.players) {
                if (player.getIsBot()) {
                    String result = Logic.botLogic(player);
                    if (result.equals("draw sand")) {
                        this.sandDiscard.addCard(player.getSandCard());
                        player.setSandCard(this.sandDeck.pickCard());
                    }
                    if (result.equals("draw blood")) {
                        this.bloodDiscard.addCard(player.getBloodCard());
                        player.setBloodCard(this.bloodDeck.pickCard());
                    }
                    if (result.equals("pass turn")) {
                        continue;
                    }
                } else {
                    // Player logic
                }
            }

            this.incrementTurn();
        }
    }
}
