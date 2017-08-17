package GameObjects;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.ActorActionEvent;

/**
 * Created by T510 on 7/30/2017.
 */

public class PlayerObject extends GameObject {

    //Renderable renderable = null; //player hand+gun model

    public PlayerObject(String modelName){
        this.receive_hits = false;
//        renderable = new Renderable(this, modelName);
    }
    public PlayerObject(String modelName, String intName){
        this.receive_hits = true;
//        renderable = new Renderable(this, modelName);
    }

    public void onActorActionEvent(ActorActionEvent e){

        switch(e.state){
            case SHOOT:
                sceneManager.scene.decreasePlayerHealth(10);
                //invoke PlayerShot animation
                break;
        }
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
  //      renderable.create();
    }

    public void onInit(){
//        renderable.init();
    }

    public void render () {
//        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
 //       renderable.dispose();
    }
}
