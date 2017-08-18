package GameObjects;

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

import java.util.Random;

import GameEvents.ActorEvent;

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public enum State {
        READY,              //Ready to spawn object
        LIVE,               //Object is live
    }

    public String targetDoorName = null;
    public Vector3 position;

    public State state = null;

    float   nextSpawnTimer = 0;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public SpawnObject(String name, String doorName, Vector3 pos){
        this.receive_hits = false;
        this.setName(name);
        targetDoorName = doorName;
        position = pos;
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();

        model = modelBuilder.createBox(0.15f, 0.15f, 0.15f,
                new Material(ColorAttribute.createDiffuse(1,1,0,1)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(position);

        setReadyToSpawn();
    }

    public void onInit(){


    }

    public void onActorEvent(ActorEvent e){
        if(e.state == ActorEvent.State.REMOVED){
            setReadyToSpawn();
        }
    }

    public void setReadyToSpawn(){
        state = State.READY;

        Random r = new Random();
        nextSpawnTimer = (r.nextFloat() * 4);
    }

    public void onUpdate() {
        if(state == State.READY) {
            nextSpawnTimer = nextSpawnTimer - this.sceneManager.frame_time_s;
            if(nextSpawnTimer <= 0){

                //random actor type
                /*

                class SpawnFactor{
                    public ActorType    actorType;
                    public float        chance;
                    public int          spawned;
                    public float        factor;
                }


                a1 - 0.1
                a2 - 0.5
                a3 - 0.2

                a1s - 3 0.3
                a2s - 1 0.1
                a3s - 6 0.6

                total spawned - 10

                chance factor = a / s

                a1 chance = 0.1 / 0.3 = 0.33;
                a2 chance = 0.5 / 0.1 = 5;
                a3 chance = 0.2 / 0.6 = 0.33;

                randomize chance of each being spawned
                when each factor gets closer to 1.0 random
                chance will matter

                */

                sceneManager.AddGameObject(new ActorObject(getName(), targetDoorName, position));
                //System.out.println("Create new actor");
                state = State.LIVE;
            }
        }
    }

    public void render () {

        modelBatch.begin(sceneManager.scene.cam);
        modelBatch.render(instance, sceneManager.scene.environment);
        modelBatch.end();

    }
    public void dispose () {
        super.dispose();
        modelBatch.dispose();
        model.dispose();
    }

}
