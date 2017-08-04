package objects;

/**
 * Created by T510 on 7/29/2017.
 */

//import com.mygdx.game.GameObject;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

import events.DoorEvent;

public class DoorObject extends GameObject {

    public enum State{
        CLOSED,
        OPEN,
        OPENING,
        CLOSING,
    }

    private Vector3  position;
    private Vector3  size;
    public float advancement = 0;
    public float advancement_min = 0;
    public float advancement_max = 110;
    public float speed = 100;
    public State state = State.CLOSED;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public DoorObject (String n, Vector3 pos){

        name = n;
        position = pos;
        size = new Vector3(0.9f, 2.2f, 0.1f);
    }

    public void init(SceneManager sceneManagerRef){

        super.init(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(this.size.x, this.size.y, this.size.z,
                new Material(ColorAttribute.createDiffuse(0.5f, 0.2f, 0f, 0f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        this.position.y = size.y / 2;
    }

    public void onDoorEvent(DoorEvent e){

//        Gdx.app.log("onDoorEvent", "call");

        if(e.action == DoorEvent.Action.SET_STATE) {
            if (e.state == State.CLOSED) this.closeDoor();
            if (e.state == State.OPEN) this.openDoor();
        }
        if(e.action == DoorEvent.Action.STATE_CHANGED) {
            if (e.state == State.CLOSED) this.openDoor();
            if (e.state == State.OPEN) this.closeDoor();
        }
    }

    public void openDoor(){
        if(state != State.OPEN)state = State.OPENING;
    }

    public void closeDoor(){
        if(state != State.CLOSED)state = State.CLOSING;
    }

    //Implement in derived classes
    public void advanceMovement(){
        if(state == State.OPENING)advancement = advancement + (speed * sceneManager.frame_time_s);
        if(state == State.CLOSING)advancement = advancement - (speed * sceneManager.frame_time_s);
    }

    public void update(){

        advanceMovement();

        if(advancement >= advancement_max && state != State.OPEN){
            state = State.OPEN;
            this.sceneManager.eventManager.sendEvent(new DoorEvent(DoorEvent.Action.STATE_CHANGED, State.OPEN));
        }
        if(advancement <= advancement_min && state != State.CLOSED){
            state = State.CLOSED;
            this.sceneManager.eventManager.sendEvent(new DoorEvent(DoorEvent.Action.STATE_CHANGED, State.CLOSED));
        }
    }

    public void render () {

        instance.transform.idt();
        instance.transform.translate(this.position);
        instance.transform.translate(size.x/2,0,0);
        instance.transform.rotate(0,1,0, advancement);
        instance.transform.translate(-size.x/2,0,0);

        modelBatch.begin(sceneManager.cam);
        modelBatch.render(instance, sceneManager.environment);
        modelBatch.end();

    }
    public void dispose () {
        super.dispose();
        modelBatch.dispose();
        model.dispose();
    }

}