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
import Utils.Error;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene1 extends Scene {

    //public CameraInputController camController;

    public GameScene1(){

    }

    public void onCreate(SceneManager mgr){

        super.onCreate(mgr);

        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-0.7f, 1.4f, 6f);
        cam.lookAt(0,1.1f,0);
        cam.near = 1f;
        cam.far = 500f;
        cam.update();

        //camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));  //, camController, , sceneManager.guiMainStage.getStage()

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 0.7f, 1f));
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


        sceneManager.AddGameObject(new DoorObject("door_1", new Vector3(-1.188f,0.011f,0.045f), "door2.g3dj", false));
        sceneManager.AddGameObject(new DoorObject("door_2", new Vector3(0.443f,0.011f,0.045f), "door2.g3dj", false));
        sceneManager.AddGameObject(new DoorObject("door_3", new Vector3(2.048f,0.011f,0.045f), "door2.g3dj", false));

        SpawnObject sp1 = new SpawnObject("spawn_1");
        sp1.addSpawnPoint(new Vector3(-1.9f,0,-0.6f));
        sp1.addSpawnPoint(new Vector3(-1.8f,0,-0.6f));
        sp1.addSpawnPoint(new Vector3(-1.7f,0,-0.6f));
        sp1.addAffectedDoor("door_1");
        sp1.addActorType(SpawnObject.ActorType.ENEMY1, 0.3f);
        sp1.addActorType(SpawnObject.ActorType.ENEMY2, 0.3f);
        sp1.addActorType(SpawnObject.ActorType.ENEMY3, 0.3f);
        sceneManager.AddGameObject(sp1);

        SpawnObject sp2 = new SpawnObject("spawn_2");
        sp2.addSpawnPoint(new Vector3(0.1f,0,-0.6f));
        sp2.addSpawnPoint(new Vector3(0,0,-0.6f));
        sp2.addSpawnPoint(new Vector3(-0.1f,0,-0.6f));
        sp2.addAffectedDoor("door_2");
        sp2.addActorType(SpawnObject.ActorType.ENEMY1, 0.3f);
        sp2.addActorType(SpawnObject.ActorType.ENEMY2, 0.3f);
        sp2.addActorType(SpawnObject.ActorType.ENEMY3, 0.3f);
        sceneManager.AddGameObject(sp2);

        SpawnObject sp3 = new SpawnObject("spawn_3");
        sp3.addSpawnPoint(new Vector3(1.9f,0,-0.6f));
        sp3.addSpawnPoint(new Vector3(1.8f,0,-0.6f));
        sp3.addSpawnPoint(new Vector3(1.7f,0,-0.6f));
        sp3.addAffectedDoor("door_3");
        sp3.addActorType(SpawnObject.ActorType.ENEMY1, 0.3f);
        sp3.addActorType(SpawnObject.ActorType.ENEMY2, 0.3f);
        sp3.addActorType(SpawnObject.ActorType.ENEMY3, 0.3f);
        sceneManager.AddGameObject(sp3);

        sceneManager.AddGameObject(new StaticObject("scene.g3dj"));

        sceneManager.AddGameObject(new PlayerObject("gun.g3dj"));

    }

    public void onUpdate(){
        //if(camController != null)camController.update();
    }

    public void onDispose(){
        super.onDispose();
    }
}
