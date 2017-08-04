package com.mygdx.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.scenes.scene2d.EventListener;

import events.GameEvent;

/**
 * Created by T510 on 7/30/2017.
 */

public class GameObject implements events.EventListener {

    public SceneManager sceneManager = null;
    public String       name = null;

    public void init(SceneManager sceneManagerRef){

        sceneManager = sceneManagerRef;

        sceneManager.eventManager.registerListener(this);
    }

    public void sendEvent(GameEvent e){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, null);
        }
    }

    public void sendEvent(GameEvent e, String targetName){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, null);  //this.sceneManager.getObjectByName(targetName);
        }
    }

    public void sendEvent(GameEvent e, GameObject target){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, target);
        }
    }

    public void update() {

    }

    public void render () {



    }
    public void dispose () {

        sceneManager.eventManager.removeListener(this);

    }


}
