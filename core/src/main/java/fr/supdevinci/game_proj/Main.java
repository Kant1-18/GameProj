package fr.supdevinci.game_proj;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;

import java.util.*;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Deck<SabaccCard> sandyDeck, bloodyDeck;
    private ArrayList<SabaccCard> playerHand = new ArrayList<>();
    private ArrayList<Image> playerHandImages = new ArrayList<>();
    private ArrayList<SabaccCard> discardSandy = new ArrayList<>(), discardBloody = new ArrayList<>();
    private Player player1 = new Player(false, "Joueur", 8, 0, null, null);
    private Player bot1 = new Player(true, "Bot 1", 8, 0, null, null);
    private ArrayList<Player> players = new ArrayList<>();
    private Round round;
    private int manche = 1, tourDansManche = 1, currentPlayerIndex = 0;
    private boolean mustDiscard = false, gameEnded = false;
    private String doubleColor = null;
    private HashMap<String, Integer> victoires = new HashMap<>();
    private Label tokensLabel, manchesLabel, tourLabel;
    private Image sandyDiscardImage, bloodyDiscardImage;
    private final int cardWidth = 120, cardHeight = 180, screenWidth = 1920, screenHeight = 1080;

    @Override
    public void create() {
        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        BitmapFont font = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = font; buttonStyle.fontColor = Color.BLACK;

        players.add(player1); players.add(bot1);
        for (Player p : players) victoires.put(p.getName(), 0);
        round = new Round(players); round.initRound();

        sandyDeck = Logic.deckInit("sandy");
        bloodyDeck = Logic.deckInit("bloody");

        addCardToHand(sandyDeck.pickCard()); addCardToHand(bloodyDeck.pickCard());

        addCardBack(CardAssets.backSandy, (screenWidth - 2 * (cardWidth + 20)) / 2);
        addCardBack(CardAssets.backBloody, (screenWidth - 2 * (cardWidth + 20)) / 2 + cardWidth + 20);

        sandyDiscardImage = addDiscard(new SabaccCard(2, "sandy"), 500, discardSandy);
        bloodyDiscardImage = addDiscard(new SabaccCard(7, "bloody"), screenWidth - cardWidth - 500, discardBloody);

        addDeck(CardAssets.backBloody, screenWidth - 2 * cardWidth - 650, bloodyDeck);
        addDeck(CardAssets.backSandy, 50 + cardWidth + 600, sandyDeck);

        Table scoreTable = new Table();
        scoreTable.bottom().left().pad(20).padBottom(100); scoreTable.setFillParent(true);

        manchesLabel = new Label("Manche : " + manche, labelStyle);
        tourLabel = new Label("Tour : " + tourDansManche, labelStyle);
        tokensLabel = new Label("Jetons : " + player1.getTokens(), labelStyle);
        Arrays.asList(tokensLabel, tourLabel, manchesLabel).forEach(label -> label.setFontScale(2.0f));
        scoreTable.add(tokensLabel).left().row();
        scoreTable.add(tourLabel).left().row();
        scoreTable.add(manchesLabel).left();
        stage.addActor(scoreTable);

        Table buttonTable = new Table();
        buttonTable.bottom().right().pad(40).padBottom(150); buttonTable.setFillParent(true);
        TextButton passButton = new TextButton("Passer mon tour", buttonStyle);
        passButton.getLabel().setFontScale(2.0f);
        passButton.addListener(new ClickListener() { public void clicked(InputEvent e, float x, float y) { nextPlayer(); }});
        buttonTable.add(passButton).size(300, 100);
        stage.addActor(buttonTable);
    }

    private void addCardBack(TextureRegion region, int x) {
        Image back = new Image(new TextureRegionDrawable(region));
        back.setSize(cardWidth, cardHeight); back.setPosition(x, screenHeight - cardHeight - 50);
        stage.addActor(back);
    }

    private Image addDiscard(SabaccCard card, int x, ArrayList<SabaccCard> pile) {
        Image img = new Image(new TextureRegionDrawable(card.getTextureRegion()));
        img.setSize(cardWidth, cardHeight); img.setPosition(x, screenHeight / 2 - cardHeight / 2);
        img.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (playerHand.size() < 3 && !pile.isEmpty() && !mustDiscard && player1.bet()) {
                    addCardToHand(pile.remove(pile.size() - 1));
                    updateTokenDisplay();
                    if (!pile.isEmpty()) updateDiscardVisual(img, pile.get(pile.size() - 1));
                }
            }
        });
        stage.addActor(img);
        return img;
    }

    private void addDeck(TextureRegion region, int x, Deck<SabaccCard> deck) {
        Image img = new Image(new TextureRegionDrawable(region));
        img.setSize(cardWidth, cardHeight); img.setPosition(x, screenHeight / 2 - cardHeight / 2);
        img.addListener(new ClickListener() {
            public void clicked(InputEvent e, float x, float y) {
                if (playerHand.size() < 3 && !mustDiscard && player1.bet()) {
                    addCardToHand(deck.pickCard()); updateTokenDisplay();
                }
            }
        });
        stage.addActor(img);
    }

    private void addCardToHand(SabaccCard card) {
        playerHand.add(card);
        int sandyCount = 0, bloodyCount = 0;
        for (SabaccCard c : playerHand) {
            if (c.getColor().equals("sandy")) sandyCount++;
            if (c.getColor().equals("bloody")) bloodyCount++;
        }
        mustDiscard = sandyCount > 1 || bloodyCount > 1;
        doubleColor = sandyCount > 1 ? "sandy" : bloodyCount > 1 ? "bloody" : null;
        renderPlayerHand();
    }

    private void renderPlayerHand() {
        playerHandImages.forEach(img -> stage.getActors().removeValue(img, true));
        playerHandImages.clear();
        for (int i = 0; i < playerHand.size(); i++) {
            SabaccCard card = playerHand.get(i);
            Image img = new Image(new TextureRegionDrawable(card.getTextureRegion()));
            img.setSize(cardWidth, cardHeight);
            img.setPosition((screenWidth - (playerHand.size() * (cardWidth + 20) - 20)) / 2 + i * (cardWidth + 20), 50);
            final int index = i;
            img.addListener(new ClickListener() {
                public void clicked(InputEvent e, float x, float y) {
                    if (mustDiscard && playerHand.get(index).getColor().equals(doubleColor)) discardCard(index);
                }
            });
            stage.addActor(img); playerHandImages.add(img);
        }
    }

    private void discardCard(int index) {
        SabaccCard card = playerHand.remove(index);
        if (card.getColor().equals("sandy")) discardSandy.add(card); else discardBloody.add(card);
        updateDiscardVisual(card.getColor().equals("sandy") ? sandyDiscardImage : bloodyDiscardImage, card);
        mustDiscard = false; doubleColor = null; renderPlayerHand(); nextPlayer();
    }

    private void updateDiscardVisual(Image img, SabaccCard card) {
        img.setDrawable(new TextureRegionDrawable(card.getTextureRegion()));
    }

    private void updateTokenDisplay() {
        tokensLabel.setText("Jetons : " + player1.getTokens());
    }

    private void updateLabels() {
        tourLabel.setText("Tour : " + tourDansManche);
        manchesLabel.setText("Manche : " + manche);
        updateTokenDisplay();
    }

    private void displayEndGame(String winner) {
        Label fin = new Label("FIN DE PARTIE\nVainqueur : " + winner, new Label.LabelStyle(new BitmapFont(), Color.GOLD));
        fin.setFontScale(3f); fin.setPosition((screenWidth - 400) / 2f, screenHeight / 2f);
        stage.addActor(fin);
    }

    private void endManche() {
        Player winner = new Logic().getWinner(players);
        victoires.put(winner.getName(), victoires.get(winner.getName()) + 1);
        round = Logic.rotation(round, winner); players = round.getPlayers();
        if (players.size() == 1) { displayEndGame(players.get(0).getName()); gameEnded = true; return; }
        manche++;
        if (manche > 3) {
            int min = Collections.min(victoires.values());
            players.removeIf(p -> victoires.getOrDefault(p.getName(), 0) == min);
            Player finalWinner = players.size() == 1 ? players.get(0) : new Logic().getWinner(players);
            displayEndGame(finalWinner.getName()); gameEnded = true; return;
        }
        tourDansManche = 1; currentPlayerIndex = 0; updateLabels(); round.initRound();
    }

    private void nextPlayer() {
        if (gameEnded) return;
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0; tourDansManche++;
            if (tourDansManche > 3) { endManche(); return; }
        }
        Player current = players.get(currentPlayerIndex);
        if (current.getIsBot()) { botPlays(current); nextPlayer(); }
        updateLabels();
    }

    private void botPlays(Player bot) {
        String action = Logic.botLogic(bot);
        if (action.equals("draw sandy")) {
            discardSandy.add(bot.getSandyCard()); bot.setSandyCard(sandyDeck.pickCard());
            updateDiscardVisual(sandyDiscardImage, bot.getSandyCard());
        } else if (action.equals("draw bloody")) {
            discardBloody.add(bot.getBloodyCard()); bot.setBloodyCard(bloodyDeck.pickCard());
            updateDiscardVisual(bloodyDiscardImage, bot.getBloodyCard());
        }
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
        batch.dispose(); stage.dispose(); CardAssets.dispose();
    }
}
