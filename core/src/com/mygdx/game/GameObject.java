package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import events.GameEvent;

/**
 * Created by T510 on 7/30/2017.
 */

public class GameObject implements events.EventListener {

    public SceneManager sceneManager = null;
    private String       name = "";
    private boolean      dispose = false;
    public boolean     receive_hits = false;

    public void onCreate(SceneManager sceneManagerRef){
        sceneManager = sceneManagerRef;
        sceneManager.eventManager.registerListener(this);
    }

    public void onInit(){   }

    public void setDispose(boolean d){ this.dispose = d; }
    public boolean isDisposable(){ return dispose; }
    public void setName(String n){ name = n; }
    public String getName(){
        return name;
    }

    public void sendEvent(GameEvent e){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, null);
        }
    }

    public void sendEvent(GameEvent e, String targetName){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, this.sceneManager.getObjectByName(targetName));
        }
    }

    public void sendEvent(GameEvent e, GameObject target){
        if(this.sceneManager != null){
            this.sceneManager.eventManager.addEvent(this, e, target);
        }
    }

    public void onUpdate() {  }

    public void render () { }
    public void dispose () {
        sceneManager.eventManager.removeListener(this);
    }


    public boolean intersectRay(Ray ray, Vector3 inter){



        return false;
    }

    public void onIntersection(Vector3 point){


    }
}
