package reversi;

public class Cell extends Subject implements Observer {
    private int row;
    private final int col;
    private Grid theGrid;
    private Colour color = Colour.NoColor;

    public Cell(int row, int col, Grid grid) {
        this.row = row;
        this.col = col;
        theGrid = grid;
    }

    public void place(Colour color) throws ReversiException {
        this.setState(new State(StateType.New, color));
        if (!isValid()) {
            throw new ReversiException(ReversiException.Flags.PIECE_NOT_VAILD);
        }
        this.color = color; //Cell is valid
        NotifyObservers();
        theGrid.NoColor--;
        if (color == Colour.Black) {
            theGrid.Black++;
        } else {
            theGrid.White++;
        }
    }

    // Will not notify the it's observers, only use for init
    public void specialPlace(Colour color) {
        this.color = color;
    }

    @Override
    public boolean isvalid(Subject whoFrom) {
        if (color != Colour.NoColor) {
            if (whoFrom.getState().type == StateType.New) {
                if (color != whoFrom.getState().color) {
                    setState(new State(StateType.Relay, whoFrom.getState().color
                            , getOptDirection(getDirection(whoFrom.getInfo()))));
                    return isValid();
                }
            } else if (whoFrom.getState().type == StateType.Relay) {
                if (getOptDirection(getDirection(whoFrom.getInfo())) == whoFrom.getState().direction) {
                    State s;
                    if (color == whoFrom.getState().color) {
                        return true;
                    } else {
                        s = whoFrom.getState();
                    }
                    setState(s);
                    return isValid();
                }
            }
        }

        return false;
    }

    public boolean isValid() {
        for (Observer ob : observer) {
            if (ob.isvalid(this)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Operation for telling
     * @param whoFrom
     */
    @Override
    public void Notify(Subject whoFrom) {
        if (color != Colour.NoColor) {
            if (whoFrom.getState().type == StateType.New) {
                if (whoFrom.getState().color != color) {
                    setState(new State(StateType.Relay, whoFrom.getInfo().color
                            , getOptDirection(getDirection(whoFrom.getInfo()))));
                    NotifyObservers();
                }
            } else if (whoFrom.getState().type == StateType.Relay) {
                if (getOptDirection(getDirection(whoFrom.getInfo())) == whoFrom.getState().direction) {
                    State s;
                    if (color == whoFrom.getState().color) {
                        s = new State(StateType.Reply, whoFrom.getState().color, getOptDirection(whoFrom.getState().direction));
                    } else {
                        s = whoFrom.getState();
                    }
                    setState(s);
                    NotifyObservers();
                }
            } else if (whoFrom.getState().type == StateType.Reply) {
                if (color != whoFrom.getState().color) {
                    Direction d = getOptDirection(getDirection(whoFrom.getInfo()));
                    if (d == whoFrom.getState().direction) {
                        toggle();
                        setState(whoFrom.getState());
                        NotifyObservers();
                    }
                }
            }

        }

    }

    /**
     * Retrun the information about the cell in a Info Class
     * @return
     */
    @Override
    public Info getInfo() {
        return new Info(row, col, color);
    }

    /**
     * toggle the color of the cell, also change the status of the
     * White and Black count in the Grid
     */
    public void toggle() {
        if (color == Colour.Black) {
            theGrid.Black--;
            theGrid.White++;
        } else {
            theGrid.Black++;
            theGrid.White--;
        }
        color = color == Colour.Black ? Colour.White : Colour.Black;
    }

    private Direction getDirection(Info whoFrom) {
        Direction d;
        if (row - whoFrom.row == 1 && col == whoFrom.col) {
            d = Direction.N;
        } else if (row - whoFrom.row == -1 && col == whoFrom.col) {
            d = Direction.S;
        } else if (row == whoFrom.row && col - whoFrom.col == 1) {
            d = Direction.W;
        } else if (row == whoFrom.row && col - whoFrom.col == -1) {
            d = Direction.E;
        } else if (row - whoFrom.row == 1 && col - whoFrom.col == 1) {
            d = Direction.NW;
        } else if (row - whoFrom.row == 1 && col - whoFrom.col == -1) {
            d = Direction.NE;
        } else if (row - whoFrom.row == -1 && col - whoFrom.col == 1) {
            d = Direction.SW;
        } else {
            d = Direction.SE;
        }
        return d;
    }

    /**
     * given a direction and will return the opt direction
     *
     * @param dir direction need to be convert
     * @return opt direction of the given direction
     */
    private Direction getOptDirection(Direction dir) {
        Direction d = null;
        switch (dir) {
            case N: {
                d = Direction.S;
                break;
            }
            case S: {
                d = Direction.N;
                break;
            }
            case E: {
                d = Direction.W;
                break;
            }
            case W: {
                d = Direction.E;
                break;
            }
            case SE: {
                d = Direction.NW;
                break;
            }
            case NW: {
                d = Direction.SE;
                break;
            }
            case SW: {
                d = Direction.NE;
                break;
            }
            case NE: {
                d = Direction.SW;
                break;
            }

        }
        return d;
    }
}
