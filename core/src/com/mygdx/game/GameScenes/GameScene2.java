package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameObjects.ActorObject;
import GameObjects.DoorObject;
import GameObjects.GameObjectiveTimerObject;
import GameObjects.PlayerObject;

/**
 * Created by T510 on 8/8/2017.
 */

public class GameScene2 extends Scene {

   // public CameraInputController camController;

    public GameScene2(){

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
        //Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this)); //camController, sceneManager.guiMainStage.getStage()

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1, 1, 0.7f, 1f));
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        environment.add(new PointLight().set(0.8f, 0.8f, 0.8f,  -2, 5, 0, 8.0f));
        environment.add(new PointLight().set(0.8f, 0.8f, 1.0f,  3, 5, 0, 2.0f));

//        SpawnObjectOld s1 = (SpawnObjectOld) sceneManager.addGameObject(new SpawnObjectOld("spawn_1", "door_1", new Vector3(-2.4f,0,-0.6f)));
//        s1.addSpawnGroup("spawn_4");
//
//        SpawnObjectOld s2 = (SpawnObjectOld) sceneManager.addGameObject(new SpawnObjectOld("spawn_2", "door_2", new Vector3(-0.9f,0,-5.6f)));
//        s2.addSpawnGroup("spawn_3");
//
//        SpawnObjectOld s3 = (SpawnObjectOld) sceneManager.addGameObject(new SpawnObjectOld("spawn_3", "door_3", new Vector3(0.8f,0,-5.6f)));
//        s3.addSpawnGroup("spawn_2");
//
//        SpawnObjectOld s4 = (SpawnObjectOld) sceneManager.addGameObject(new SpawnObjectOld("spawn_4", "door_4", new Vector3(2.4f,0,-0.6f)));
//        s4.addSpawnGroup("spawn_1");

        sceneManager.addGameObject(new DoorObject("door_1", new Vector3(-1.6f,0.011f,0.045f), "door2.g3dj", false));
        sceneManager.addGameObject(new DoorObject("door_2", new Vector3(-0.3f,0.011f,-5), "door2.g3dj", false));
        sceneManager.addGameObject(new DoorObject("door_3", new Vector3(1.2f,0.011f,-5), "door2.g3dj", false));
        sceneManager.addGameObject(new DoorObject("door_4", new Vector3(2.7f,0.011f,0.045f), "door2.g3dj", false));

        sceneManager.addGameObject(new PlayerObject("gun.g3dj"));

        addActorType(ActorObject.ActorType.ENEMY_1,
                    ActorObject.ActorType.ENEMY_2,
                    ActorObject.ActorType.ENEMY_3,
                    ActorObject.ActorType.NPC_1,
                    ActorObject.ActorType.NPC_2,
                    ActorObject.ActorType.NPC_3);

        sceneManager.addGameObject(new GameObjectiveTimerObject());


        setNextGameScene(GameScene3.class);
    }

    public void onUpdate(){
        super.onUpdate();
       // if(camController != null)camController.update();

    }

    public void onDispose(){
        super.onDispose();
    }
}
