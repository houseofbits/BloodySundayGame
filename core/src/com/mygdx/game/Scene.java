package com.mygdx.game;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import GameObjects.BulletSplashObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class Scene  extends InputAdapter {

    public SceneManager sceneManager = null;
    public Environment environment = null;
    public PerspectiveCamera cam = null;

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

        Ray ray = cam.getPickRay(screenX, screenY);

        //System.out.println("touch "+screenX+","+screenY);

        for(int i=0; i<sceneManager.gameObjectArray.size; i++) {
            GameObject o = sceneManager.gameObjectArray.get(i);

            Vector3 pt = new Vector3();
            if(o.intersectRay(ray, pt)){

                sceneManager.AddGameObject(new BulletSplashObject(pt));

            }
        }

        return false;   //return true stops event propagation
    }
}
