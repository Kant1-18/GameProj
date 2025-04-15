package fr.supdevinci.game_proj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    private ArrayList<Card> playerHand;
    private ArrayList<Image> playerHandImages;

    private Deck sandyDeck;
    private Deck bloodyDeck;

    private ArrayList<Card> discardSandy = new ArrayList<>();
    private ArrayList<Card> discardBloody = new ArrayList<>();

    private Image sandyDiscardImage;
    private Image bloodyDiscardImage;

    private boolean mustDiscard = false;
    private String doubleColor = null;

    private final int cardWidth = 140;
    private final int cardHeight = 180;
    private final int screenWidth = 1920;
    private final int screenHeight = 1080;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.BLACK;

        // Initialisation des decks
        sandyDeck = new Deck("sandy");
        bloodyDeck = new Deck("bloody");
        sandyDeck.deckInit();
        bloodyDeck.deckInit();

        // Main du joueur
        playerHand = new ArrayList<>();
        playerHandImages = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            addCardToHand(sandyDeck.pickCard());
            addCardToHand(bloodyDeck.pickCard());
        }

        // Main du joueur en haut (fixe)
        Card[] topHand = new Card[] {
            new Card(4, "sandy"),
            new Card(5, "bloody"),
        };
        for (int i = 0; i < topHand.length; i++) {
            Image cardImage = new Image(new TextureRegionDrawable(topHand[i].getTextureRegion()));
            cardImage.setSize(cardWidth, cardHeight);
            cardImage.setPosition(
                (screenWidth - (topHand.length * (cardWidth + 20) - 20)) / 2 + i * (cardWidth + 20),
                screenHeight - cardHeight - 50
            );
            stage.addActor(cardImage);
        }

        sandyDiscardImage = new Image(new TextureRegionDrawable(new Card(2, "sandy").getTextureRegion()));
        sandyDiscardImage.setSize(cardWidth, cardHeight);
        sandyDiscardImage.setPosition(50, screenHeight / 2 - cardHeight / 2);
        sandyDiscardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !discardSandy.isEmpty() && !mustDiscard) {
                    Card card = discardSandy.remove(discardSandy.size() - 1);
                    addCardToHand(card);
        
                    // Mettre à jour l’image (si vide, carte par défaut ou transparente ?)
                    if (!discardSandy.isEmpty()) {
                        updateDiscardVisual(sandyDiscardImage, discardSandy.get(discardSandy.size() - 1));
                    }
                }
            }
        });
        stage.addActor(sandyDiscardImage);
        
        bloodyDiscardImage = new Image(new TextureRegionDrawable(new Card(7, "bloody").getTextureRegion()));
        bloodyDiscardImage.setSize(cardWidth, cardHeight);
        bloodyDiscardImage.setPosition(screenWidth - cardWidth - 50, screenHeight / 2 - cardHeight / 2);
        bloodyDiscardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !discardBloody.isEmpty() && !mustDiscard) {
                    Card card = discardBloody.remove(discardBloody.size() - 1);
                    addCardToHand(card);
        
                    if (!discardBloody.isEmpty()) {
                        updateDiscardVisual(bloodyDiscardImage, discardBloody.get(discardBloody.size() - 1));
                    }
                }
            }
        });
        stage.addActor(bloodyDiscardImage);

        // Pioche rouge
        Image bloodyDeckimage = new Image(new TextureRegionDrawable(new Card(1, "bloody").getTextureRegion()));
        bloodyDeckimage.setSize(cardWidth, cardHeight);
        bloodyDeckimage.setPosition(screenWidth - 2 * cardWidth - 450, screenHeight / 2 - cardHeight / 2);
        bloodyDeckimage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !mustDiscard) {
                    addCardToHand(bloodyDeck.pickCard());
                }
            }
        });
        stage.addActor(bloodyDeckimage);

        // Pioche jaune
        Image sandyDeckimage = new Image(new TextureRegionDrawable(new Card(1, "sandy").getTextureRegion()));
        sandyDeckimage.setSize(cardWidth, cardHeight);
        sandyDeckimage.setPosition(50 + cardWidth + 400, screenHeight / 2 - cardHeight / 2);
        sandyDeckimage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !mustDiscard) {
                    addCardToHand(sandyDeck.pickCard());
                }
            }
        });
        stage.addActor(sandyDeckimage);

        // Tableau de score
        Table scoreTable = new Table();
        scoreTable.bottom().left().pad(20).padBottom(100);
        scoreTable.setFillParent(true);

        Label manchesLabel = new Label("Manches : 3", labelStyle);
        Label victoiresLabel = new Label("Victoires : 1", labelStyle);
        Label tourLabel = new Label("Tour : 1", labelStyle);

        manchesLabel.setFontScale(2.0f);
        victoiresLabel.setFontScale(2.0f);
        tourLabel.setFontScale(2.0f);

        scoreTable.add(tourLabel).left().row();
        scoreTable.add(manchesLabel).left().row();
        scoreTable.add(victoiresLabel).left();
        stage.addActor(scoreTable);

        // Boutons de contrôle
        Table buttonTable = new Table();
        buttonTable.bottom().right().pad(40).padBottom(150);
        buttonTable.setFillParent(true);

        TextButton playButton = new TextButton("Jouer", buttonStyle);
        TextButton passButton = new TextButton("Passer mon tour", buttonStyle);

        playButton.getLabel().setFontScale(2.0f);
        passButton.getLabel().setFontScale(2.0f);

        buttonTable.add(playButton).padRight(20).size(300, 100);
        buttonTable.add(passButton).size(300, 100);
        stage.addActor(buttonTable);
    }

    private void addCardToHand(Card card) {
        playerHand.add(card);
        int sandyCount = 0;
        int bloodyCount = 0;
    
        for (Card c : playerHand) {
            if (c.getColor().equals("sandy")) sandyCount++;
            if (c.getColor().equals("bloody")) bloodyCount++;
        }
    
        if (sandyCount > 1) {
            mustDiscard = true;
            doubleColor = "sandy";
        } else if (bloodyCount > 1) {
            mustDiscard = true;
            doubleColor = "bloody";
        } else {
            mustDiscard = false;
            doubleColor = null;
        }
    
        renderPlayerHand();
    }

    private void renderPlayerHand() {
        for (Image img : playerHandImages) {
            stage.getActors().removeValue(img, true);
        }
        playerHandImages.clear();
    
        for (int i = 0; i < playerHand.size(); i++) {
            Card card = playerHand.get(i);
            Image img = new Image(new TextureRegionDrawable(card.getTextureRegion()));
            img.setSize(cardWidth, cardHeight);
            img.setPosition(
                (screenWidth - (playerHand.size() * (cardWidth + 20) - 20)) / 2 + i * (cardWidth + 20),
                50
            );
    
            final int index = i;
    
            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (mustDiscard) {
                        if (playerHand.get(index).getColor().equals(doubleColor)) {
                            discardCard(index);
                        }
                    } else {
                        if (playerHand.size() < 3) {
                            playCardFromHand(index);
                        }
                    }
                }
            });
    
            stage.addActor(img);
            playerHandImages.add(img);
        }
    }

    private void discardCard(int index) {
        Card card = playerHand.get(index);
    
        if (card.getColor().equals("sandy")) {
            discardSandy.add(card);
            updateDiscardVisual(sandyDiscardImage, card);
        } else {
            discardBloody.add(card);
            updateDiscardVisual(bloodyDiscardImage, card);
        }
    
        playerHand.remove(index);
        mustDiscard = false;
        doubleColor = null;
        renderPlayerHand();
    }

    private void updateDiscardVisual(Image image, Card topCard) {
        image.setDrawable(new TextureRegionDrawable(topCard.getTextureRegion()));
    }
    

    private void playCardFromHand(int index) {
        Card card = playerHand.get(index);
        Image played = new Image(new TextureRegionDrawable(card.getTextureRegion()));
        played.setSize(cardWidth, cardHeight);
        played.setPosition(
            card.getColor().equals("sandy") ? 800 : screenWidth - cardWidth - 800,
            250
        );
        stage.addActor(played);
        playerHand.remove(index);
        renderPlayerHand();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        CardAssets.dispose();
    }
}
