package fr.supdevinci.game_proj;
import java.util.ArrayList;
import java.util.Arrays;

public class Logic {

    public static String botLogic(Player player) {
        Integer sandCard = player.getSandCard().getValue();
        Integer bloodCard = player.getBloodCard().getValue();
        Integer amountTokens = player.getTokens();

        if (amountTokens > 0) {
            if (sandCard > bloodCard) {
                if (sandCard - bloodCard > 3) {
                    return "draw sand";
                }
            }
            if (sandCard < bloodCard) {
                if (bloodCard - sandCard > 3) {
                    return "draw blood";
                }
            }
        }

        return "pass turn";
    }
}
