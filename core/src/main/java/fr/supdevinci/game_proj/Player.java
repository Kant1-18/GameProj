package fr.supdevinci.game_proj;

public class Player {
    private Boolean isBot;
    private String name;
    private Integer tokens;
    private Integer stake;
    private Card sandCard;
    private Card bloodCard;

    public Player(Boolean isBot, String name, Integer tokens, Integer stake, Card sandCard, Card bloodCard){
        this.isBot = isBot;
        this.name = name;
        this.tokens = tokens;
        this.stake = stake;
        this.sandCard = sandCard;
        this.bloodCard = bloodCard;
    }

    public Boolean getIsBot(){ return this.isBot; }

    public String getName(){ return this.name; }

    public Integer getTokens(){ return this.tokens; }

    public Integer getStake(){ return this.stake; }

    public Card getSandCard(){ return this.sandCard; }

    public Card getBloodCard(){ return this.bloodCard; }

    public void setSandCard(Card sandCard){ this.sandCard = sandCard; }

    public void setBloodCard(Card bloodCard){ this.bloodCard = bloodCard; }

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
}
