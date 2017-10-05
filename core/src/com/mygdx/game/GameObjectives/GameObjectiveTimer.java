package com.mygdx.game.GameObjectives;

import com.mygdx.game.Scene;

/**
 * Created by KristsPudzens on 05.10.2017.
 */

public class GameObjectiveTimer extends GameObjective {

    public Scene    scene;
    public float    timeLeft = 0;

    public GameObjectiveTimer(Scene s, float time){
        scene = s;
        timeLeft = time;
    }
    public void onStateChange(GameObjectiveState previousState, GameObjectiveState newState){
        if(newState == GameObjectiveState.PROCESS){
            //uiObjectiveTimer = scene.getUI().addObjectiveIcon(new UIObjectiveTimer());
            //scene.getUI().addMessage();
        }
    }
    public void onProcess(){
        //uiObjectiveTimer.advance();
    }
}
