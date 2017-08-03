package events;

import objects.DoorObject;

/**
 * Created by T510 on 8/3/2017.
 */

public class DoorEvent implements GameEvent {

    public DoorObject.DoorState setState = DoorObject.DoorState.CLOSED;

    public DoorEvent(DoorObject.DoorState doorState){
        setState = doorState;
    }
}
