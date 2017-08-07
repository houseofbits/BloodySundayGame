package GameObjects;

/**
 * Created by T510 on 7/29/2017.
 */

//import com.mygdx.game.GameObject;
//import com.badlogic.gdx.graphics.Color;
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
    public float speedOpening = 1.5f;
    public float speedClosing = 2;
    public State state = State.CLOSED;

    public float angleMin = 0;
    public float angleMax = 110;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public DoorObject (String n, Vector3 pos){

        this.setName(n);
        position = pos;
        size = new Vector3(0.9f, 2.2f, 0.1f);
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(this.size.x, this.size.y, this.size.z,
                new Material(ColorAttribute.createDiffuse(0.5f, 0.2f, 0f, 0f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        this.position.y = size.y / 2;

        //this.sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, State.OPEN));
    }

    public void onInit() {

    }

    public void onDoorEvent(DoorEvent e){

        if(e.action == DoorEvent.Action.SET_STATE) {
            if (e.state == State.CLOSED) this.closeDoor();
            if (e.state == State.OPEN) this.openDoor();
        }
        if(e.action == DoorEvent.Action.STATE_CHANGED) {
        //    if (e.state == State.CLOSED) this.openDoor();
        //    if (e.state == State.OPEN) this.closeDoor();
        }
    }

    public void openDoor(){
        if(state != State.OPEN)state = State.OPENING;
        //System.out.println(" call openDoor "+state.toString());
    }

    public void closeDoor(){
        if(state != State.CLOSED)state = State.CLOSING;
    }

    //Implement in derived classes
    public void advanceMovement(){
        if(state == State.OPENING)advancement = advancement + (speedOpening * sceneManager.frame_time_s);
        if(state == State.CLOSING)advancement = advancement - (speedClosing * sceneManager.frame_time_s);
    }

    public void onUpdate(){

        advanceMovement();

        if(advancement >= 1 && state != State.OPEN){
            advancement = 1;
            state = State.OPEN;
            this.sendEvent(new DoorEvent(DoorEvent.Action.STATE_CHANGED, State.OPEN));
        }
        if(advancement <= 0 && state != State.CLOSED){
            advancement = 0;
            state = State.CLOSED;
            this.sendEvent(new DoorEvent(DoorEvent.Action.STATE_CHANGED, State.CLOSED));
        }
    }

    public void render () {

        float angle = angleMin + ((angleMax - angleMin) * advancement);

        instance.transform.idt();
        instance.transform.translate(this.position);
        instance.transform.translate(size.x/2,0,0);
        instance.transform.rotate(0,1,0, angle);
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