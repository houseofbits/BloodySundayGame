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
import GameEvents.SpawnEvent;
import Utils.Error;
import Utils.RandomDistribution;

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public enum State {
        FREE,                   //waiting to spawn object
        OCCUPIED,               //occupied by spawned object
        READY,                  //ready to spawn new object
    }

    private Array<Vector3> spawnPoints = new Array<Vector3>();
    private Array<String> affectedDoors = new Array<String>();
    private RandomDistribution<ActorObject.ActorType> actorDistribution = new RandomDistribution<ActorObject.ActorType>();
    public Vector3 position;
    public State state = null;
    private float   nextSpawnTimer = 0;
    private static Random random = new Random();

    public SpawnObject(String name, String doorName, Vector3 pos){
        this.collide = false;
        this.setName(name);
        position = pos;

        addSpawnPoint(pos);
        addAffectedDoor(doorName);
        addActorType(ActorObject.ActorType.ENEMY_1,
                ActorObject.ActorType.ENEMY_2,
                ActorObject.ActorType.ENEMY_3,
                ActorObject.ActorType.NPC_1,
                ActorObject.ActorType.NPC_2,
                ActorObject.ActorType.NPC_3);
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

        setFree();
    }

    public void onInit(){    }

    //ActorEvent from actor attached to this spawn point
    public void onActorEvent(ActorEvent e){
        if(e.state == ActorEvent.State.REMOVED){
            setFree();
            freeReadyToSpawnObjects();
        }
    }

    //When spawned object dies it sets free spawn point so it can begin wait for new action
    private void setFree(){
        state = State.FREE;
        nextSpawnTimer = (random.nextFloat() * 1);
        sendEvent(new SpawnEvent(State.FREE));
    }

    //Perform spawn by actor type
    public void spawn(ActorObject.ActorType t){
        if(spawnPoints.size > 0) {
            position = spawnPoints.get(random.nextInt(spawnPoints.size));
            sceneManager.addGameObject(t.createInstance(this));
            state = State.OCCUPIED;
            //Notify other SpawnObjects that this object is occupied
            sendEvent(new SpawnEvent(State.OCCUPIED));
            //Remove from readyToSpawn structure
            removeReadyToSpawn();
        }
    }

    //Perform spawn by RandomDistribution
    public void spawn(){
        RandomDistribution<ActorObject.ActorType>.Node node = actorDistribution.get();
        if(node != null)spawn(node.data);
    }

    public void onUpdate() {
        if(state == State.FREE) {
            nextSpawnTimer = nextSpawnTimer - this.sceneManager.frame_time_s;
            if(nextSpawnTimer <= 0){
                state = State.READY;
                //Add this object to readyToSpawn structure
                addReadyToSpawn();
            }
        }
    }

    public void render () { }
    public void dispose () {
        super.dispose();
    }

    public void addSpawnPoint(Vector3... p){
        for (int i = 0; i < p.length; i++) {
            spawnPoints.add(p[i]);
        }
    }

    public void removeActorType(ActorObject.ActorType type){
        actorDistribution.remove(type);
    }

    public void addActorType(ActorObject.ActorType type, float weight){
        actorDistribution.add(type, weight);
    }

    public void addActorType(ActorObject.ActorType... type){
        for (int i = 0; i < type.length; i++) {
            actorDistribution.add(type[i], 1.0f);
        }
    }

    public void addAffectedDoor(String... name){
        for (int i = 0; i < name.length; i++) {
            affectedDoors.add(name[i]);
        }
    }

    public void setAffectedDoorsState(DoorObject.State doorState){
        for (int i=0; i<affectedDoors.size; i++){
            DoorObject d = (DoorObject)sceneManager.getObjectByName(affectedDoors.get(i));
            if(d != null){
                d.setState(doorState);
            }
        }
    }

    // Spawn points activate only when others are not blocked
    // Spawn group allows for multiple simultaneous spawns to occur
    public void addSpawnGroup(String... name){
        for (int i = 0; i < name.length; i++) {
            spawnGroup.add(name[i]);
        }
    }
    private Array<String> spawnGroup = new Array<String>();

    // Ready to spawn objects are all active spawn points, activated randomly
    static private Array<SpawnObject> readyToSpawnObjects = new Array<SpawnObject>();

    //Try to spawn new objects
    //Static method called outside object update loop
    public static void updateAndSpawn(){
        if(readyToSpawnObjects.size > 0) {
            int r = 0;
            for (int i = 0; i < 10; i++) {
                r = random.nextInt(readyToSpawnObjects.size);
                SpawnObject sp = readyToSpawnObjects.get(r);
                if (sp.tryToSpawn()) {
                    readyToSpawnObjects.removeValue(sp, true);
                    break;
                }
            }
        }
    }

    public static void resetReadyToSpawn(){
        readyToSpawnObjects.clear();
    }

    public static void freeReadyToSpawnObjects(){
        if(readyToSpawnObjects.size > 0) {
            for (int i = 0; i < readyToSpawnObjects.size; i++) {
                SpawnObject sp = readyToSpawnObjects.get(i);
                sp.setFree();
            }
        }
        resetReadyToSpawn();
    }

    private void addReadyToSpawn() {
        readyToSpawnObjects.add(this);
    }
    private void removeReadyToSpawn(){
        readyToSpawnObjects.removeValue(this, true);
    }

    private Array<SpawnObject> blockingSpawnObjects = new Array<SpawnObject>();

    public void onSpawnEvent(SpawnEvent e){

        if(e.action != null && e.actorType != null) {
            switch (e.action) {
                case ADD_ACTOR:
                    addActorType(e.actorType, e.actorWeight);
                    break;
                case REMOVE_ACTOR:
                    removeActorType(e.actorType);
                    break;
                case SET_ACTOR_WEIGHT:

                    break;
            }
        }else if(e.state != null) {
            if (e.senderObject != null && e.senderObject.getClass() == SpawnObject.class) {
                if (e.state == State.FREE) {
                    blockingSpawnObjects.removeValue((SpawnObject) e.senderObject, true);
                } else {
                    //Error.log(getName()+" contains "+e.senderObject.getName()+" = "+spawnGroup.contains(e.senderObject.getName(), false));
                    if (!spawnGroup.contains(e.senderObject.getName(), false))
                        blockingSpawnObjects.add((SpawnObject) e.senderObject);
                }
            }
        }
    }

    //Tries to activate based on group conditions
    private boolean tryToSpawn(){
        if(blockingSpawnObjects.size == 0){
            spawn();
            return true;
        }
        return false;
    }



}
