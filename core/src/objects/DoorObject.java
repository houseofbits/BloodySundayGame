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

    public enum DoorState{
        CLOSED,
        OPEN,
        OPENING,
        CLOSING,
    }

    private Vector3  position;
    private Vector3  size;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    public float angle = 0;
    public float speed = 100;
    public DoorState state = DoorState.CLOSED;

    public DoorObject (Vector3 pos){

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

        Gdx.app.log("onDoorEvent", "call");

        if(e.setState == DoorState.CLOSED && state != DoorState.CLOSED)state = DoorState.CLOSING;

        if(e.setState == DoorState.OPEN && state != DoorState.OPEN)state = DoorState.OPENING;
    }

    public void update(){

        if(state == DoorState.OPENING)angle = angle + (speed * sceneManager.frame_time_s);
        if(state == DoorState.CLOSING)angle = angle - (speed * sceneManager.frame_time_s);

        if(angle >= 110)state = DoorState.OPEN;
        if(angle <= 0)state = DoorState.CLOSED;

        instance.transform.idt();
        instance.transform.translate(this.position);
        instance.transform.translate(size.x/2,0,0);
        instance.transform.rotate(0,1,0, angle);
        instance.transform.translate(-size.x/2,0,0);
    }

    public void render () {

        modelBatch.begin(sceneManager.cam);
        modelBatch.render(instance, sceneManager.environment);
        modelBatch.end();

    }
    public void dispose () {
        modelBatch.dispose();
        model.dispose();
    }

}