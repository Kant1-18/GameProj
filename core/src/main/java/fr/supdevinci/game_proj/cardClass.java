package fr.supdevinci.game_proj;

public class cardClass {
    private Integer value;
    private String color;

    public cardClass(Integer value, String color) {
        this.value = value;
        this.color = color;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
