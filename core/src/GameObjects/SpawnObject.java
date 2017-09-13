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

    public enum SpawnType{  //???????
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
    public Vector3 position;
    public State state = null;
    private float   nextSpawnTimer = 0;
    private Random random = new Random();

    public SpawnObject(String name, String doorName, Vector3 pos){
        this.collide = false;
        this.setName(name);
        position = pos;

        addSpawnPoint(pos);
        addAffectedDoor(doorName);
    }

    public SpawnObject(String name) {
        this.collide = false;
        this.setName(name);
    }

    public Vector3 getSpawnedPosition(){
        return position;
    }

    public void onCreate(SceneManager sceneManagerRef){

        super.onCreate(sceneManagerRef);

        setReadyToSpawn();
    }

    public void onInit(){    }

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

                    sceneManager.AddGameObject(new ActorEnemyObject(this));

                    state = State.LIVE;
                }
            }
        }
    }

    public void render () { }
    public void dispose () {
        super.dispose();
    }

    public void addSpawnPoint(Vector3 p){
        spawnPoints.add(p);
    }

    public void addActorType(SpawnType type, float weight){
        actorDistribution.add(type, weight);
    }

    public void addAffectedDoor(String name){
        affectedDoors.add(name);
    }

    public void setDoorState(DoorObject.State state){


    }
}
