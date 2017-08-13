package GameObjects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;
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

    Renderable renderable;

    public void setState(State s){

        state = s;

        if(state != null) {

            stateTimer = 1;

            //System.out.println("Set state: " + s.toString());

            switch (state) {
                case APPEAR:
                    sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.OPEN), doorName);
                    //System.out.println("Open door event sent "+doorName);
                    break;
                case IDLE:

                    break;
                case ACTION:

                    break;
                case DISAPPEAR:
                    sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.CLOSED), doorName);
                    break;
            }
        }
    }

    public ActorObject(String spawn, String door, Vector3 pos){
        this.receive_hits = false;
        spawnName = spawn;
        doorName = door;
        position = pos;
        renderable = new Renderable(this);
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);

        renderable.create("test_actor.g3dj");
    }

    public void onInit(){

        renderable.init();
        renderable.translate(position);

        Vector3 v = new Vector3(position);
        v = v.sub(sceneManager.scene.cam.position);
        Vector2 v2n1 = new Vector2(v.x,v.z);
        v2n1 = v2n1.nor();

        float crs = v2n1.crs(new Vector2(sceneManager.scene.cam.direction.x,sceneManager.scene.cam.direction.z).nor());

        if(renderable.modelInstance != null)renderable.modelInstance.transform.rotate(0,1,0, (crs*90));

        setState(State.APPEAR);
    }

    public void onUpdate() {

        if(state!= null && stateTimer > 0)stateTimer = stateTimer - sceneManager.frame_time_s;

        //set next state
        if(state!= null && stateTimer <= 0){

            //System.out.println("State finished: " + state.toString());

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
                    setState(State.DISAPPEAR);
                    break;
                case DIE:
                    setState(State.DISAPPEAR);
                    break;
                case DISAPPEAR:
                    //finish, delete object
                    setState(null);
                    this.setDispose(true);
                    sendEvent(new ActorEvent(ActorEvent.State.REMOVED), spawnName);
                    break;
            }
        }
    }

    public void render () {
        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }
}
