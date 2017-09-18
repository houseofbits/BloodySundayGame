package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GUI.GUIDevStage;
import GameObjects.AnimatedObject;
import GameObjects.DoorObject;
import GameObjects.PlayerObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene4 extends Scene {

    public CameraInputController camController;

    public GUIDevStage devStage;
    public AnimatedObject animatedObject;
    public String modelName = "character2.g3dj";
    public Array<Animation> animationsArray = new Array<Animation>();


    public GameScene4(){

    }

    public void onCreate(SceneManager mgr){

        super.onCreate(mgr);

        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-0.7f, 1.4f, 4f);
        cam.lookAt(0,1.1f,0);
        cam.near = 1f;
        cam.far = 500f;
        cam.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 0.7f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        sceneManager.AddGameObject(new StaticObject("testcharscene.g3dj"));

        animatedObject = new AnimatedObject(modelName);

        sceneManager.AddGameObject(animatedObject);

        sceneManager.assetsManager.load(modelName, Model.class);
        sceneManager.assetsManager.finishLoadingAsset(modelName);
        Model model = sceneManager.assetsManager.get(modelName, Model.class);
        for (int i = 0; i < model.animations.size; i++) {
            System.out.println("Anim: " + model.animations.get(i).id);
            animationsArray.add(model.animations.get(i));
        }

        devStage = new GUIDevStage(this);

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(new InputMultiplexer(devStage.getStage(), guiGameStage.getStage(), camController));

    }

    public void onUpdate(){
        if(camController != null)camController.update();
        devStage.render();
    }

    public void onDispose(){
        super.onDispose();
    }
}
