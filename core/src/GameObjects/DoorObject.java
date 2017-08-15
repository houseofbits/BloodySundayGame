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
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;

/*
    float exponentialEasing (float x, float a){

      float epsilon = 0.00001;
      float min_param_a = 0.0 + epsilon;
      float max_param_a = 1.0 - epsilon;
      a = max(min_param_a, min(max_param_a, a));

      if (a < 0.5){
        // emphasis
        a = 2.0*(a);
        float y = pow(x, a);
        return y;
      } else {
        // de-emphasis
        a = 2.0*(a-0.5);
        float y = pow(x, 1.0/(1-a));
        return y;
      }
    }


//------------------------------------------------
float quadraticBezier (float x, float a, float b){
  // adapted from BEZMATH.PS (1993)
  // by Don Lancaster, SYNERGETICS Inc.
  // http://www.tinaja.com/text/bezmath.html

  float epsilon = 0.00001;
  a = max(0, min(1, a));
  b = max(0, min(1, b));
  if (a == 0.5){
    a += epsilon;
  }

  // solve t from x (an inverse operation)
  float om2a = 1 - 2*a;
  float t = (sqrt(a*a + om2a*x) - a)/om2a;
  float y = (1-2*b)*(t*t) + (2*b)*t;
  return y;
}

 */


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
        this.receive_hits = true;
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

}