package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import java.lang.reflect.Constructor;

import GUI.GUIGameStage;
import GameEvents.DoorEvent;
import GameEvents.PlayerEvent;
import GameEvents.SpawnEvent;
import GameObjects.ActorObject;
import GameObjects.DoorObject;
import Utils.Error;
import Utils.RandomDistribution;
import Utils.RandomDistributionActors;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;
    public GUIGameStage guiGameStage = null;
    private Class nextGameSceneClass = null;
    protected RandomDistributionActors actorDistribution = null;

    protected int difficultyLevel = 0;
    private float sceneCompleteTimer = 7;
    private int sceneCompleteCounter = 0;

    public Scene(){ }

    public SceneManager getSceneManager(){  return sceneManager; }

    public GUIGameStage getUI(){
        return guiGameStage;
    }

    public RandomDistributionActors getActorDist(){
        return actorDistribution;
    }

    public <T extends GameObject> T addGameObject(T object) {
        return sceneManager.addGameObject(object);
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
    //Called from GameObjective
    public boolean advanceDifficultyLevel(){
        difficultyLevel++;
        return true;
    }

    public void setSceneFinish(){
        if(sceneCompleteCounter == 0)sceneCompleteCounter = 5;
    }

    public void onCreate(SceneManager mgr){
        sceneManager = mgr;
        actorDistribution = new RandomDistributionActors(mgr);
        guiGameStage = new GUIGameStage(this);

        Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));

        //Preload some stuff
        getSceneManager().assetsManager.load("test_actor.g3dj", Model.class);
//        getSceneManager().assetsManager.load(ActorObject.ActorType.ENEMY_POLICE.modelName, Model.class);
//        getSceneManager().assetsManager.load(ActorObject.ActorType.NPC_DOCTOR.modelName, Model.class);

        getActorDist().addActorType(ActorObject.ActorType.ENEMY_POLICE, 0);
        getActorDist().addActorType(ActorObject.ActorType.NPC_DOCTOR, 0);
    }

    public void onUpdate(){

        if(sceneCompleteCounter > 0 && sceneCompleteTimer > 0) {

            //TODO: Consider using Timer
            sceneCompleteTimer -= Gdx.graphics.getDeltaTime();

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
