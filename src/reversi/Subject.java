package reversi;

import java.util.ArrayList;

public abstract class Subject {
    protected final ArrayList<Observer> observer = new ArrayList<Observer>();
    private State state;

    public abstract Info getInfo();
    public abstract boolean isValid();

    // get State, only allow for its sub class
    protected State getState(){
        return state;
    }

    public void setState(State state){
        this.state = state;
    }

    public void attachObserver(Observer ob){
        observer.add(ob);
    }


    public void NotifyObservers(){
        for (Observer ob : observer){
            ob.Notify(this);
        }
    }


}
