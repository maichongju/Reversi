package reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GridGraphic extends JComponent {

    /*
    --------->x
    |
    |
    |
    v
    y
    */
    private final int WIDTH = 800, HEIGHT = 800;
    private int cellWidth;
    private final Grid grid;
    private Color background = new Color(142, 255, 107);
    private ArrayList<ArrayList<Piece>> PieceArray;
    private ArrayList<Line> LineArray;

    public GridGraphic(Grid grid) {
        setSize(WIDTH, HEIGHT);
        this.grid = grid;
        setBackground(Color.GREEN);
        addMouseListener(new mouseListener());
    }

    public void init() {
        int size = grid.getGridSize();
        System.out.println(size);
        cellWidth = WIDTH / size;

        PieceArray = new ArrayList<ArrayList<Piece>>();
        LineArray = new ArrayList<Line>();

        // Add all the piece to piece array
        for (int r = 0; r < size; r++) {
            ArrayList<Piece> temp = new ArrayList<Piece>();
            for (int c = 0; c < size; c++) {
                temp.add(new Piece(cellWidth,r,c));
            }
            PieceArray.add(temp);
        }

        for (int i = 0; i <= size; i++) {
            LineArray.add(new Line(0, cellWidth * i, WIDTH, cellWidth * i));
            LineArray.add(new Line(cellWidth * i, 0, cellWidth * i, WIDTH));
        }

        PieceArray.get(size / 2 - 1).get(size / 2 - 1).color = Colour.Black;
        PieceArray.get(size / 2).get(size / 2).color = Colour.Black;
        PieceArray.get(size / 2 - 1).get(size / 2).color = Colour.White;
        PieceArray.get(size / 2).get(size / 2 - 1).color = Colour.White;

        repaint();// repaint
    }

    public void notify(final Subject whoFrom) {

        Info info = whoFrom.getInfo();
        PieceArray.get(info.row).get(info.col).color = info.color;
        repaint();
    }

    // TODO Add change color for grid
    /**
     * Change the background color to given color
     * @param color Color java.awt
     */
    public void changeBackgroundColor(Color color){
        background = color;
        repaint();
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(background);
        // Draw background
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.BLACK);
        // Draw all the piece
        for (ArrayList<Piece> row : PieceArray) {
            for (Piece piece : row) {
                piece.draw(g);
            }
        }

        // Draw all the line
        g.setColor(Color.BLACK);
        for (Line line : LineArray) {
            line.draw(g);
        }

    }
    class mouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            int col = 0,row = 0;
            //Find col
            for (int i=0;i<=grid.getGridSize();i++){
                if (i*cellWidth > e.getX()){
                    col = i-1;
                    break;
                }
            }

            // find row
            for (int i=0;i<=grid.getGridSize();i++){
                if (i*cellWidth > e.getY()){
                    row = i-1;
                    break;
                }
            }

            try{
                grid.place(row,col);
                if (grid.isGameOver()){
                    Colour result = grid.whoWon();
                    String r = "";
                    switch (result) {
                        case Black: {
                            r = "Black Win!";
                            break;
                        }
                        case NoColor: {
                            r = "Tie!";
                            break;
                        }
                        case White: {
                            r = "White Win!";
                        }
                    }
                    JOptionPane.showMessageDialog(null,r,"Result",JOptionPane.INFORMATION_MESSAGE,null);
                    grid.init(grid.getGridSize());
                }
            }catch(ReversiException e0){}
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}



class Piece {
    public Colour color = Colour.NoColor;
    private final int row;
    private final int col;
    private final int cellWidth;

    public Piece(int cellWidth, int row, int col) {
        this.cellWidth = cellWidth;
        this.row = row;
        this.col = col;
    }

    public void draw(Graphics g) {
        if (color != Colour.NoColor) {
            g.setColor(color == Colour.Black ? Color.BLACK : Color.WHITE);
            double x, y, piecewidth;
            x = col * cellWidth + 0.1 * cellWidth;
            y = row * cellWidth + 0.1 * cellWidth;
            piecewidth = cellWidth - 0.2 * cellWidth;
            g.fillOval((int) x, (int) y, (int) piecewidth, (int) piecewidth);
            g.setColor(Color.BLACK);
            g.drawOval((int) x, (int) y, (int) piecewidth, (int) piecewidth);
        }
    }
}

class Line {
    private final int x0;
    private final int x1;
    private final int y0;
    private final int y1;

    public Line(int x0, int y0, int x1, int y1) {
        this.x0 = x0;
        this.y0 = y0;
        this.x1 = x1;
        this.y1 = y1;
    }

    /**
     * Must call repaint after calling this function, otherwise the graphic will not update
     *
     * @param g
     */
    public void draw(Graphics g) {
        g.drawLine(x0, y0, x1, y1);
    }
}

