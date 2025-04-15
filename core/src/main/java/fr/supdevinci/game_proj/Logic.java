package fr.supdevinci.game_proj;
import java.util.ArrayList;

public class Logic {

    public static String botLogic(Player player) {
        Integer sandyCard = player.getSandyCard().getValue();
        Integer bloodyCard = player.getBloodyCard().getValue();
        Integer amountTokens = player.getTokens();

        if (amountTokens > 0) {
            if (sandyCard > bloodyCard) {
                if (sandyCard - bloodyCard > 3) {
                    return "draw sand";
                }
            }
            if (sandyCard < bloodyCard) {
                if (bloodyCard - sandyCard > 3) {
                    return "draw blood";
                }
            }
        }

        return "pass turn";
    }

    public static Integer roleDice() {
        return (int) (Math.random() * 6) + 1;
    }

    private Player compareSabacc(ArrayList<Player> sabacc) {
        Player mainSabacc = null;
        for (Player player : sabacc) {
            if (mainSabacc == null) {
                mainSabacc = player;
                continue;
            }
            Integer mainSabaccValue = mainSabacc.getSandyCard().getValue();
            Integer playerValue = player.getSandyCard().getValue();
            if (mainSabaccValue > playerValue) {
                mainSabacc = player;
            }
        }

        return mainSabacc;
    }

    private Player compareWinOrder(ArrayList<Player> other) {
        Player mainWinner = null;
        for (Player player : other) {
            if (mainWinner == null) {
                mainWinner = player;
                continue;
            }
            Integer mainWinnerValue = mainWinner.handValue();
            Integer playerValue = player.handValue();
            if (mainWinnerValue > playerValue) {
                mainWinner = player;
            }
        }
        return mainWinner;
    }

    public Player getWinner(ArrayList<Player> players) {
        ArrayList<Player> sabacc = new ArrayList<>();
        ArrayList<Player> winOrder = new ArrayList<>();

        for (Player player : players) {
            Integer handValue = player.handValue();
            if (handValue == 0) {
                sabacc.add(player);
                continue;
            }
            else {
                winOrder.add(player);
            }
        }

        if (sabacc.size() != 0) {
            return this.compareSabacc(sabacc);
        }
        else {
            return this.compareWinOrder(winOrder);
        }
    }

    public static Round rotation(Round round, Player winner) {
        for (Player player : round.getPlayers()) {
            if (player == winner) {
                player.win();
            }
            else {
                player.lose();
                if (player.getTokens() == 0) {
                    round.removePlayer(player);
                }
            }
        }
        return new Round(round.getPlayers());
    }
}
