package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Timer;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

import GameEvents.GameStateEvent;
import Utils.Error;

/**
 * Created by KristsPudzens on 05.10.2017.
 */

public class GameObjectiveTimerObject extends GameObject {


    public float objectiveTime = 120;
    public float objectiveAdvanceTime = 20;
    private Timer.Task objectiveUpdateTimer = null;
    private Timer.Task objectiveAdvanceTimer = null;

    public GameObjectiveTimerObject(float t, float at){
        this.collide = false;
        objectiveTime = t;
        objectiveAdvanceTime = at;
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

    }

    public void onInit(){
        //Start timer update every 1 second
        objectiveUpdateTimer = Timer.schedule(new Timer.Task() {
            float timer = 0;
            @Override
            public void run() {
                if(!checkUpdateMissionTimer(timer))cancel();
                timer++;
            }
        }, 0,1);

        //Start timer update for each difficulty advancement
        objectiveAdvanceTimer = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if(!getScene().advanceDifficultyLevel())cancel();
                else objectiveAdvanceTimer = Timer.schedule(this, objectiveAdvanceTime);
            }
        }, objectiveAdvanceTime);

        //Set difficultu level = 0
        getScene().advanceDifficultyLevel();
    }

    private boolean checkUpdateMissionTimer(float secondsElapsed){
        if(secondsElapsed <= objectiveTime) {
            float dt = objectiveTime - secondsElapsed;
            int minutes = ((int) dt % 3600) / 60;
            int seconds = (int) dt % 60;
            getScene().getUI().missionLabel.setText("LEFT: " + String.format("%02d:%02d", minutes, seconds));
            return true;
        }else{
            getScene().setSceneFinish();
            return false;
        }
    }

    public void onUpdate() {    }

    public void dispose () {
        super.dispose();
        if(objectiveUpdateTimer != null)objectiveUpdateTimer.cancel();
        if(objectiveAdvanceTimer != null)objectiveAdvanceTimer.cancel();
    }

    public void onGameStateEvent(GameStateEvent e){
        switch (e.state){
            case GAME_PAUSED:
                if(objectiveUpdateTimer != null)objectiveUpdateTimer.cancel();
                if(objectiveAdvanceTimer != null)objectiveAdvanceTimer.cancel();
                break;
            case GAME_RESUME:
                if(objectiveUpdateTimer != null){
                    //Start timer update every 1 second
                    objectiveUpdateTimer = Timer.schedule(objectiveUpdateTimer, 1,1,(int)objectiveTime);
                }
                if(objectiveAdvanceTimer != null){
                    //Start timer update for each difficulty advancement
                    objectiveAdvanceTimer = Timer.schedule(objectiveAdvanceTimer, objectiveAdvanceTime);
                }
                break;
        }
    }
}
