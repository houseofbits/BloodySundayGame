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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameObject;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;

public class DoorObject extends GameObject {

    public enum State{
        CLOSED,
        OPEN,
        OPENING,
        CLOSING,
    }

    private Vector3  position;
    //private Vector3  size;
    public float advancement = 0;
    public float speedOpening = 1.5f;
    public float speedClosing = 2;
    public State state = State.CLOSED;

    public float angleMin = 0;
    public float angleMax = 110;


    Renderable renderable;
//    public ModelBatch modelBatch;
//    public Model model;
//    public ModelInstance instance;

    public BoundingBox boundingBox;

    public DoorObject (String n, Vector3 pos, String filename){
        this.receive_hits = true;
        this.setName(n);
        position = pos;
        //size = new Vector3(0.9f, 2.2f, 0.1f);
        boundingBox = new BoundingBox();
        //boundingBox = new BoundingBox(new Vector3(size.cpy().scl(-0.5f)), new Vector3(size.cpy().scl(0.5f)));
        renderable = new Renderable(this, filename);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
        renderable.create();
    }

    public void onInit() {
        renderable.init();
        renderable.modelInstance.calculateBoundingBox(boundingBox);
    }

    public void onDoorEvent(DoorEvent e){

        if(e.action == DoorEvent.Action.SET_STATE) {
            if (e.state == State.CLOSED) this.closeDoor();
            if (e.state == State.OPEN) this.openDoor();
        }
        //if(e.action == DoorEvent.Action.STATE_CHANGED) {
        //    if (e.state == State.CLOSED) this.openDoor();
        //    if (e.state == State.OPEN) this.closeDoor();
        //}
    }

    public void openDoor(){
        if(state != State.OPEN)state = State.OPENING;
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

        if(renderable.modelInstance == null)return;

        renderable.modelInstance.transform.idt();
        renderable.modelInstance.transform.translate(this.position);
        //renderable.modelInstance.transform.translate(size.x/2,0,0);
        renderable.modelInstance.transform.rotate(0,1,0, angle);
        //renderable.modelInstance.transform.translate(-size.x/2,0,0);

        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }

    public boolean intersectRay(Ray ray, Vector3 inter){
        if(renderable.modelInstance == null)return false;
        Ray r = ray.cpy();
        r.mul(renderable.modelInstance.transform.cpy().inv());
        if (Intersector.intersectRayBounds(r, boundingBox, inter)) {
            inter.mul(renderable.modelInstance.transform);
            return true;
        }
        return false;
    }

}