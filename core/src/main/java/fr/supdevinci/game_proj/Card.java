package fr.supdevinci.game_proj;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
    private Integer value;
    private String color;
    private TextureRegion textureRegion;

    private static boolean assetLoaded = false;

    public Card(Integer value, String color) {
        if (!assetLoaded) {
            CardAssets.load();
            assetLoaded = true;
        }
    
        this.value = value;
        this.color = color;
    
        if (value == null) {
            throw new IllegalArgumentException("Card value cannot be null");
        }
    
        if (color.equals("sandy")) {
            this.textureRegion = CardAssets.sandyCards.get(value - 1);
        } else if (color.equals("bloody")) {
            this.textureRegion = CardAssets.bloodyCards.get(value - 1);
        } else {
            throw new IllegalArgumentException("Invalid card color: " + color);
        }
    }

    public Integer getValue() { return value; }

    public void setValue(Integer value)
    {
        if (value <= 0 || value >= 7) {
            throw new IllegalArgumentException("Value must be between 0 and 7");
        }
        this.value = value;
    }

    public String getColor() { return color; }

    public void setColor(String color) {
        if (color != "sandy" || color != "bloody") {
            throw new IllegalArgumentException("Color must be either 'sandy' or 'bloody'");
        }
        this.color = color;
    }
    public TextureRegion getTextureRegion() { return textureRegion; }
}
