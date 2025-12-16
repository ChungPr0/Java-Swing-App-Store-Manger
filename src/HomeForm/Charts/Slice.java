package HomeForm.Charts;

import java.awt.*;

public class Slice {
    public String name;
    public double value;
    public Color color;
    public Shape shape;

    public Slice(String name, double value, Color color) {
        this.name = name;
        this.value = value;
        this.color = color;
    }
}