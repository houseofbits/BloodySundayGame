package objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;
import com.mygdx.game.GameObject;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import events.ActorEvent;
import events.DoorEvent;

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

    Renderable model;

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
        spawnName = spawn;
        doorName = door;
        position = pos;
        model = new Renderable(this);
    }

    public void init(SceneManager sceneManagerRef){

        super.init(sceneManagerRef);

        model.init("test_actor.g3dj");
        model.translate(position);

        Vector3 v = new Vector3(position);
        v = v.sub(sceneManager.cam.position);
        Vector2 v2n1 = new Vector2(v.x,v.z);
        v2n1 = v2n1.nor();

        float crs = v2n1.crs(new Vector2(sceneManager.cam.direction.x,sceneManager.cam.direction.z).nor());

        model.instance.transform.rotate(0,1,0, (crs*90));

        setState(State.APPEAR);
    }

    public void update() {

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
        model.render(sceneManager.cam, sceneManager.environment);
    }
    public void dispose () {
        super.dispose();
        model.dispose();
    }
}
