package GameObjects;

/**
 * Created by T510 on 7/29/2017.
 */

//import com.mygdx.game.GameObject;
//import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Color;
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
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.ActorEvent;
import GameEvents.DoorEvent;
import Utils.ParametricFunctions;

public class DoorObject extends GameObject {

    public enum State{
        CLOSED,
        OPEN,
        OPENING,
        CLOSING,
    }

    private Vector3  position;
    public float advancement = 0;
    public float speedOpening = 1.5f;
    public float speedClosing = 2;
    public State state = State.CLOSED;

    public float angleMin = 0;
    public float angleMax = 110;

    Renderable renderable = null;
    IntersectionMesh intersectionMesh = null;

    public DoorObject (String n, Vector3 pos, String filename){
        this.collide = true;
        this.setName(n);
        position = pos;
        renderable = new Renderable(this, filename);
        intersectionMesh = new IntersectionMesh(this, filename);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
        renderable.create();
        intersectionMesh.create();
    }

    public void onInit() {
        renderable.init();
        intersectionMesh.init();
    }

    public void onDoorEvent(DoorEvent e){

        if(e.action == DoorEvent.Action.SET_STATE) {
            if (e.state == State.CLOSED) this.closeDoor();
            if (e.state == State.OPEN) this.openDoor();
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

        float advy = ParametricFunctions.exponentialEasing(advancement, 0.3f);

        if(state == State.CLOSING)advy = ParametricFunctions.exponentialEasing(advancement, 0.7f);

        float angle = angleMin + ((angleMax - angleMin) * advy);

        if(renderable.modelInstance == null)return;

        renderable.modelInstance.transform.idt();
        renderable.modelInstance.transform.translate(this.position);
        renderable.modelInstance.transform.rotate(0,1,0, angle);

        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }

    public boolean intersectRay(Ray ray, Vector3 inter){

        if(renderable.modelInstance == null)return false;

        intersectionMesh.transform = renderable.modelInstance.transform;
        return intersectionMesh.IntersectRay(ray, inter);
    }
    public void onCollision(GameObject o, Vector3 p){
        if(o.getClass() == BulletObject.class) {
            sceneManager.AddGameObject(new BulletSplashObject(p.cpy(), new Color(0.3f, 0.5f,0.3f,0)));
            closeDoor();
        }
    }
}