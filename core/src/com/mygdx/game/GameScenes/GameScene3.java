package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameObjects.DoorObject;
import GameObjects.PlayerObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene3 extends Scene {

    public CameraInputController camController;

    public GameScene3(){

    }

    public void onCreate(SceneManager mgr){

        super.onCreate(mgr);

        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-0.7f, 1.4f, 5f);
        cam.lookAt(0,1.1f,0);
        cam.near = 1f;
        cam.far = 500f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));  //, camController, , sceneManager.guiMainStage.getStage()

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 0.7f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        sceneManager.AddGameObject(new SpawnObject("spawn_2", "door_2", new Vector3(0.6f,0,-0.8f)));
        sceneManager.AddGameObject(new SpawnObject("spawn_1", "door_1", new Vector3(-0.4f,0,-0.6f)));

        sceneManager.AddGameObject(new DoorObject("door_2", new Vector3(0.91f,0,0), "door2.g3dj", false));
        sceneManager.AddGameObject(new DoorObject("door_1", new Vector3(-0.91f,0,0), "ldoor1.g3dj", true));

        //sceneManager.AddGameObject(new StaticObject("scene.g3dj"));

        sceneManager.AddGameObject(new PlayerObject("gun.g3dj"));

    }

    public void onUpdate(){
        if(camController != null)camController.update();
    }

    public void onDispose(){
        super.onDispose();
    }
}
