package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;
import GameEvents.SpawnEvent;
import GameObjects.ActorObject;
import GameObjects.DoorObject;
import GameObjects.GameObjectiveObject;
import GameObjects.PlayerObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;
import Utils.Error;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene3 extends Scene {

   // public CameraInputController camController;

    public GameScene3(){

    }

    public void onCreate(SceneManager mgr){

        super.onCreate(mgr);

        cam = new PerspectiveCamera(40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(-0.6f, 1.4f, 4f);
        cam.lookAt(0,1.1f,0);
        cam.near = 1f;
        cam.far = 500f;
        //cam.fieldOfView = 30;
        cam.update();

        //camController = new CameraInputController(cam);
        //Gdx.input.setInputProcessor(new InputMultiplexer(guiGameStage.getStage(), this));  //, camController, , sceneManager.guiMainStage.getStage()

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.6f, 0.6f, 0.5f,  0, -1f, -0.2f));

        sceneManager.addGameObject(new DoorObject("door_2", new Vector3(0.92f,0, -0.88f), "door2.g3dj", false));
        sceneManager.addGameObject(new DoorObject("door_1", new Vector3(-0.88f,0, -0.88f), "ldoor1.g3dj", true));

        SpawnObject sp1 = new SpawnObject("spawn_1");
        sp1.addSpawnPoint(new Vector3(-0.4f,0,-1.5f),
                new Vector3(-0.5f,0,-2.5f),
                new Vector3(-0.5f,0,-3.5f));
        sp1.addAffectedDoor("door_1");
        sp1.addSpawnGroup("spawn_2");
        sceneManager.addGameObject(sp1);

        SpawnObject sp2 = new SpawnObject("spawn_2");
        sp2.addSpawnPoint(new Vector3(0.6f,0,-1.5f),
                new Vector3(0.8f,0,-2.5f),
                new Vector3(0.9f,0,-3.5f));
        sp2.addAffectedDoor("door_2");
        sp2.addSpawnGroup("spawn_1");
        sceneManager.addGameObject(sp2);

        SpawnObject sp3 = new SpawnObject("spawn_3");
        sp3.addSpawnPoint(new Vector3(0f,0,-1.5f),
                new Vector3(0f,0,-5.5f),
                new Vector3(0f,0,-4.5f),
                new Vector3(0.5f,0,-4.5f),
                new Vector3(-0.5f,0,-4.5f),
                new Vector3(0f,0,-3.5f));
        sp3.addAffectedDoor("door_1", "door_2");
        sceneManager.addGameObject(sp3);

        sceneManager.addGameObject(new StaticObject("dev_scenes/scene1.g3dj"));
        sceneManager.addGameObject(new PlayerObject("gun.g3dj"));
        sceneManager.addGameObject(new GameObjectiveObject());

        setNextGameScene(GameScene1.class);
    }

    public void onUpdate(){
        super.onUpdate();
        //if(camController != null)camController.update();

    }

    public boolean advanceDifficultyLevel(){

        switch(difficultyLevel){
            case 0:
                addActorType(ActorObject.ActorType.NPC_1);
                addActorType(ActorObject.ActorType.NPC_2);
                addActorType(ActorObject.ActorType.NPC_3);
                addActorType(ActorObject.ActorType.ENEMY_1);
                break;
            case 1:
                addActorType(ActorObject.ActorType.ENEMY_2);
                addActorType(ActorObject.ActorType.ENEMY_3);
                break;
            case 2:
                removeActorType(ActorObject.ActorType.NPC_2);
                removeActorType(ActorObject.ActorType.NPC_3);
                break;
        };

        difficultyLevel++;
        return true;
    }

    public void onDispose(){
        super.onDispose();
    }
}
