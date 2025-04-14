package fr.supdevinci.game_proj;

public class Player {
    private String name;
    private Integer tokens;
    private Integer stake;
    
    public Player(String name, Integer tokens, Integer stake){
        this.name = name;
        this.tokens = tokens;
        this.stake = stake;
    }

    public String getName(){
        return this.name;
    }

    public Integer getTokens(){
        return this.tokens;
    }

    public Integer getStake(){
        return this.stake;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setTokens(Integer tokens){
        this.tokens = tokens;
    }

    public void setStake(Integer stake){
        this.stake = stake;
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

    public void loose(){
        this.stake = 0;
        this.tokens -= 1;
    }

    public void win(){
        this.tokens += this.stake;
        this.stake = 0;
    }
}