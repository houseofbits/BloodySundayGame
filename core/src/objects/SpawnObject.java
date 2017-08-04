package objects;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
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

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public String targetDoorName = null;
    public Vector3 position;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public SpawnObject(String doorName, Vector3 pos){
        targetDoorName = doorName;
        position = pos;
    }

    public void init(SceneManager sceneManagerRef){

        super.init(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();

        model = modelBuilder.createBox(0.15f, 0.15f, 0.15f,
                new Material(ColorAttribute.createDiffuse(1,1,0,1)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(position);


    }

    public void onDoorChangeEvent(DoorEvent e){

//        Gdx.app.log("onDoorChangeEvent", "call");

        if(targetDoorName == e.getName() && e.action == DoorEvent.Action.STATE_CHANGED) {
            //if (e.state == DoorObject.State.CLOSED) ready to spawn new Actors
        }
    }

    public void update() {



    }

    public void render () {

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
