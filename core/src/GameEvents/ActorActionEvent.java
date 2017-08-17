package GameEvents;

import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class ActorActionEvent extends GameEvent {

    public enum State{
        SHOOT,      //actor shoots
    }
    public ActorActionEvent.State state = null;

    public ActorActionEvent(ActorActionEvent.State s){ state = s; }

}
