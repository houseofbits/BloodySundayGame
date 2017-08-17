package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import GameEvents.PlayerEvent;
import GameObjects.BulletSplashObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene  extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;

    public float    playerHealth = 100;

    public Scene(){

    }

    public void onCreate(SceneManager mgr){
        sceneManager = mgr;
    }

    public void onUpdate(){


    }

    public void onDispose(){

    }

    @Override
    public boolean touchDown (int screenX, int screenY, int pointer, int button) {

        sceneManager.sendEvent(new PlayerEvent(PlayerEvent.State.FIRE, cam.getPickRay(screenX, screenY)));

        return false;
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
