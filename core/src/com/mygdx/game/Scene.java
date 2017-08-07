package com.mygdx.game;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;

    public Scene(SceneManager mgr){
        sceneManager = mgr;
    }

    public void onCreate(){

    }

    public void onUpdate(){


    }

    public void onDispose(){

    }
}
