package fr.supdevinci.game_proj;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CardAssets {

    public ArrayList<TextureRegion> cards;
    public TextureRegion token;
    public Texture texture;

    public CardAssets() {
        texture = new Texture(Gdx.files.internal("Cards.png"));
        cards = new ArrayList<>();

        int cardWidth = 130;
        int cardHeight = 180;
        int startX = 10;
        int startY = 15;
        int spacingX = 15;
        int spacingY = 20;

        // Sandy cards
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY;
            cards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        // Bloody Cards
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY + cardHeight + spacingY;
            cards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        token = new TextureRegion(texture, 1785, 1220, 72, 72);
    }

    public void dispose() {
        texture.dispose();
    }
}
