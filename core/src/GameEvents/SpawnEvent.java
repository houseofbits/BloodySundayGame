package GameEvents;

import GameObjects.SpawnObject;
import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class SpawnEvent extends GameEvent {
    public SpawnObject.State state = null;
    public SpawnEvent(SpawnObject.State s){ state = s; }
}
