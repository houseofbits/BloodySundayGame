package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.AnimatedRenderable;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import java.util.HashMap;
import java.util.Map;

import GameEvents.ActorEvent;
import GameEvents.DoorEvent;

/**
 * Created by KristsPudzens on 04.08.2017.
 */

public class ActorObject extends GameObject {

    public class ActorState{

        public String name = "";
        public String animationName = null;
        public float speed = 1.0f;
        public float time = 1.0f;
        public String nextState = "";
        public ActorObject parent = null;

        public ActorState(String stateName, String nextStateName, String animationTrackName, float animationSpeed, float stateTime){
            name = stateName;
            animationName = animationTrackName;
            nextState = nextStateName;
            speed = animationSpeed;
            time = stateTime;
        }

        public void onStateStart(){}
        public void onStateFinish(){}
    }

    public class ActorStateAppear extends ActorState{
        public ActorStateAppear(String nextStateName, String animationTrackName, float animationSpeed, float stateTime){
            super("APPEAR", nextStateName, animationTrackName, animationSpeed, stateTime);
        }
        public void onStateStart(){
            if(parent.spawnObject != null)parent.spawnObject.setDoorState(DoorObject.State.OPEN);
        }
    }
    public class ActorStateDisappear extends ActorState{
        public ActorStateDisappear(){
            super("DISAPPEAR", null, null, 1.0f, 1.0f);
        }
        public ActorStateDisappear(String nextStateName, String animationTrackName, float animationSpeed, float stateTime){
            super("DISAPPEAR", nextStateName, animationTrackName, animationSpeed, stateTime);
        }
        public void onStateFinish(){
            parent.setDispose(true);
            //sendEvent(new ActorEvent(ActorEvent.State.REMOVED), spawnName);
        }
    }

    private Map<String, ActorState> actorStateMap = new HashMap<String, ActorState>();

    private ActorState currentState = null;

    public void switchState(String newState){
        if(actorStateMap.containsKey(newState)){
            ActorState st = actorStateMap.get(newState);
            currentState = st;
           // animatedRederable.PlayAnim(st.animationName);
            //animatedRenderable.playNext(st.animationName, st.speed);
            st.onStateStart();
            stateTimer = st.time;
        }else{
            currentState = null;
        }
    }

    public void addActorState(ActorState state){
        state.parent = this;
        actorStateMap.put(state.name, state);
    }

    public  Vector3 position;
    float   stateTimer = 0;

    public SpawnObject spawnObject = null;

    AnimatedRenderable animatedRederable = null;
    IntersectionMesh intersectionMesh = null;

    public ActorObject(SpawnObject spawn, String renderModel, String colModel){

        this.collide = true;

        position = spawn.getSpawnedPosition();

        intersectionMesh = new IntersectionMesh(this, colModel);
        animatedRederable = new AnimatedRenderable(this, renderModel);

        setName("actor_"+System.identityHashCode(this));
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);
        animatedRederable.create();
        intersectionMesh.create();
    }

    public void onInit(){

        intersectionMesh.init();
        animatedRederable.init();
        animatedRederable.translate(position);

        Vector3 v = new Vector3(position);
        v = v.sub(sceneManager.scene.cam.position);
        Vector2 v2n1 = new Vector2(v.x,v.z);
        v2n1 = v2n1.nor();

        float crs = v2n1.crs(new Vector2(sceneManager.scene.cam.direction.x,sceneManager.scene.cam.direction.z).nor());

        if(animatedRederable.modelInstance != null)animatedRederable.modelInstance.transform.rotate(0,1,0, (crs*90));
    }

    public void onUpdate() {

        if(currentState!= null && stateTimer > 0)stateTimer = stateTimer - sceneManager.frame_time_s;

        if(currentState != null && stateTimer <= 0) {
            currentState.onStateFinish();
            switchState(currentState.nextState);
        }

        /*
        if(state!= null && stateTimer > 0)stateTimer = stateTimer - sceneManager.frame_time_s;

        //set next state
        if(state!= null && stateTimer <= 0){

            DoorObject dobj = (DoorObject)sceneManager.getObjectByName(doorName);
            if(dobj != null
                    && (dobj.state == DoorObject.State.CLOSED || dobj.state == DoorObject.State.CLOSING)
                    && (state == State.IDLE || state == State.APPEAR)){

                setState(State.DISAPPEAR);
                animatedRederable.StopAnim();

                return;
            }

            switch(state){
                case APPEAR:
                    setState(State.IDLE);
                    break;
                case IDLE:
                    setState(State.ACTION);
                    break;
                case ACTION:
                    setState(State.DISAPPEAR);
                    break;
                case HIT:
                    setState(State.DIE);
                    break;
                case DIE:
                    setState(State.DISAPPEAR);
                    break;
                case DISAPPEAR:
                    setState(null);
                    this.setDispose(true);
                    sendEvent(new ActorEvent(ActorEvent.State.REMOVED), spawnName);
                    break;
            }
        }*/
    }

    public void render () {
        animatedRederable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        animatedRederable.dispose();
        actorStateMap.clear();
    }

    public boolean intersectRay(Ray ray, Vector3 inter){
        if(animatedRederable.modelInstance == null)return false;
        intersectionMesh.transform = animatedRederable.modelInstance.transform;
        return intersectionMesh.IntersectRay(ray, inter);
    }

    public void onCollision(GameObject o, Vector3 p){
        if(o.getClass() == BulletObject.class){
            //if(state != State.HIT && state != State.DIE && state != State.DISAPPEAR) setState(State.HIT);

            sceneManager.AddGameObject(new BulletSplashObject(p.cpy(), new Color(0.6f, 0, 0, 0)));
        }
    }
}
