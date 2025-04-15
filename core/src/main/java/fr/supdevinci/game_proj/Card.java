package fr.supdevinci.game_proj;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Card {
    private Integer value;
    private String color;
    private TextureRegion textureRegion;

    private static boolean assetLoaded = false;

    public Integer getValue() { return value; }

    public String getColor() { return color; }

    public void setValue(Integer value)
    {
        if (value <= 0 || value >= 7) {
            throw new IllegalArgumentException("Value must be between 0 and 7");
        }
        this.value = value;
    }

    public void setColor(String color) {
        if (color != "sandy" || color != "bloody") {
            throw new IllegalArgumentException("Color must be either 'sandy' or 'bloody'");
        }
        this.color = color;
    }

    public Card(Integer value, String color) {
        this.setValue(value);
        this.setColor(color);

        if (!assetLoaded) {
            CardAssets.load();
            assetLoaded = true;
        }

        if (color.equals("sandy")) {
            this.textureRegion = CardAssets.sandyCards.get(value - 1);
        }
        if (color.equals("bloody")) {
            this.textureRegion = CardAssets.bloodyCards.get(value - 1);
        }
    }
}
