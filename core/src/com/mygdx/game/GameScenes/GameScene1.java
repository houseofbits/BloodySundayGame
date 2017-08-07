package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameObjects.DoorObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene1 extends Scene {

    public CameraInputController camController;

    public GameScene1(SceneManager mgr){
        super(mgr);
    }

    public void onCreate(){

        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-0.7f, 1.3f, 6f);
        cam.lookAt(0,1.7f,0);
        cam.near = 1f;
        cam.far = 500f;
        cam.update();

        camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        sceneManager.AddGameObject(new SpawnObject("spawn_1", "door_1", new Vector3(-1.6f,0,-0.6f)));
        sceneManager.AddGameObject(new SpawnObject("spawn_2", "door_2", new Vector3(0,0,-0.6f)));
        sceneManager.AddGameObject(new SpawnObject("spawn_3", "door_3", new Vector3(1.7f,0,-0.6f)));

        sceneManager.AddGameObject(new DoorObject("door_1", new Vector3(-1.5f,0,0)));
        sceneManager.AddGameObject(new DoorObject("door_2", new Vector3(0,0,0)));
        sceneManager.AddGameObject(new DoorObject("door_3", new Vector3(1.5f,0,0)));

        sceneManager.AddGameObject(new StaticObject(new Vector3(0,-0.05f,0), new Vector3(15,0.1f,15), new Color(0.4f,0.4f,0.4f,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(0,2.8f,0), new Vector3(10,0.1f,10), new Color(1,1,1,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(-2.55f,1.5f,2), new Vector3(0.1f, 3, 5), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(2.55f,1.5f,2), new Vector3(0.1f, 3, 5), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(0,2.6f,0.05f), new Vector3(5, 0.8f, 0.1f), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(-0.75f,1.1f,0.05f), new Vector3(0.6f, 2.2f, 0.1f), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(0.75f,1.1f,0.05f), new Vector3(0.6f, 2.2f, 0.1f), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(-2.225f,1.1f,0.05f), new Vector3(0.55f, 2.2f, 0.1f), new Color(0,0.5f,0,0)));
        sceneManager.AddGameObject(new StaticObject(new Vector3(2.225f,1.1f,0.05f), new Vector3(0.55f, 2.2f, 0.1f), new Color(0,0.5f,0,0)));

    }

    public void onUpdate(){

        if(camController != null)camController.update();

    }

    public void onDispose(){

    }
}
