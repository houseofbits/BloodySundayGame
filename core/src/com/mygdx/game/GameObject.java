package com.mygdx.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.scenes.scene2d.EventListener;

/**
 * Created by T510 on 7/30/2017.
 */

public class GameObject implements events.EventListener {

    public SceneManager sceneManager;
    public String       name;

    public void init(SceneManager sceneManagerRef){

        sceneManager = sceneManagerRef;

        sceneManager.eventManager.registerListener(this);
    }

    public void update() {

    }

    public void render () {



    }
    public void dispose () {

        sceneManager.eventManager.removeListener(this);

    }


}
