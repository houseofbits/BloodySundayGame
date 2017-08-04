package events;

import objects.DoorObject;

/**
 * Created by T510 on 8/3/2017.
 */

public class DoorEvent implements GameEvent {

    public enum DoorAction{
        SET_STATE,
        STATE_CHANGED,
    }

    public DoorObject.DoorState state = DoorObject.DoorState.CLOSED;
    public DoorAction action = DoorAction.SET_STATE;

    public DoorEvent(DoorObject.DoorState doorState){
        state = doorState;
    }
    public DoorEvent(DoorAction a, DoorObject.DoorState doorState){
        action = a;
        state = doorState;
    }
}
