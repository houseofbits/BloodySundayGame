package com.mygdx.game;

import com.badlogic.gdx.utils.Array;

/**
 * Created by T510 on 10/4/2017.
 */

public class GameObjective {

    public enum GameObjectiveState{
        WAITING,                    ///Waiting for objective to start
        PROCESS,                    ///Objective isin progress
        COMPLETED,                  ///Objective is completed
        FAILED,                     ///Objective has failed
    };

    public GameObjective parent = null;
    public Array<GameObjective> relativeGameObjectives = new Array<GameObjective>();
    public float delayStart = 0;
    public boolean addDynamic = false;
    public boolean removeDynamic = false;
    public String message = "";
    public GameObjectiveState state = GameObjectiveState.WAITING;

    ///Set delay time for objective to start
    public GameObjective setDelayStart(float delay){
        delayStart = delay;
        return this;
    }
    ///Dynamic objectives are added or deleted in course of gameplay
    public GameObjective setDynamic(boolean add, boolean remove){
        addDynamic = add;
        removeDynamic = remove;
        return this;
    }
    ///Set message to display when objective is started
    public GameObjective setMessage(String msg){
        message = msg;
        return this;
    }
    public GameObjective parent(){
        if(parent == null)return this;
        else return parent;
    }
    public GameObjective addObjective(GameObjective o){
        o.parent = this;
        relativeGameObjectives.add(o);
        return o;
    }

    //on state start
    // scene.gui.addObjectiveIcon();
    //on state finish
}
