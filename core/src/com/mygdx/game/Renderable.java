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

    public ModelBatch modelBatch = null;
    public Model model = null;
    public ModelInstance instance = null;
    //private Texture texture = null;


    public Renderable(){

    }

    public void init(String modelName){

        modelBatch = new ModelBatch();
        ModelBuilder modelBuilder = new ModelBuilder();
        ModelLoader loader = new G3dModelLoader(new JsonReader());          //new UBJsonReader() for binary
        model = loader.loadModel(Gdx.files.internal(modelName));

        instance = new ModelInstance(model);

        //texture = new Texture(Gdx.files.internal("badlogic.jpg"));

    }

    public void translate(Vector3 pos){
        instance.transform.idt();
        instance.transform.translate(pos);
    }

    public void render(PerspectiveCamera cam, Environment env){
        modelBatch.begin(cam);
        modelBatch.render(instance, env);
        modelBatch.end();
    }

    public void dispose(){
        modelBatch.dispose();
        model.dispose();
    }

}
