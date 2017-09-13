package GameObjects;

import com.badlogic.gdx.math.Vector3;

import GameEvents.ActorEvent;

/**
 * Created by T510 on 9/12/2017.
 */

public class ActorEnemyObject extends ActorObject {

//sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.CLOSED), doorName);

    public class ActorStateAction extends ActorState{
        public ActorStateAction(String nextStateName, String animationTrackName, float animationSpeed, float stateTime){
            super("ACTION", nextStateName, animationTrackName, animationSpeed, stateTime);
        }
        public void onStateFinish(){
            parent.sendEvent(new ActorEvent(ActorEvent.State.SHOOT));
        }
    }


    public ActorEnemyObject(SpawnObject spawnObject) {

        super(spawnObject, "test_actor_anim.g3dj", "test_actor.g3dj");

        addActorState(new ActorStateAppear("IDLE", "appear1", 1.0f, 1.0f));

        addActorState(new ActorState("IDLE", "ACTION", null, 1.0f, 1.0f));

        addActorState(new ActorStateAction("DISSAPEAR", null, 1.0f, 1.0f));

        addActorState(new ActorState("HIT", "DISSAPEAR", null, 1.0f, 1.0f));

        addActorState(new ActorState("DIE", "DISSAPEAR", null, 1.0f, 1.0f));

        addActorState(new ActorStateDisappear());

        switchState("APPEAR");
    }
    public void onActorEvent(ActorEvent e){
        switch(e.state){
            case SET_DISAPPEAR:
                switchState("DISSAPEAR");
                break;
        }
    }
}
