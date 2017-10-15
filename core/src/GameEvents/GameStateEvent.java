package GameEvents;

import GameObjects.DoorObject;
import events.GameEvent;

/**
 * Created by T510 on 8/3/2017.
 */

public class GameStateEvent extends GameEvent {

    public enum State{
        GAME_PAUSED,
        GAME_RESUME,
    }

    public State state = null;
    public boolean isPaused = false;

    public GameStateEvent(State s){
        state = s;
        if(state == State.GAME_PAUSED)isPaused = true;
        if(state == State.GAME_RESUME)isPaused = false;
    }
}
