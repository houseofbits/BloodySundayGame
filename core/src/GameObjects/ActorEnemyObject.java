package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;

import GameEvents.ActorEvent;
import Utils.Error;

/**
 * Created by T510 on 9/12/2017.
 */

public class ActorEnemyObject extends ActorObject {

    public class ActorStateAction extends ActorState{
        public ActorStateAction(String stateName, String nextStateName, float stateDuration, float animationSpeed, String anim){
            super(stateName, nextStateName, stateDuration, animationSpeed, anim);
        }
        public void onStateStart(){}
        public void onStateUpdate(float t){
            if(t > 0.35f)animatedRederable.setMaterialOpacity("ITEM_MATERIAL", 1);
        }
        public void onStateFinish(){
            parent.sendEvent(new ActorEvent(ActorEvent.State.SHOOT));
        }
    }
    public class ActorStateAppearPoliceman extends ActorState{
        public ActorStateAppearPoliceman(String stateName, String nextStateName, float stateDuration, float animationSpeed, String anim){
            super(stateName, nextStateName, stateDuration, animationSpeed, anim);
        }
        public void onStateStart(){
            if(parent.spawnObject != null)parent.spawnObject.setAffectedDoorsState(DoorObject.State.OPEN);
        }
        public void onStateUpdate(float t){
            if(t > 0.35f)animatedRederable.setMaterialOpacity("ITEM_MATERIAL", 1);
        }
        public void onStateFinish(){
            parent.sendEvent(new ActorEvent(ActorEvent.State.SHOOT));
        }
    }
    public ActorEnemyObject(SpawnObject spawnObject, ActorType actorType) {
        super(spawnObject, actorType.modelName, "test_actor.g3dj");

        switch (actorType){
            case ENEMY_1:
                addActorState(new ActorStateAppear("APPEAR", "ACTION", 1, 0.7f, "APPEAR"));
                addActorState(new ActorStateAction("ACTION", "DISAPPEAR", 1.6f, 0.7f, "ACTION1"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case ENEMY_2:
                addActorState(new ActorStateAppear("APPEAR", "IDLE", 1, 0.7f, "APPEAR"));
                addActorState(new ActorState("IDLE", "ACTION", 0.6f, 0.7f, "IDLE"));
                addActorState(new ActorStateAction("ACTION", "DISAPPEAR", 1.3f, 0.9f, "ACTION1"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case ENEMY_3:
                addActorState(new ActorStateAppear("APPEAR", "IDLE", 1, 0.7f, "APPEAR"));
                addActorState(new ActorState("IDLE", "ACTION", 1.0f, 0.7f, "IDLE"));
                addActorState(new ActorStateAction("ACTION", "DISAPPEAR", 1.6f, 0.7f, "ACTION1"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case ENEMY_POLICE:
                addActorState(new ActorStateAppearPoliceman("APPEAR", "ACTION", 1.2f, 0.9f, "ACTION1"));
                addActorState(new ActorStateAction("ACTION", "DISAPPEAR", 1.6f, 0.7f, "ACTION1"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
        }

    }

    public void onInit() {
        super.onInit();
        switchState("APPEAR");
        animatedRederable.setMaterialOpacity("ITEM_MATERIAL", 0);
    }

    public void onActorEvent(ActorEvent e){
        switch(e.state){
            case SET_DISAPPEAR:
                switchState("DISSAPEAR");
                break;
        }
    }

    public void onCollision(GameObject o, Vector3 p){

        if(o.getClass() == BulletObject.class){
            //if(state != State.HIT && state != State.DIE && state != State.DISAPPEAR) setState(State.HIT);
            if(currentState != null && currentState.name != "DIE" && currentState.name != "REMOVE") {
                switchState("DIE");
                sendEvent(new ActorEvent(ActorEvent.State.DIE));
                sceneManager.addGameObject(new BulletSplashObject(p.cpy(), new Color(0.6f, 0, 0, 0)));
            }
        }
    }
}
