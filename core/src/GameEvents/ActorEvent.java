package GameEvents;

import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class ActorEvent extends GameEvent {

    public enum State{
        REMOVED,
        SHOOT,
        DIE
    }
    public ActorEvent.State state = null;

    public ActorEvent(ActorEvent.State s){ state = s; }

}
