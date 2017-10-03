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
import GameEvents.PlayerEvent;
import GameObjects.ActorObject;
import GameObjects.BulletSplashObject;
import GameObjects.SpawnObject;
import Utils.Error;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene  extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;
    public GUIGameStage guiGameStage = null;
    private Class nextGameSceneClass = null;

    public Scene(){

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

    public void onCreate(SceneManager mgr){
        sceneManager = mgr;
        guiGameStage = new GUIGameStage(this);

        Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));

        SpawnObject.resetReadyToSpawn();
    }

    public void onUpdate(){
        SpawnObject.updateAndSpawn();
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
