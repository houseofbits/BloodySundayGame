package GameObjects;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

import java.util.Random;

import GameEvents.ActorEvent;
import Utils.RandomDistribution;

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public enum State {
        READY,              //Ready to spawn object
        LIVE,               //Object is live
    }

    public enum SpawnType{
        ENEMY1,
        ENEMY2,
        ENEMY3,
        NPC1,
        NPC2,
        NPC3
    }

    private Array<Vector3> spawnPoints = new Array<Vector3>();
    private Array<String> affectedDoors = new Array<String>();
    private RandomDistribution<SpawnType> actorDistribution = new RandomDistribution<SpawnType>();

    public String targetDoorName = null;
    public Vector3 position;

    public State state = null;

    float   nextSpawnTimer = 0;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    Random random = new Random();

    public SpawnObject(String name, String doorName, Vector3 pos){
        this.collide = false;
        this.setName(name);
        targetDoorName = doorName;
        position = pos;

        AddSpawnPoint(pos);
        AddAffectedDoor(doorName);
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

        nextSpawnTimer = (random.nextFloat() * 3);
    }

    public void onUpdate() {
        if(state == State.READY) {
            nextSpawnTimer = nextSpawnTimer - this.sceneManager.frame_time_s;
            if(nextSpawnTimer <= 0){

                //random actor type
                RandomDistribution<SpawnType>.Node node = actorDistribution.get();

                if(spawnPoints.size > 0) {

                    position = spawnPoints.get(random.nextInt(spawnPoints.size));

                    //Do spawn
                    sceneManager.AddGameObject(new ActorObject(getName(), targetDoorName, position));
                    //System.out.println("Create new actor");
                    state = State.LIVE;
                }
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

    public void AddSpawnPoint(Vector3 p){
        spawnPoints.add(p);
    }

    public void AddActorType(SpawnType type, float weight){
        actorDistribution.add(type, weight);
    }

    public void AddAffectedDoor(String name){
        affectedDoors.add(name);
    }

}
