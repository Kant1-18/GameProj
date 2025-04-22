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
import java.util.Collections;
import java.util.HashMap;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;

    private ArrayList<Card> playerHand;
    private ArrayList<Image> playerHandImages;

    private Deck sandyDeck;
    private Deck bloodyDeck;

    private Player player1;
    private Player bot1;
    private Player bot2;
    private Player bot3;

    private Round round;

    private int manche = 1;
    private int tourDansManche = 1;
    private int currentPlayerIndex = 0;
    private HashMap<String, Integer> victoires = new HashMap<>();
    private ArrayList<Player> players;
    
    private Label tokensLabel;
    private Label manchesLabel;
    private Label tourLabel;
    private boolean gameEnded = false;

    private ArrayList<Card> discardSandy = new ArrayList<>();
    private ArrayList<Card> discardBloody = new ArrayList<>();

    private Image sandyDiscardImage;
    private Image bloodyDiscardImage;

    private boolean mustDiscard = false;
    private String doubleColor = null;

    private final int cardWidth = 120;
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
        player1 = new Player(false, "Joueur", 8, 0, null, null);
        bot1 = new Player(true, "Bot 1", 8, 0, null, null);

        players = new ArrayList<>();
        players.add(player1);
        players.add(bot1);

        round = new Round(players);
        round.initRound();
        tokensLabel = new Label("Jetons : " + player1.getTokens(), labelStyle);
        victoires = new HashMap<>();
        for (Player p : players) {
            victoires.put(p.getName(), 0);
        }

        sandyDeck = new Deck("sandy");
        bloodyDeck = new Deck("bloody");
        sandyDeck.deckInit();
        bloodyDeck.deckInit();

        playerHand = new ArrayList<>();
        playerHandImages = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            addCardToHand(sandyDeck.pickCard());
            addCardToHand(bloodyDeck.pickCard());
        }

        Image sandyBack = new Image(new TextureRegionDrawable(CardAssets.backSandy));
        sandyBack.setSize(cardWidth, cardHeight);
        sandyBack.setPosition(
                (screenWidth - 2 * (cardWidth + 20)) / 2,
                screenHeight - cardHeight - 50
        );
        stage.addActor(sandyBack);

        Image bloodyBack = new Image(new TextureRegionDrawable(CardAssets.backBloody));
        bloodyBack.setSize(cardWidth, cardHeight);
        bloodyBack.setPosition(
                (screenWidth - 2 * (cardWidth + 20)) / 2 + cardWidth + 20,
                screenHeight - cardHeight - 50
        );
        stage.addActor(bloodyBack);

        sandyDiscardImage = new Image(new TextureRegionDrawable(new Card(2, "sandy").getTextureRegion()));
        sandyDiscardImage.setSize(cardWidth, cardHeight);
        sandyDiscardImage.setPosition(500, screenHeight / 2 - cardHeight / 2);
        sandyDiscardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !discardSandy.isEmpty() && !mustDiscard && player1.bet()) {
                    Card card = discardSandy.remove(discardSandy.size() - 1);
                    addCardToHand(card);
                    updateTokenDisplay();
                    if (!discardSandy.isEmpty()) {
                        updateDiscardVisual(sandyDiscardImage, discardSandy.get(discardSandy.size() - 1));
                    }
                }
            }
        });
        stage.addActor(sandyDiscardImage);

        bloodyDiscardImage = new Image(new TextureRegionDrawable(new Card(7, "bloody").getTextureRegion()));
        bloodyDiscardImage.setSize(cardWidth, cardHeight);
        bloodyDiscardImage.setPosition(screenWidth - cardWidth - 500, screenHeight / 2 - cardHeight / 2);
        bloodyDiscardImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !discardBloody.isEmpty() && !mustDiscard && player1.bet()) {
                    Card card = discardBloody.remove(discardBloody.size() - 1);
                    addCardToHand(card);
                    updateTokenDisplay();
                    System.out.println("token" + player1.getTokens());
                    if (!discardBloody.isEmpty()) {
                        updateDiscardVisual(bloodyDiscardImage, discardBloody.get(discardBloody.size() - 1));
                    }
                }
                
            }
        });
        stage.addActor(bloodyDiscardImage);

       
        Image bloodyDeckimage = new Image(new TextureRegionDrawable(CardAssets.backBloody));
        bloodyDeckimage.setSize(cardWidth, cardHeight);
        bloodyDeckimage.setPosition(screenWidth - 2 * cardWidth - 650, screenHeight / 2 - cardHeight / 2);
        bloodyDeckimage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !mustDiscard && player1.bet()) {
                    addCardToHand(bloodyDeck.pickCard());
                    updateTokenDisplay();
                }
            }
        });
        stage.addActor(bloodyDeckimage);

        Image sandyDeckimage = new Image(new TextureRegionDrawable(CardAssets.backSandy));
        sandyDeckimage.setSize(cardWidth, cardHeight);
        sandyDeckimage.setPosition(50 + cardWidth + 600, screenHeight / 2 - cardHeight / 2);
        sandyDeckimage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (playerHand.size() < 3 && !mustDiscard && player1.bet()) {
                    addCardToHand(sandyDeck.pickCard());
                    updateTokenDisplay();
                }
            }
        });
        stage.addActor(sandyDeckimage);

        Table scoreTable = new Table();
        scoreTable.bottom().left().pad(20).padBottom(100);
        scoreTable.setFillParent(true);

        manchesLabel = new Label("Manche : " + manche, labelStyle);
        tourLabel = new Label("Tour : " + tourDansManche, labelStyle);
        Label victoiresLabel = new Label("Victoires : 1", labelStyle);
        
        

        manchesLabel.setFontScale(2.0f);
        victoiresLabel.setFontScale(2.0f);
        tourLabel.setFontScale(2.0f);
        tokensLabel.setFontScale(2.0f);

        scoreTable.add(tokensLabel).left().row();
        scoreTable.add(tourLabel).left().row();
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
        passButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nextPlayer();
            }
        });

        buttonTable.add(playButton).padRight(20).size(300, 100);
        buttonTable.add(passButton).size(300, 100);
        stage.addActor(buttonTable);
    }

    private void addCardToHand(Card card) {
        playerHand.add(card);
        int sandyCount = 0;
        int bloodyCount = 0;

        for (Card c : playerHand) {
            if (c.getColor().equals("sandy"))
                sandyCount++;
            if (c.getColor().equals("bloody"))
                bloodyCount++;
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
                    50);

            final int index = i;

            img.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (mustDiscard) {
                        if (playerHand.get(index).getColor().equals(doubleColor)) {
                            discardCard(index);
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
        nextPlayer();
    }

    private void updateDiscardVisual(Image image, Card topCard) {
        image.setDrawable(new TextureRegionDrawable(topCard.getTextureRegion()));
    }

    private void updateTokenDisplay() {
        tokensLabel.setText("Jetons : " + player1.getTokens());
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

    private void endManche() {
        Logic logic = new Logic();
        Player winner = logic.getWinner(players);
        System.out.println("Gagnant de la manche : " + winner.getName());
    
        victoires.put(winner.getName(), victoires.getOrDefault(winner.getName(), 0) + 1);
    
        round = Logic.rotation(round, winner);
        players = round.getPlayers();
    
        if (players.size() == 1) {
            displayEndGame(players.get(0).getName());
            gameEnded = true;
            return;
        }
    
        manche++;
        if (manche > 3) {
            int min = Collections.min(victoires.values());
            players.removeIf(p -> victoires.getOrDefault(p.getName(), 0) == min);
    
            if (players.size() == 1) {
                displayEndGame(players.get(0).getName());
            } else {
                Player finalWinner = new Logic().getWinner(players);
                displayEndGame(finalWinner.getName());
            }
    
            gameEnded = true;
            return;
        }
    
        tourDansManche = 1;
        currentPlayerIndex = 0;
        updateLabels();
        round.initRound();
    }

    private void displayEndGame(String winnerName) {
        Label finLabel = new Label("FIN DE PARTIE\nVainqueur : " + winnerName,
                new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        finLabel.setFontScale(3f);
        finLabel.setPosition((screenWidth - 400) / 2f, screenHeight / 2f);
        stage.addActor(finLabel);
    }
    
    

    private void botPlays(Player bot) {
        String action = Logic.botLogic(bot);
        if (action.equals("draw sandy")) {
            Card old = bot.getSandyCard();
            discardSandy.add(old);
            bot.setSandyCard(sandyDeck.pickCard());
            updateDiscardVisual(sandyDiscardImage, old);
        } else if (action.equals("draw bloody")) {
            Card old = bot.getBloodyCard();
            discardBloody.add(old);
            bot.setBloodyCard(bloodyDeck.pickCard());
            updateDiscardVisual(bloodyDiscardImage, old);
        }
    }
    
    private void updateLabels() {
        tourLabel.setText("Tour : " + tourDansManche);
        manchesLabel.setText("Manche : " + manche);
        tokensLabel.setText("Jetons : " + player1.getTokens());
    }

    private void nextPlayer() {
        if (gameEnded) return;
        currentPlayerIndex++;
    
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            tourDansManche++;
    
            if (tourDansManche > 3) {
                endManche();
            }
        }
    
        Player current = players.get(currentPlayerIndex);
        System.out.println("le" + current.getName() + "possède les cartes : " + current.getSandyCard().getValue() + " et " + current.getBloodyCard().getValue());
        if (current.getIsBot()) {
            botPlays(current);
            nextPlayer();
        }
        updateLabels();
    }
}
