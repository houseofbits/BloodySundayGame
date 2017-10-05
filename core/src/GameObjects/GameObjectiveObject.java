package GameObjects;

import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

/**
 * Created by KristsPudzens on 05.10.2017.
 */

public class GameObjectiveObject extends GameObject {

    public float missionTime = 120;

    public GameObjectiveObject(){
        this.collide = false;

    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

    }

    public void onInit(){

    }

    public void onUpdate() {

        missionTime -= sceneManager.frame_time_s;

        if(missionTime > 0) {

            int minutes = ((int) missionTime % 3600) / 60;
            int seconds = (int) missionTime % 60;

            sceneManager.getScene().getUI().missionLabel.setText("LEFT: " + String.format("%02d:%02d", minutes, seconds));
        }
        if(missionTime <= 0)sceneManager.getScene().setSceneFinish();
    }

    public void dispose () {
        super.dispose();
    }
}
