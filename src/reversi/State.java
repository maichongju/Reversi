package reversi;

enum StateType {New,Relay,Reply}
enum Colour {Black,White,NoColor}
enum Direction {N,NE,E,SE,S,SW,W,NW}

public class State {
    public boolean vaildTest = false;
    public final StateType type;
    public final Colour color;
    public Direction direction;

    public State (StateType type, Colour color){
        this.type = type;
        this.color = color;
    }

    public State(StateType type, Colour color, Direction direction){
        this.type = type;
        this.color = color;
        this.direction = direction;
    }

    public State(StateType type, Colour color, Direction direction, boolean vaildTest){
        this.type = type;
        this.color = color;
        this.direction = direction;
        this.vaildTest = vaildTest;
    }
}
