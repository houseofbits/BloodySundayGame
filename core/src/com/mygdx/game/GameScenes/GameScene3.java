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
        cam.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.6f, 0.6f, 0.5f,  0, -1f, -0.2f));

        addGameObject(new DoorObject("door_2", new Vector3(0.92f,0, -0.88f), "door2.g3dj", false));
        addGameObject(new DoorObject("door_1", new Vector3(-0.88f,0, -0.88f), "ldoor1.g3dj", true));

        SpawnObject sp = new SpawnObject("spawn");
        addGameObject(sp);
        sp.addSpawnPoint()
                .addDoors("door_1")
                .addPosition(new Vector3(-0.4f,0,-1.5f), new Vector3(-0.5f,0,-2.5f), new Vector3(-0.5f,0,-3.5f));
        sp.addSpawnPoint()
                .addDoors("door_2")
                .addPosition(new Vector3(0.6f,0,-1.5f), new Vector3(0.8f,0,-2.5f), new Vector3(0.9f,0,-3.5f));

//        sp.addSpawnPoint()
//                .addDoors("door_1","door_2")
//                .addPosition(new Vector3(0f,0,-1.5f),
//                            new Vector3(0f,0,-5.5f),
//                            new Vector3(0f,0,-4.5f),
//                            new Vector3(0.5f,0,-4.5f),
//                            new Vector3(-0.5f,0,-4.5f),
//                            new Vector3(0f,0,-3.5f));
        sp.setDifficultyLevel(0.5f);

        addGameObject(new StaticObject("dev_scenes/scene1.g3dj"));
        addGameObject(new PlayerObject("gun.g3dj"));
        addGameObject(new GameObjectiveTimerObject(120, 20));

        getActorDist().addActorType(ActorObject.ActorType.NPC_1, new float[]{1.0f, 0.5f, 0.0f});
        getActorDist().addActorType(ActorObject.ActorType.NPC_2, new float[]{1.0f, 0.5f, 0.0f});
        getActorDist().addActorType(ActorObject.ActorType.NPC_3, new float[]{1.0f, 0.5f, 0.0f});
        getActorDist().addActorType(ActorObject.ActorType.ENEMY_1, new float[]{0.0f, 0.5f, 1.0f});
        getActorDist().addActorType(ActorObject.ActorType.ENEMY_2, new float[]{0.0f, 0.5f, 1.0f});
        getActorDist().addActorType(ActorObject.ActorType.ENEMY_3, new float[]{0.0f, 0.5f, 1.0f});

        getActorDist().setDifficultyLevel(0.5f);

        setNextGameScene(GameScene1.class);
    }

    public void onUpdate(){
        super.onUpdate();
        //if(camController != null)camController.update();

    }

    public boolean advanceDifficultyLevel(){

        switch(difficultyLevel){
            case 0:

                break;
            case 1:

                break;
            case 2:

                break;
        };

        difficultyLevel++;
        return true;
    }

    public void onDispose(){
        super.onDispose();
    }
}
