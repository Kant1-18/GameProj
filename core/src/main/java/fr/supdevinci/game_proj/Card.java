package fr.supdevinci.game_proj;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
    private Integer value;
    private String color;
    private TextureRegion textureRegion;

    public Card(Integer value, String color) {
        this.value = value;
        this.color = color;

        if (color.equals("sand")) {
            this.textureRegion = CardAssets.sandCards.get(value - 1);
        }
        if (color.equals("blood")) {
            this.textureRegion = CardAssets.bloodCards.get(value - 1);
        }
    }

    public Integer getValue() { return value; }

    public void setValue(Integer value) { this.value = value; }

    public String getColor() { return color; }

    public void setColor(String color) { this.color = color; }
}
