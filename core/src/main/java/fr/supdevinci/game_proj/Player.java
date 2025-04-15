package fr.supdevinci.game_proj;

public class Player {
    private Boolean isBot;
    private String name;
    private Integer tokens;
    private Integer stake;
    private Card sandyCard;
    private Card bloodyCard;

    public Player(Boolean isBot, String name, Integer tokens, Integer stake, Card sandyCard, Card bloodyCard){
        this.isBot = isBot;
        this.name = name;
        this.tokens = tokens;
        this.stake = stake;
        this.sandyCard = sandyCard;
        this.bloodyCard = bloodyCard;
    }

    public Boolean getIsBot(){ return this.isBot; }

    public String getName(){ return this.name; }

    public Integer getTokens(){ return this.tokens; }

    public Integer getStake(){ return this.stake; }

    public Card getSandyCard(){ return this.sandyCard; }

    public Card getBloodyCard(){ return this.bloodyCard; }

    public void setSandyCard(Card sandyCard) {
        if (sandyCard.getColor() != "sandy") {
            throw new IllegalArgumentException("Card must be of color 'sandy'");
        }
        this.sandyCard = sandyCard;
    }

    public void setBloodyCard(Card bloodyCard) {
        if (bloodyCard.getColor() != "bloody") {
            throw new IllegalArgumentException("Card must be of color 'bloody'");
        }
        this.bloodyCard = bloodyCard;
    }

    public Boolean bet(){
        if (tokens > 0){
            this.tokens -= 1;
            this.stake += 1;
            return true;
        }
        else{
            return false;
        }
    }

    public void lose(){
        this.stake = 0;
        this.tokens -= 1;
    }

    public void win(){
        this.tokens += this.stake;
        this.stake = 0;
    }

    public Integer handValue(){
        Integer sandyCardValue = this.sandyCard.getValue();
        Integer bloodyCardValue = this.bloodyCard.getValue();

        if (sandyCardValue == 7){ sandyCardValue = Logic.roleDice(); }
        if (bloodyCardValue == 7){ bloodyCardValue = Logic.roleDice(); }

        if (sandyCardValue != 0 && bloodyCardValue != 0){
            if (sandyCardValue > bloodyCardValue){
                return sandyCardValue - bloodyCardValue;
            }
            if (sandyCardValue < bloodyCardValue){
                return bloodyCardValue - sandyCardValue;
            }
        }

        return 0;
    }
}
