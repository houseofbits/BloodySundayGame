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

public class MultiSpawnObject extends GameObject {

    public enum State {
        FREE,                   //waiting to spawn object
        OCCUPIED,               //occupied by spawned object
        READY,                  //ready to spawn new object
    }

    class SpawnPoint{
        private Vector3 point;
        private State state;
        private Array<String> affectedDoors;

    }


    public MultiSpawnObject(String name) {
        this.collide = false;
        this.setName(name);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

    }

    public void onInit(){    }

    public void onUpdate() {}
    public void render () { }
    public void dispose () {
        super.dispose();
    }

}
