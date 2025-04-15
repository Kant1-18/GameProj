package fr.supdevinci.game_proj;
// import java.util.ArrayList;
// import java.util.Arrays;

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
}
