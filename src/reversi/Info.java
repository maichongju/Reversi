package reversi;

public class Info {
    public final int row;
    public final int col;
    public final Colour color;

    public Info(int row, int col, Colour color) {
        this.row = row;
        this.col = col;
        this.color = color;
    }
}