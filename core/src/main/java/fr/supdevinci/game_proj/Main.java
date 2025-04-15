package fr.supdevinci.game_proj;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture cardSprite;
    private Stage stage;
    private TextureRegion cardRegion;
    private Image[] handImages;

    @Override
    public void create() {
        batch = new SpriteBatch();
        cardSprite = new Texture("Cards.png");
        cardRegion = new TextureRegion(cardSprite);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font;
        buttonStyle.fontColor = Color.BLACK;

        int screenWidth = 1920;
        int screenHeight = 1080;
        int cardWidth = 140;
        int cardHeight = 180;

        Card[] hand = new Card[] {
            new Card(1, "sandy"),
            new Card(3, "bloody"),
            new Card(2, "sandy")
        };

        for (int i = 0; i < hand.length; i++) {
            Image cardImage = new Image(new TextureRegionDrawable(hand[i].getTextureRegion()));
            cardImage.setSize(cardWidth, cardHeight);
            cardImage.setPosition(
            (screenWidth - (hand.length * (cardWidth + 20) - 20)) / 2 + i * (cardWidth + 20),
            50
            );
            stage.addActor(cardImage);
        }

        Card[] topHand = new Card[] {
            new Card(4, "sandy"),
            new Card(5, "bloody"),
            new Card(6, "bloody")
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

        Card redDiscardCard = new Card(7, "bloody");
        System.out.println(redDiscardCard.getValue());
        Image redDiscard = new Image(new TextureRegionDrawable(redDiscardCard.getTextureRegion()));
        redDiscard.setSize(cardWidth, cardHeight);
        redDiscard.setPosition(screenWidth - cardWidth - 50, screenHeight / 2 - cardHeight / 2);
        stage.addActor(redDiscard);

        Card redDeckCard = new Card(2, "bloody");
        System.out.println(redDeckCard.getValue());
        System.out.println(redDeckCard.getColor());
        Image redDeck = new Image(new TextureRegionDrawable(redDeckCard.getTextureRegion()));
        redDeck.setSize(cardWidth, cardHeight);
        redDeck.setPosition(screenWidth - 2 * cardWidth - 450, screenHeight / 2 - cardHeight / 2);
        stage.addActor(redDeck);

        Card yellowDiscardCard = new Card(2, "sandy");
        System.out.println(yellowDiscardCard);
        Image yellowDiscard = new Image(new TextureRegionDrawable(yellowDiscardCard.getTextureRegion()));
        yellowDiscard.setSize(cardWidth, cardHeight);
        yellowDiscard.setPosition(50, screenHeight / 2 - cardHeight / 2);
        stage.addActor(yellowDiscard);

        Card yellowDeckCard = new Card(4, "sandy");
        Image yellowDeck = new Image(new TextureRegionDrawable(yellowDeckCard.getTextureRegion()));
        yellowDeck.setSize(cardWidth, cardHeight);
        yellowDeck.setPosition(50 + cardWidth + 400, screenHeight / 2 - cardHeight / 2);
        stage.addActor(yellowDeck);

        
        Table scoreTable = new Table();
        scoreTable.bottom().left().pad(20).padBottom(100);
        scoreTable.setFillParent(true);

        Label manchesLabel = new Label("Manches : 3", labelStyle);
        Label victoiresLabel = new Label("Victoires : 1", labelStyle);

        manchesLabel.setFontScale(2.0f); 
        victoiresLabel.setFontScale(2.0f); 

        scoreTable.add(manchesLabel).left().row();
        scoreTable.add(victoiresLabel).left();
        stage.addActor(scoreTable);

        
        Table buttonTable = new Table();
        buttonTable.bottom().right().pad(40).padBottom(150); 
        buttonTable.setFillParent(true);

        TextButton playButton = new TextButton("Jouer", buttonStyle);
        TextButton passButton = new TextButton("Passer mon tour", buttonStyle);

        playButton.getLabel().setFontScale(2.0f); 
        passButton.getLabel().setFontScale(2.0f); 

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Jouer !");
            }
        });

        passButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Tour passé !");
            }
        });

        buttonTable.add(playButton).padRight(20).size(300, 100); 
        buttonTable.add(passButton).size(300, 100); 
        stage.addActor(buttonTable);
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
        cardSprite.dispose();
        stage.dispose();
    }
}
