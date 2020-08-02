package reversi;

import java.awt.*;
import java.util.ArrayList;

public class Grid {
    private ArrayList<ArrayList<Cell>> grid = null;
    private TextDisplay td;
    private int gridSize;
    private Colour currentColor = Colour.Black;
    public int Black, White, NoColor = 0;
    private GraphicDisplay gd = null;

    public Grid() {
    }

    public void setGraphicDisplay(GraphicDisplay gd) {
        this.gd = gd;
    }

    public void init(int size) {
        // Set first so that GridInfo will be correct
        Black = White = 2;
        NoColor = size * size - 2;
        // Reset current piece color
        currentColor = Colour.Black;
        gd.updateCurrentPiece(Color.BLACK);
        gd.updateCount(getGridInfo());

        gridSize = size;

        // Set up new TextDisplay
        td = new TextDisplay(size);

        // Set up new Grid
        grid = new ArrayList<ArrayList<Cell>>();

        // Set up all the cell
        for (int r = 0; r < size; r++) {
            ArrayList<Cell> temp = new ArrayList<Cell>();
            for (int c = 0; c < size; c++) {
                temp.add(new Cell(r, c, this));
            }
            grid.add(temp);
        }

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                for (int i = 0; i < 8; i++) {
                    try {
                        int cr = 0, cc = 0;
                        switch (i) {
                            case 0: {
                                cr = r - 1;
                                cc = c - 1;
                                break;
                            }
                            case 1: {
                                cr = r - 1;
                                cc = c;
                                break;
                            }
                            case 2: {
                                cr = r - 1;
                                cc = c + 1;
                                break;
                            }
                            case 3: {
                                cr = r;
                                cc = c - 1;
                                break;
                            }
                            case 4: {
                                cr = r;
                                cc = c + 1;
                                break;
                            }
                            case 5: {
                                cr = r + 1;
                                cc = c - 1;
                                break;
                            }
                            case 6: {
                                cr = r + 1;
                                cc = c;
                                break;
                            }
                            case 7: {
                                cr = r + 1;
                                cc = c + 1;
                                break;
                            }

                        }
                        grid.get(r).get(c).attachObserver(grid.get(cr).get(cc));
                    } catch (Exception e) {
                    }
                }

                grid.get(r).get(c).attachObserver(td);
                grid.get(r).get(c).attachObserver(gd);
            }
        }


        grid.get(size / 2 - 1).get(size / 2 - 1).specialPlace(Colour.Black);
        grid.get(size / 2).get(size / 2).specialPlace(Colour.Black);
        grid.get(size / 2 - 1).get(size / 2).specialPlace(Colour.White);
        grid.get(size / 2).get(size / 2 - 1).specialPlace(Colour.White);


        gd.init(); //refresh the graphic display, put it at the end just want to make sure the grid is set up
    }


    /**
     * Place the piece into the grid, Must call this function to place piece
     *
     * @param row
     * @param col
     * @throws ReversiException
     */
    public void place(int row, int col) throws ReversiException {
        // Check if there is already a piece
        if (grid.get(row).get(col).getInfo().color != Colour.NoColor)
            throw new ReversiException(ReversiException.Flags.PIECE_ALREADY_EXIST);

        grid.get(row).get(col).place(currentColor);
        currentColor = currentColor == Colour.Black ? Colour.White : Colour.Black;
        gd.updateCurrentPiece(currentColor == Colour.Black ? Color.BLACK : Color.WHITE);
        gd.updateCount(getGridInfo());
    }

    /**
     * function will tell who win the game, only call this function when the game is over.
     * When the grid is full
     *
     * @return Colour class, Black, White, NoColor
     */
    public Colour whoWon() {
        Colour result;
        if (Black > White) {
            result = Colour.Black;
        } else if (White > Black) {
            result = Colour.White;
        } else {
            result = Colour.NoColor;
        }
        return result;
    }

    public GridInfo getGridInfo() {
        return new GridInfo(Black, White, NoColor);
    }

    public boolean isGameOver() {
        for (ArrayList<Cell> row : grid) {
            for (Cell cell : row) {
                if (cell.getInfo().color == Colour.NoColor) {
                    cell.setState(new State(StateType.New, currentColor));
                    if (cell.isValid()) {
                        cell.setState(null);
                        return false;
                    }
                    cell.setState(null);
                }
            }
        }

        return true;
    }

    public int getGridSize() {
        return gridSize;
    }

    @Override
    public String toString() {
        return td.toString();
    }

}
