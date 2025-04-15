package fr.supdevinci.game_proj;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class CardAssets {

    public static Texture texture;
    public static ArrayList<TextureRegion> sandyCards;
    public static ArrayList<TextureRegion> bloodyCards;
    public static TextureRegion backBloody;
    public static TextureRegion backSandy;
    public static TextureRegion token;

    public static void load() {
        texture = new Texture(Gdx.files.internal("Cards.png"));

        int cardWidth = 230;
        int cardHeight = 330;
        int startX = 30;
        int startY = 55;
        int spacingX = 33;
        int spacingY = 52;

        sandyCards = new ArrayList<>();
        bloodyCards = new ArrayList<>();

        // Sandy cards (ligne 1)
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY;
            sandyCards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }
        backSandy = new TextureRegion(texture, 2160, 55, 230, 350);

        // Bloody cards (ligne 2)
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY + cardHeight + spacingY;
            bloodyCards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }
        backBloody = new TextureRegion(texture, 2160, 435, 230, 350);

        // Jeton (position fixe)
        token = new TextureRegion(texture, 1785, 1220, 72, 72);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
