package GameObjects;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

import java.util.Random;

import GameEvents.ActorEvent;
import GameEvents.SpawnEvent;
import Utils.RandomDistribution;

/**
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public enum State {
        FREE,                   //waiting to spawn object
        OCCUPIED,               //occupied by spawned object
    }

    public class SpawnPoint{
        private Vector3 point = new Vector3();
        private Array<Vector3> points = new Array<Vector3>();
        private State state = State.FREE;
        private Array<String> affectedDoors = new Array<String>();
        private String groupName = "";
        private String blockingGroup = "";
        private Array<SpawnPoint> blocking = new Array<SpawnPoint>();
        private GameObject parent = null;

        private SpawnPoint(GameObject go){
            parent = go;
        }
        private SceneManager getSceneManager(){
            return parent.getSceneManager();
        }
        public SpawnPoint addDoors(String... doors){
            for (int i = 0; i < doors.length; i++) {
                affectedDoors.add(doors[i]);
            }
            return this;
        }
        public SpawnPoint addPosition(Vector3... p){
            for (int i = 0; i <p.length ; i++) {
                points.add(p[i].cpy());
            }
            return this;
        }
        public SpawnPoint setGroupName(String name){
            groupName = name;
            return this;
        }
        public SpawnPoint setBlockingGroup(String name){
            blockingGroup = name;
            return this;
        }
        public boolean isFreeToSpawn(){
            if(blocking.size > 0){
                for (int i = 0; i < blocking.size; i++) {
                    if(blocking.get(i).state != State.FREE)return false;
                }
            }
            return true;
        }
        public void setFree(){
            state = State.FREE;
        }

        public Vector3 getSpawnedPosition(){
            return point;
        }
        public void setAffectedDoorsState(DoorObject.State doorState){
            for (int i=0; i<affectedDoors.size; i++){
                DoorObject d = (DoorObject)getSceneManager().getObjectByName(affectedDoors.get(i));
                if(d != null){
                    d.setState(doorState);
                }
            }
        }
    }

    private Array<SpawnPoint> spawnPoints = new Array<SpawnPoint>();
    private Timer.Task nextSpawnTimerTask = null;
    private float timerMin = 0f;
    private float timerMax = 0.5f;
    private Random random = new Random();

    public SpawnObject(String name) {
        this.collide = false;
        this.setName(name);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

    }

    public SpawnPoint addSpawnPoint(){
        SpawnPoint sp = new SpawnPoint(this);
        spawnPoints.add(sp);
        return sp;
    }

    private void startNextSpawnTimer(){
        float t = timerMin + (random.nextFloat() * (timerMax - timerMin));
        nextSpawnTimerTask = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                //try tospawn
                for (int i = 0; i < 10; i++) {
                    int id = random.nextInt(spawnPoints.size);
                    SpawnPoint sp = spawnPoints.get(id);
                    if(sp.state == State.FREE && sp.isFreeToSpawn()){
                        //do spawn
                        int rip = random.nextInt(sp.points.size);
                        sp.point = sp.points.get(rip);
                        ActorObject.ActorType actor = getScene().getRandomActorType();
                        getSceneManager().addGameObject(actor.createInstance(sp));
                        sp.state = State.OCCUPIED;
                        break;
                    }
                }
                startNextSpawnTimer();
            }
        }, t);
    }

    private void updateGroups(){
        for (int i = 0; i < spawnPoints.size; i++) {
            SpawnPoint sp = spawnPoints.get(i);
            if(!sp.blockingGroup.isEmpty()){
                for (int j = 0; j < spawnPoints.size; j++) {
                    if(i != j){
                        if(spawnPoints.get(j).groupName == sp.blockingGroup){
                            sp.blocking.add(spawnPoints.get(j));
                        }
                    }
                }
            }
        }
    }

    public void onSpawnEvent(SpawnEvent e){
        switch(e.action){
            case SET_ENABLED:
                if(nextSpawnTimerTask != null)nextSpawnTimerTask.cancel();
                if(e.enabled){
                    startNextSpawnTimer();
                }
                break;
        }
    }

    public void onInit(){
        updateGroups();
        startNextSpawnTimer();
    }

    public void onUpdate() {}
    public void render () { }
    public void dispose () {
        super.dispose();
    }

}
