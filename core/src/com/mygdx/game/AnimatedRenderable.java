package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;

import Utils.Error;

/**
 * Created by T510 on 8/6/2017.
 */

public class AnimatedRenderable implements AnimationController.AnimationListener{

    public GameObject gameObject;

    public String modelName = "";

    public ModelBatch modelBatch = null;
    public ModelInstance modelInstance = null;
    private AnimationController controller = null;
    //private Texture texture = null;

    public AnimatedRenderable(GameObject o){
        gameObject = o;
    }
    public AnimatedRenderable(GameObject o, String filename){
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
            controller = new AnimationController(modelInstance);
            /*
            for (int i = 0; i < modelInstance.materials.size; i++) {
                Error.log("Mat: "+i+" - "+modelInstance.materials.get(i).id);
            }*/

        }else{
            System.out.println("AnimatedRenderable:init asset not loaded "+modelName);
        }
        //PlayAnim();
    }

    public void translate(Vector3 pos){
        if(modelInstance != null) {
            modelInstance.transform.idt();
            modelInstance.transform.translate(pos);
        }
    }

    public void PlayAnim(String name){

        if(controller != null)controller.setAnimation(name,-1, 1, this);

    }

    public void PlayAnim(String name, float speed, float transition){

        if(controller != null)controller.animate(name, 1, speed, this, transition);

    }

    public void StopAnim(){

        controller.setAnimation(null);

    }

    public void setColor(float r, float g, float b){
        if(modelInstance != null)modelInstance.materials.get(0).set(ColorAttribute.createDiffuse(r,g,b,1));
    }

    public void render(PerspectiveCamera cam, Environment env){
        controller.update(Gdx.graphics.getDeltaTime());
        modelBatch.begin(cam);
        if(modelInstance != null)modelBatch.render(modelInstance, env);
        else System.out.println("Renderable:render instance not created "+modelName);
        modelBatch.end();
    }

    public void setMaterialOpacity(String materialName, float opacity){
        for (int i = 0; i < modelInstance.materials.size; i++) {
            Material m = modelInstance.materials.get(i);
            if(m.id.toString().compareTo(materialName.toString()) == 0) {
                BlendingAttribute blendingAttribute = new BlendingAttribute(GL20.GL_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                blendingAttribute.opacity = opacity;
                m.set(blendingAttribute);
                break;
            }
        }
    }

    public void dispose(){
        modelBatch.dispose();
    }


    @Override
    public void onEnd(AnimationController.AnimationDesc animation) {   }

    @Override
    public void onLoop(AnimationController.AnimationDesc animation) {   }
}
