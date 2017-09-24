package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import GUI.GUIGameStage;
import GameEvents.PlayerEvent;
import GameObjects.BulletSplashObject;
import GameObjects.SpawnObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene  extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;
    public GUIGameStage guiGameStage = null;

    public float    playerHealth = 100;

    public Scene(){

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
        sceneManager.traceRay(r,poi);

        //Send event to player
        sceneManager.sendEvent(new PlayerEvent(PlayerEvent.State.TOUCH_DOWN, r, poi));

        return true;
    }

    public float getPlayerHealth(){
        return playerHealth;
    }

    public float decreasePlayerHealth(float val){
        if(playerHealth <= 0){

            return 0;
        }
        playerHealth = playerHealth - val;
        return playerHealth;
    }

}
