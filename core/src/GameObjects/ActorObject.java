package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.AnimatedRenderable;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.ActorEvent;
import GameEvents.DoorEvent;

/**
 * Created by KristsPudzens on 04.08.2017.
 */

public class ActorObject extends GameObject {

    public enum State{
        APPEAR,                 //Open door
        IDLE,                   //Do nothing
        ACTION,                 //Perform action
        DIE,                    //Die
        HIT,                    //Get hit
        DISAPPEAR,              //Disappear, wak away
    }

    public Vector3 position;
    public State state = null;
    float   stateTimer = 0;

    String  doorName = "";
    String  spawnName = "";

   // Renderable renderable = null;
    AnimatedRenderable animatedRederable = null;
    IntersectionMesh intersectionMesh = null;

    public void setState(State s){

        state = s;

        if(state != null) {

            stateTimer = 1;

            //System.out.println("Set state: " + s.toString());

            switch (state) {
                case APPEAR:
                    sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.OPEN), doorName);
                    //animatedRederable.setColor(0.2f,0.2f,0.2f);
                    animatedRederable.PlayAnim("anim1");
                    break;
                case IDLE:
                    //animatedRederable.setColor(0.3f,0.3f,0.5f);
                    break;
                case ACTION:
                    sendEvent(new ActorEvent(ActorEvent.State.SHOOT));
                    animatedRederable.setColor(0.5f,0.5f,0.3f);
                    break;
                case HIT:
                    animatedRederable.setColor(0.5f,0,0);
                    break;
                case DIE:
                    sendEvent(new ActorEvent(ActorEvent.State.DIE));
                    //animatedRederable.setColor(0.5f,0.2f,0.2f);
                    break;
                case DISAPPEAR:
                    sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.CLOSED), doorName);
                    break;
            }
        }
    }

    public ActorObject(String spawn, String door, Vector3 pos){
        this.collide = true;
        spawnName = spawn;
        doorName = door;
        position = pos;
       // renderable = new Renderable(this, "test_actor.g3dj");
        intersectionMesh = new IntersectionMesh(this, "test_actor.g3dj");
        animatedRederable = new AnimatedRenderable(this, "test_actor_anim.g3dj");

        setName("actor_"+System.identityHashCode(this));
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);
       // renderable.create();
        animatedRederable.create();
        intersectionMesh.create();
    }

    public void onInit(){

       // renderable.init();
        intersectionMesh.init();
        animatedRederable.init();
        animatedRederable.translate(position);
        //renderable.translate(position);

        Vector3 v = new Vector3(position);
        v = v.sub(sceneManager.scene.cam.position);
        Vector2 v2n1 = new Vector2(v.x,v.z);
        v2n1 = v2n1.nor();

        float crs = v2n1.crs(new Vector2(sceneManager.scene.cam.direction.x,sceneManager.scene.cam.direction.z).nor());

       // if(renderable.modelInstance != null)renderable.modelInstance.transform.rotate(0,1,0, (crs*90));

        if(animatedRederable.modelInstance != null)animatedRederable.modelInstance.transform.rotate(0,1,0, (crs*90));

        setState(State.APPEAR);
    }

    public void onUpdate() {

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
        }
    }

    public void onActorEvent(ActorEvent e){
        switch(e.state){
            case SET_DISAPPEAR:
                setState(State.DISAPPEAR);
                break;
        }
    }

    public void render () {
//        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
        animatedRederable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
    //    renderable.dispose();
        animatedRederable.dispose();
    }

    public boolean intersectRay(Ray ray, Vector3 inter){
        if(animatedRederable.modelInstance == null)return false;
        intersectionMesh.transform = animatedRederable.modelInstance.transform;
        return intersectionMesh.IntersectRay(ray, inter);
    }

    public void onCollision(GameObject o, Vector3 p){
        if(o.getClass() == BulletObject.class){
            if(state != State.HIT && state != State.DIE && state != State.DISAPPEAR) setState(State.HIT);
            sceneManager.AddGameObject(new BulletSplashObject(p.cpy(), new Color(0.6f, 0, 0, 0)));
        }
    }
}
