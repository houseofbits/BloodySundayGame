package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;

/**
 * Created by T510 on 8/6/2017.
 */

public class Renderable {

    private GameObject gameObject;

    private String modelName = "";

    public ModelBatch modelBatch = null;
    public ModelInstance modelInstance = null;
    //private Texture texture = null;

    public Renderable(GameObject o){
        gameObject = o;
    }
    public Renderable(GameObject o, String filename){
        gameObject = o;
        modelName = filename;
    }

    public void create(String m){
        modelName = m;
        gameObject.sceneManager.assetsManager.load(modelName, Model.class);
    }

    public void create(){
        gameObject.sceneManager.assetsManager.load(modelName, Model.class);
    }

    public void init(){

        modelBatch = new ModelBatch();

        if(gameObject.sceneManager.assetsManager.isLoaded(modelName)) {
            Model model = gameObject.sceneManager.assetsManager.get(modelName, Model.class);
            modelInstance = new ModelInstance(model);
        }
    }

    public void translate(Vector3 pos){
        if(modelInstance != null) {
            modelInstance.transform.idt();
            modelInstance.transform.translate(pos);
        }
    }

    public void render(PerspectiveCamera cam, Environment env){
        modelBatch.begin(cam);
        if(modelInstance != null)modelBatch.render(modelInstance, env);
        modelBatch.end();
    }

    public void dispose(){
        modelBatch.dispose();
    }

}
