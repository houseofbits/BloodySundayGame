package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameScenes.GameScene1;
import com.mygdx.game.GameScenes.GameScene3;

import java.lang.reflect.Constructor;

import GUI.GUIGameStage;
import GameEvents.DoorEvent;
import GameEvents.PlayerEvent;
import GameEvents.SpawnEvent;
import GameObjects.ActorObject;
import GameObjects.BulletSplashObject;
import GameObjects.DoorObject;
import GameObjects.SpawnObject;
import Utils.Error;
import Utils.RandomDistribution;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;
    public GUIGameStage guiGameStage = null;
    private Class nextGameSceneClass = null;
    protected RandomDistribution<ActorObject.ActorType> actorDistribution = new RandomDistribution<ActorObject.ActorType>();

    private float sceneCompleteTimer = 7;
    private int sceneCompleteCounter = 0;

    public Scene(){ }

    public SceneManager getSceneManager(){  return sceneManager; }

    public GUIGameStage getUI(){
        return guiGameStage;
    }

    public void setNextGameScene(Class gc){
        nextGameSceneClass = gc;
    }

    public Scene getNextGameSceneInstance(){
        try {
            Constructor<?> ctor = nextGameSceneClass.getConstructor();
            return (Scene)ctor.newInstance();
        }catch (Exception e){
            Error.log(e.getMessage());
        }
        return null;
    }

    public ActorObject.ActorType getRandomActorType(){
        RandomDistribution<ActorObject.ActorType>.Node node = actorDistribution.get();
        if(node != null)return node.data;
        return null;
    }
    public void addActorType(ActorObject.ActorType type, float weight){
        actorDistribution.add(type, weight);
    }
    public void addActorType(ActorObject.ActorType... type){
        for (int i = 0; i < type.length; i++) {
            actorDistribution.add(type[i], 1.0f);
        }
    }
    public void removeActorType(ActorObject.ActorType type){
        actorDistribution.remove(type);
    }
    //Set weight by percentage of total weight
    public void setActorWeight(ActorObject.ActorType type, float weight){actorDistribution.setFraction(type, weight);}
    public void setActorWeightAbsolute(ActorObject.ActorType type, float weight){actorDistribution.set(type, weight);}

    public void setSceneFinish(){
        if(sceneCompleteCounter == 0)sceneCompleteCounter = 5;
    }

    public void onCreate(SceneManager mgr){
        sceneManager = mgr;
        guiGameStage = new GUIGameStage(this);

        Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));

        SpawnObject.resetReadyToSpawn();
    }

    public void onUpdate(){

        SpawnObject.updateAndSpawn();

        if(sceneCompleteCounter > 0 && sceneCompleteTimer > 0) {

            sceneCompleteTimer -= sceneManager.frame_time_s;

            if (sceneCompleteTimer <= 6 && sceneCompleteCounter > 4) {
                sceneManager.sendEvent(new SpawnEvent(SpawnEvent.Action.SET_ENABLED, false));
                sceneCompleteCounter = 4;
            }
            if (sceneCompleteTimer <= 1 && sceneCompleteCounter > 3) {
                sceneManager.sendEvent(new DoorEvent(DoorEvent.Action.SET_STATE, DoorObject.State.OPEN));
                sceneCompleteCounter = 3;
            }
            if (sceneCompleteTimer <= 0 && sceneCompleteCounter > 2) {
                guiGameStage.ShowGameWonPopup();
                sceneCompleteCounter = 2;
            }
        }
    }

    public void onDispose(){

        guiGameStage.dispose();

    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {

        //Get pick ray
        Ray r = cam.getPickRay(screenX, screenY);

        //Get point of interest
        Vector3 poi = new Vector3();
        r.getEndPoint(poi, 20); //end point if does not hit anything
        GameObject go = sceneManager.traceRay(r,poi);

        //Send event to player
        sceneManager.sendEvent(new PlayerEvent(PlayerEvent.State.TOUCH_DOWN, r, poi, go));

        return true;
    }
}
