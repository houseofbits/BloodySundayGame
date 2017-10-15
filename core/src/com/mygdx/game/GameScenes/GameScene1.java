package com.mygdx.game.GameScenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameObjects.ActorObject;
import GameObjects.DoorObject;
import GameObjects.GameObjectiveTimerObject;
import GameObjects.PlayerObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;
import Utils.Error;
import Utils.RandomDistributionActors;

/**
 * Created by KristsPudzens on 07.08.2017.
 */

public class GameScene1 extends Scene {

    //public CameraInputController camController;
    SpawnObject spawnObject = null;

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
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        addGameObject(new DoorObject("door_1", new Vector3(-1.188f,0.011f,0.045f), "door2.g3dj", false));
        addGameObject(new DoorObject("door_2", new Vector3(0.443f,0.011f,0.045f), "door2.g3dj", false));
        addGameObject(new DoorObject("door_3", new Vector3(2.048f,0.011f,0.045f), "door2.g3dj", false));

        spawnObject = new SpawnObject("spawn");
        addGameObject(spawnObject);
        spawnObject.addSpawnPoint("sp1")
                .addDoors("door_1")
                .addPosition(new Vector3(-1.8f,0,-0.6f), new Vector3(-1.9f,0,-0.6f), new Vector3(-1.7f,0,-0.6f));

        spawnObject.addSpawnPoint("sp2")
                .addDoors("door_2")
                .addPosition(new Vector3(0,0,-0.6f), new Vector3(0.1f,0,-0.6f), new Vector3(-0.1f,0,-0.6f));

        spawnObject.addSpawnPoint("sp3")
                .addDoors("door_3")
                .addPosition(new Vector3(1.8f,0,-0.6f), new Vector3(1.9f,0,-0.6f), new Vector3(1.7f,0,-0.6f));

        spawnObject.setDifficultyLevel(0.9f);

        addGameObject(new StaticObject("scene.g3dj"));
        addGameObject(new PlayerObject("gun.g3dj"));
        addGameObject(new GameObjectiveTimerObject(120, 10));

        getActorDist()
                .addActorType(ActorObject.ActorType.NPC_1, new float[]      {1.0f, 0.5f, 0.0f})
                .addActorType(ActorObject.ActorType.NPC_2, new float[]      {1.0f, 0.5f, 0.0f})
                .addActorType(ActorObject.ActorType.NPC_3, new float[]      {1.0f, 0.5f, 0.0f})
                .addActorType(ActorObject.ActorType.ENEMY_1, new float[]    {0.0f, 0.5f, 1.0f})
                .addActorType(ActorObject.ActorType.ENEMY_2, new float[]    {0.0f, 0.5f, 1.0f})
                .addActorType(ActorObject.ActorType.ENEMY_4, new float[]    {0.0f, 0.5f, 2.0f})
                .setDifficultyLevel(0.0f);

//        for (int i = 0; i < 20; i++) {
//            RandomDistributionActors.Node n = getActorDist().get();
//            getActorDist().saveDebugLine(n);
//        }
//        getActorDist().saveDebugFile("actordstr.html");

        setNextGameScene(GameScene2.class);

    }

    public void onUpdate(){
        super.onUpdate();
        //if(camController != null)camController.update();
    }
    public boolean advanceDifficultyLevel(){

        switch(difficultyLevel){
            case 0:
                Error.log("advance 0");
                getActorDist().setDifficultyLevel(0);
                spawnObject.setDifficultyLevel(0);
                break;
            case 1:
                Error.log("advance 1");
                getActorDist().setDifficultyLevel(0.2f);
                spawnObject.setDifficultyLevel(0.2f);
                break;
            case 2:
                Error.log("advance 2");
                getActorDist().setDifficultyLevel(0.4f);
                spawnObject.setDifficultyLevel(0.4f);
                break;
            case 3:
                Error.log("advance 3");
                getActorDist().setDifficultyLevel(0.6f);
                spawnObject.setDifficultyLevel(0.6f);
                break;
            case 4:
                Error.log("advance 4");
                getActorDist().setDifficultyLevel(0.8f);
                spawnObject.setDifficultyLevel(0.8f);
                break;
            case 6:
                Error.log("advance 5");
                getActorDist().setDifficultyLevel(1.0f);
                spawnObject.setDifficultyLevel(1.0f);
                break;

            case 9:
                Error.log("ending 1");
                getActorDist().setDifficultyLevel(0.7f);
                spawnObject.setDifficultyLevel(0.7f);
                break;
            case 10:
                Error.log("ending 2");
                getActorDist().setDifficultyLevel(0.5f);
                spawnObject.setDifficultyLevel(0.5f);
                break;
        };

        difficultyLevel++;
        return true;
    }
    public void onDispose(){
        super.onDispose();
    }
}
