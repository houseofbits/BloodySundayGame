package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;

import GameEvents.ActorEvent;

/**
 * Created by T510 on 9/12/2017.
 */

public class ActorNPCObject extends ActorObject {

    public ActorNPCObject(SpawnObject spawnObject, ActorType actorType) {
        super(spawnObject, actorType.modelName, "test_actor.g3dj");

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

            switchState("DIE");

            sceneManager.addGameObject(new BulletSplashObject(p.cpy(), new Color(0.6f, 0, 0, 0)));
        }
    }
}
