package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.AnimatedRenderable;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.SceneManager;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import GameEvents.ActorEvent;
import Utils.Error;

/**
 * Created by KristsPudzens on 04.08.2017.
 */

public class ActorObject extends GameObject {

    public enum ActorType{

        ENEMY_1(ActorEnemyObject.class, "characters/character2.g3dj"),
        ENEMY_2(ActorEnemyObject.class, "characters/character3.g3dj"),
        ENEMY_3(ActorEnemyObject.class, "characters/character4.g3dj"),
        ENEMY_POLICE(ActorEnemyObject.class, "characters/cop.g3dj"),
        NPC_1(ActorNPCObject.class, "characters/character2.g3dj"),
        NPC_2(ActorNPCObject.class, "characters/character3.g3dj"),
        NPC_3(ActorNPCObject.class, "characters/character4.g3dj"),
        NPC_DOCTOR(ActorNPCObject.class, "characters/doctor.g3dj");

        private ActorType(Class actorClass, String model){
            modelName = model;
            actorObjectClass = actorClass;
        }
        public String modelName;
        private Class actorObjectClass;

        public ActorObject createInstance(SpawnObject.SpawnPoint spawn){
            try {
                Constructor<?> ctor = actorObjectClass.getConstructor(SpawnObject.SpawnPoint.class, ActorType.class);
                return (ActorObject)ctor.newInstance(spawn, this);
            }catch (Exception e){
                Error.log(e.getMessage());
            }
            return null;
        }
    }

    public class ActorState{

        public String name = "";
        public float speed = 1.0f;
        public float duration = 1.0f;
        public String nextState = "";
        public ActorObject parent = null;
        public String animationName = null;

        public ActorState(String stateName, String nextStateName, float stateDuration, float animationSpeed, String anim){
            name = stateName;
            nextState = nextStateName;
            speed = animationSpeed;
            duration = stateDuration;
            animationName = anim;
        }

        public void onStateStart(){}
        public void onStateUpdate(float t){}
        public void onStateFinish(){}
    }

    public class ActorStateAppear extends ActorState{
        public ActorStateAppear(String stateName, String nextStateName, float stateDuration, float animationSpeed, String anim){
            super(stateName, nextStateName, stateDuration, animationSpeed, anim);
        }
        public void onStateStart(){
            if(parent.spawnPoint != null)parent.spawnPoint.setAffectedDoorsState(DoorObject.State.OPEN);
        }
    }
    public class ActorStateDisappear extends ActorState{
        public ActorStateDisappear(String stateName, String nextStateName, float stateDuration, float animationSpeed, String anim){
            super(stateName, nextStateName, stateDuration, animationSpeed, anim);
        }
        public void onStateStart(){
            if(parent.spawnPoint != null)parent.spawnPoint.setAffectedDoorsState(DoorObject.State.CLOSED);
        }
        public void onStateFinish(){
            //Error.log("State Disappear remove");
            parent.setDispose(true);
            sendEvent(new ActorEvent(ActorEvent.State.REMOVED));
            parent.spawnPoint.setFree();
        }
    }

    private Map<String, ActorState> actorStateMap = new HashMap<String, ActorState>();

    public ActorState currentState = null;

    public void switchState(String newState){
        //Error.log("Try switch state "+newState);
        if(actorStateMap.containsKey(newState)){
            ActorState st = actorStateMap.get(newState);
            currentState = st;
            animatedRederable.PlayAnim(st.animationName, st.speed, 0.5f);
            st.onStateStart();
            stateTimer = st.duration;
            //Error.log("Switch state "+newState+" anim: "+st.name);
        }else{
            currentState = null;
        }
    }

    public void addActorState(ActorState state){
        state.parent = this;
        actorStateMap.put(state.name, state);
    }

    public ActorType actorType = null;

    public  Vector3 position;
    float   stateTimer = 0;

    public SpawnObject.SpawnPoint spawnPoint = null;

    public AnimatedRenderable animatedRederable = null;
    IntersectionMesh intersectionMesh = null;

    public ActorObject(SpawnObject.SpawnPoint spawn, ActorType type, String colModel){
        spawnPoint = spawn;
        actorType = type;
        this.collide = true;
        position = spawn.getSpawnedPosition();
        intersectionMesh = new IntersectionMesh(this, colModel);
        animatedRederable = new AnimatedRenderable(this, actorType.modelName);
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
        v = v.sub(getSceneManager().scene.cam.position);
        Vector2 v2n1 = new Vector2(v.x,v.z);
        v2n1 = v2n1.nor();

        float crs = v2n1.crs(new Vector2(getScene().cam.direction.x,getScene().cam.direction.z).nor());

        if(animatedRederable.modelInstance != null)animatedRederable.modelInstance.transform.rotate(0,1,0, (crs*90));

        /*
        Model model = sceneManager.assetsManager.get(animatedRederable.modelName, Model.class);
        if(model != null){

            for (int i = 0; i < model.animations.size; i++) {
                Animation anim = model.animations.get(i);

                System.out.println("Anim: " + anim.id+", duration: "+anim.duration);
            }

        }*/
    }

    public void onUpdate() {
        if(currentState!= null && stateTimer > 0){
            stateTimer = stateTimer - Gdx.graphics.getDeltaTime();
            currentState.onStateUpdate(currentState.duration - stateTimer);
        }
        if(currentState != null && stateTimer <= 0) {
            currentState.onStateFinish();
            switchState(currentState.nextState);
        }
    }

    public void render () {
        animatedRederable.render(getScene().cam, getScene().environment);
    }
    public void dispose () {
        super.dispose();
        animatedRederable.dispose();
        actorStateMap.clear();
    }

    public boolean intersectRay(Ray ray, Vector3 inter){
        if(animatedRederable.modelInstance == null)return false;
        intersectionMesh.transform = animatedRederable.modelInstance.transform;

        //Error.log("Inttersect: "+intersectionMesh.IntersectRay(ray, inter));
        return intersectionMesh.IntersectRay(ray, inter);
    }
}
