package events;

import objects.ActorObject;

/**
 * Created by T510 on 8/6/2017.
 */

public class ActorEvent extends GameEvent {

    public enum State{
        REMOVED,
    }
    public ActorEvent.State state = null;

    public ActorEvent(ActorEvent.State s){ state = s; }

}
