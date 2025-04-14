package fr.supdevinci.game_proj;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CardAssets {

    public ArrayList<TextureRegion> cards;
    public TextureRegion token;
    public Texture texture;
    public static ArrayList<TextureRegion> sandCards;
    public static ArrayList<TextureRegion> bloodCards;

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

        // Initialize the sand and blood cards
        initSandCards();
        initBloodCards();
    }

    public void dispose() {
        texture.dispose();
    }

    private void initSandCards() {
        sandCards = new ArrayList<>();
        for (int i = 0; i <=7 ; i++) {
            sandCards.add(cards.get(i));
        }
    }

    private void initBloodCards() {
        bloodCards = new ArrayList<>();
        for (int i = 8; i <=15 ; i++) {
            bloodCards.add(cards.get(i));
        }
    }
}
