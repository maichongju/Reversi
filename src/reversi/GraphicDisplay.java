package reversi;

import javax.swing.*;
import java.awt.*;

public class GraphicDisplay extends JPanel implements Observer {
    // Define width and height
    private final int WIDTH = 1000, HEIGHT = 830;
    private Grid grid;
    private GridGraphic gg;
    private ComponentGraphic cg;

    public GraphicDisplay(Grid grid){
        gg = new GridGraphic(grid);
        cg = new ComponentGraphic(grid);
        this.grid = grid;
        setSize(WIDTH,HEIGHT);
        setLayout(new BorderLayout());
        add(gg,BorderLayout.CENTER);
        add(cg ,BorderLayout.LINE_END);
    }

    public void init(){
        gg.init();
    }

    public void updateCount(final GridInfo info){
        cg.updateCount(info);
    }

    /**
     * function will pass the color to the CurrentPieceGraphic to update the current color piece
     * @param color java.awt
     */
    public void updateCurrentPiece(Color color){
        cg.updateCurrentPiece(color);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH,HEIGHT);
    }

    /**
     * This function will notify the GridGraphic to change to cell of the
     * color
     * @param whoFrom
     */
    @Override
    public void Notify(final Subject whoFrom) { // This is for using console input
        gg.notify(whoFrom);
    }

    /**
     * since this class is not a cell class, so it will always not valid for placing a piece here.
     * @param whoFrom the Subject that call this
     * @return false
     */

    @Override
    public boolean isvalid(Subject whoFrom) {
        return false;
    }
}
