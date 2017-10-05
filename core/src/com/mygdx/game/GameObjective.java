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

    private GameObjective parent = null;
    private Array<GameObjective> relativeGameObjectives = new Array<GameObjective>();
    private float delayStart = 0;
    private boolean addDynamic = false;
    private boolean removeDynamic = false;
    private String message = "";
    private GameObjectiveState state = GameObjectiveState.WAITING;
    private GameObjectiveState globalState = GameObjectiveState.WAITING;

    ///Set delay time for objective to start
    public final GameObjective setDelayStart(float delay){
        delayStart = delay;
        return this;
    }
    ///Dynamic objectives are added or deleted in course of gameplay
    public final GameObjective setDynamic(boolean add, boolean remove){
        addDynamic = add;
        removeDynamic = remove;
        return this;
    }
    ///Set message to display when objective is started
    public final GameObjective setMessage(String msg){
        message = msg;
        return this;
    }
    public final GameObjective parent(){
        if(parent == null)return this;
        else return parent;
    }
    public final GameObjective addObjective(GameObjective o){
        o.parent = this;
        relativeGameObjectives.add(o);
        return o;
    }
    public final void setState(GameObjectiveState s){
        GameObjectiveState sp = state;
        state = s;
        onStateChange(sp, state);
        if(s == GameObjectiveState.COMPLETED){
            //Set children to PROCESS if WAITING
            setChildrenStateProcess();
        }
        if(s == GameObjectiveState.FAILED){
            setChildrenStateFailed();
        }
    }
    public GameObjectiveState getState(){
        return globalState;
    }
    private final void setChildrenStateFailed(){
        for (int i = 0; i < relativeGameObjectives.size; i++) {
            GameObjective g = relativeGameObjectives.get(i);
            g.state = GameObjectiveState.FAILED;
            g.setChildrenStateFailed();
        }
    }
    private final void setChildrenStateProcess(){
        for (int i = 0; i < relativeGameObjectives.size; i++) {
            GameObjective g = relativeGameObjectives.get(i);
            if(g.state == GameObjectiveState.WAITING)g.state = GameObjectiveState.PROCESS;
            g.setChildrenStateProcess();
        }
    }
    //Virtual function called when state changes occur
    public void onStateChange(GameObjectiveState previousState, GameObjectiveState newState){   }

    //Virtual function called in update loop to process objective rules
    public void onProcess(){

    }
    //Update parent states from objective tree
    private final GameObjectiveState recurseStates(){

        GameObjectiveState retState = GameObjectiveState.COMPLETED;

        if(relativeGameObjectives.size == 0){
            globalState = state;
            return state;
        }

        int waitingCnt = 0;
        int processCnt = 0;
        int failedCnt = 0;
        int completeCnt = 0;

        for (int i = 0; i < relativeGameObjectives.size; i++) {
            GameObjectiveState s = relativeGameObjectives.get(i).recurseStates();
            switch(s){
                case FAILED:
                    failedCnt++;
                    break;
                case WAITING:
                    waitingCnt++;
                    break;
                case COMPLETED:
                    completeCnt++;
                    break;
                case PROCESS:
                    processCnt++;
                    break;
            }
        }
        if(failedCnt>0){
            retState = GameObjectiveState.FAILED;
        }
        if(failedCnt == 0 && processCnt > 0){
            retState = GameObjectiveState.PROCESS;
        }
        if(completeCnt == relativeGameObjectives.size){
            retState = GameObjectiveState.COMPLETED;
        }
        if(waitingCnt == relativeGameObjectives.size){
            retState = GameObjectiveState.WAITING;
        }
        globalState = retState;
        return retState;
    }
    public final void process(){
        if(parent == null)recurseStates();
        onProcess();
        for (int i = 0; i < relativeGameObjectives.size; i++) {
            relativeGameObjectives.get(i).process();
        }
    }
}
