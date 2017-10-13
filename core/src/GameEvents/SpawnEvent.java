package GameEvents;

import GameObjects.ActorObject;
import GameObjects.SpawnObject;
import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class SpawnEvent extends GameEvent {

    public enum Action{
        SET_ENABLED
    }

    public Action action = null;
    public boolean enabled = false;

    public SpawnEvent(Action a, boolean e){
        action = a;
        enabled = e;
    }

}
