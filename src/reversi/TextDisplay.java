package reversi;

import java.util.ArrayList;

public class TextDisplay implements Observer {
    private final ArrayList<ArrayList<Character>> grid = new ArrayList<>();

    public TextDisplay(int size) {

        for (int r = 0; r < size; r++) {
            ArrayList<Character> temp = new ArrayList<Character>();
            for (int c = 0; c < size; c++) {
                temp.add('-');
            }
            grid.add(temp);
        }

        // Set up the first 4 pieces
        grid.get(size / 2 - 1).set(size/2-1, 'B');
        grid.get(size / 2 ).set(size/2, 'B');
        grid.get(size / 2 - 1).set(size/2, 'W');
        grid.get(size / 2).set(size/2-1, 'W');
    }

    @Override
    public boolean isvalid(Subject whoFrom) {
        return false;
    }

    @Override
    public void Notify(Subject whoFrom) {
        Info info = whoFrom.getInfo();
        grid.get(info.row).set(info.col, info.color == Colour.Black ? 'B' : 'W');
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int r = 0; r < grid.size(); r++){
            for (int c = 0; c < grid.size(); c++){
                result.append(grid.get(r).get(c));
            }
            result.append("\n");
        }
        return result.toString();
    }
}
