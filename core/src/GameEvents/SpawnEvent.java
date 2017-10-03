package GameEvents;

import GameObjects.ActorObject;
import GameObjects.SpawnObject;
import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class SpawnEvent extends GameEvent {

    public enum Action{
        ADD_ACTOR,
        REMOVE_ACTOR,
        SET_ACTOR_WEIGHT,
        SET_ENABLED
    }

    public SpawnObject.State state = null;
    public ActorObject.ActorType actorType = null;
    public float actorWeight = 0;
    public Action action = null;
    public boolean enabled = false;

    public SpawnEvent(SpawnObject.State s){ state = s; }

    public SpawnEvent(Action a, boolean e){
        action = a;
        enabled = e;
    }

    public SpawnEvent(Action a, ActorObject.ActorType t, float w){
        action = a;
        actorType = t;
        actorWeight = w;
    }
    public SpawnEvent(Action a, ActorObject.ActorType t){
        action = a;
        actorType = t;
    }
}
