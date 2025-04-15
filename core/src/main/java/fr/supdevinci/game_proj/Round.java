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
        this.sandyDeck = new Deck("sand");
        this.bloodyDeck = new Deck("blood");
        this.sandyDiscard = new Deck("sand");
        this.bloodyDiscard = new Deck("blood");
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
        this.sandyDeck.deckInit();
        this.bloodyDeck.deckInit();

        for (Player player : this.players) {
            player.setSandyCard(this.sandyDeck.pickCard());
            player.setBloodyCard(this.bloodyDeck.pickCard());
        }
    }

    public void play() {
        this.initRound();
        while (this.Turn <= 3) {
            for (Player player : this.players) {
                if (player.getIsBot()) {
                    String result = Logic.botLogic(player);
                    switch (result) {
                        case "draw sand":
                            this.sandyDiscard.addCard(player.getSandyCard());
                            player.setSandyCard(this.sandyDeck.pickCard());
                            break;
                        case "draw blood":
                            this.bloodyDiscard.addCard(player.getBloodyCard());
                            player.setBloodyCard(this.bloodyDeck.pickCard());
                            break;
                        case "pass turn":
                            continue;
                    }
                }
                else {
                    break;
                }
            }

            this.incrementTurn();
            break;
        }
    }
}
