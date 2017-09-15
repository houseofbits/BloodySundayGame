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

import static GameObjects.SpawnObject.ActorType.ENEMY1;

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public enum State {
        READY,              //Ready to spawn object
        LIVE,               //Object is live
    }

    public enum ActorType{
        ENEMY1,
        ENEMY2,
        ENEMY3,
        NPC1,
        NPC2,
        NPC3
    }

    private Array<Vector3> spawnPoints = new Array<Vector3>();
    private Array<String> affectedDoors = new Array<String>();
    private RandomDistribution<ActorType> actorDistribution = new RandomDistribution<ActorType>();
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

    public void spawn(ActorType t){
        if(spawnPoints.size > 0) {
            position = spawnPoints.get(random.nextInt(spawnPoints.size));
            switch(t){
                case ENEMY1:
                    sceneManager.AddGameObject(new ActorEnemyObject(this));
                    break;
            };
            state = State.LIVE;
        }
    }

    public void spawn(){
        RandomDistribution<ActorType>.Node node = actorDistribution.get();
        spawn(node.data);
    }

    public void onUpdate() {
        if(state == State.READY) {
            nextSpawnTimer = nextSpawnTimer - this.sceneManager.frame_time_s;
            if(nextSpawnTimer <= 0){
                spawn(ENEMY1);
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

    public void addActorType(ActorType type, float weight){
        actorDistribution.add(type, weight);
    }

    public void addAffectedDoor(String name){
        affectedDoors.add(name);
    }

    public void setAffectedDoorsState(DoorObject.State doorState){
        for (int i=0; i<affectedDoors.size; i++){
           DoorObject d = (DoorObject)sceneManager.getObjectByName(affectedDoors.get(i));
            if(d != null){
                d.setState(doorState);
            }
        }
    }
}
