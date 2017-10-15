package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;

import GameEvents.ActorEvent;
import GameEvents.GameStateEvent;
import Utils.Error;

/**
 * Created by T510 on 9/12/2017.
 */

public class ActorNPCObject extends ActorObject {

    public ActorNPCObject(SpawnObject.SpawnPoint spawnObject, ActorType type) {
        super(spawnObject, type, "test_actor.g3dj");

        switch (actorType){
            case NPC_1:
                addActorState(new ActorStateAppear("APPEAR", "DISAPPEAR", 1, 0.7f, "APPEAR"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case NPC_2:
                addActorState(new ActorStateAppear("APPEAR", "IDLE", 1, 0.7f, "APPEAR"));
                addActorState(new ActorState("IDLE", "DISAPPEAR", 0.6f, 0.7f, "IDLE"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case NPC_3:
                addActorState(new ActorStateAppear("APPEAR", "IDLE", 1, 0.7f, "APPEAR"));
                addActorState(new ActorState("IDLE", "DISAPPEAR", 1.0f, 0.7f, "IDLE"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorState("DISAPPEAR", "REMOVE", 1, 1.0f, "APPEAR"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
            case NPC_DOCTOR:
                addActorState(new ActorStateAppear("APPEAR", "REMOVE", 1f, 0.7f, "IDLE"));
                addActorState(new ActorState("DIE", "REMOVE", 0.7f, 1.5f, "DIE1"));
                addActorState(new ActorStateDisappear("REMOVE", null, 1, 1.0f, null));
                break;
        }

    }

    public int doctorHealth = 100;

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
    public void onGameStateEvent(GameStateEvent e){
        super.onGameStateEvent(e);
    }
    public void onCollision(GameObject o, Vector3 p){

        if(o.getClass() == BulletObject.class){
            //if(state != State.HIT && state != State.DIE && state != State.DISAPPEAR) setState(State.HIT);
            if(currentState != null && currentState.name != "DIE" && currentState.name != "REMOVE") {
                switchState("DIE");
                sendEvent(new ActorEvent(ActorEvent.State.DIE));
                getSceneManager().addGameObject(new BulletSplashObject(p.cpy(), new Color(0.6f, 0, 0, 0)));
            }
        }
    }
}
