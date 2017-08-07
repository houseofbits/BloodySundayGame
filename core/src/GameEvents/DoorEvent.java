package GameEvents;

import GameObjects.DoorObject;
import events.GameEvent;

/**
 * Created by T510 on 8/3/2017.
 */

public class DoorEvent extends GameEvent {

    public enum Action{
        SET_STATE,
        STATE_CHANGED,
    }

    public DoorObject.State state = DoorObject.State.CLOSED;
    public Action action = Action.SET_STATE;

    public DoorEvent(DoorObject.State doorState){
        state = doorState;
    }
    public DoorEvent(Action a, DoorObject.State doorState){
        action = a;
        state = doorState;
    }
}
