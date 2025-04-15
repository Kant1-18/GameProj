package fr.supdevinci.game_proj;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CardAssets {

    public ArrayList<TextureRegion> cards;
    public static TextureRegion token;
    public static Texture texture;
    public static ArrayList<TextureRegion> sandyCards;
    public static ArrayList<TextureRegion> bloodyCards;

    public static void load() {
        texture = new Texture(Gdx.files.internal("Cards.png"));
        int cardWidth = 140;
        int cardHeight = 180;
        int startX = 10;
        int startY = 15;
        int spacingX = 15;
        int spacingY = 20;

        sandyCards = new ArrayList<>();
        bloodyCards = new ArrayList<>();

        // Sand cards (ligne 1)
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY;
            sandyCards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        // Blood cards (ligne 2)
        for (int i = 0; i < 8; i++) {
            int x = startX + i * (cardWidth + spacingX);
            int y = startY + cardHeight + spacingY;
            bloodyCards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        token = new TextureRegion(texture, 1785, 1220, 72, 72);
    }

    public CardAssets() {
        texture = new Texture(Gdx.files.internal("Cards.png"));
        cards = new ArrayList<>();

        Integer cardWidth = 130;
        Integer cardHeight = 180;
        Integer startX = 10;
        Integer startY = 15;
        Integer spacingX = 15;
        Integer spacingY = 20;

        // Sandy cards
        for (Integer i = 0; i < 8; i++) {
            Integer x = startX + i * (cardWidth + spacingX);
            Integer y = startY;
            cards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        // Bloody Cards
        for (Integer i = 0; i < 8; i++) {
            Integer x = startX + i * (cardWidth + spacingX);
            Integer y = startY + cardHeight + spacingY;
            cards.add(new TextureRegion(texture, x, y, cardWidth, cardHeight));
        }

        token = new TextureRegion(texture, 1785, 1220, 72, 72);

        // Initialize the sand and blood cards
        initSandCards();
        initBloodCards();
    }

    public static void dispose() {
        texture.dispose();
    }

    private void initSandCards() {
        sandyCards = new ArrayList<>();
        for (Integer i = 0; i <=7 ; i++) {
            sandyCards.add(cards.get(i));
        }
    }

    private void initBloodCards() {
        bloodyCards = new ArrayList<>();
        for (Integer i = 8; i <=15 ; i++) {
            bloodyCards.add(cards.get(i));
        }
    }
}
