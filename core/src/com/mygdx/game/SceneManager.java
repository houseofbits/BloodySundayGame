package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by T510 on 8/2/2017.
 */

public class SceneManager {

    protected Array<GameObject> gameObjectArray = new Array<GameObject>();
    protected TimeUtils time = new TimeUtils();
    protected long prev_frame_time = 0;
    public float frame_time_s = 0;

    public Environment environment;
    public PerspectiveCamera cam;
    public CameraInputController camController;



    public SceneManager(){
        prev_frame_time = time.millis();
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
    }

    public void AddGameObject(GameObject object){
        this.gameObjectArray.add(object);

        object.init(this);
    }

    void renderAll(){

        long current_time_ms = time.millis();
        frame_time_s = (current_time_ms - prev_frame_time) / 1000.0f;

        camController.update();

        for (final GameObject go : this.gameObjectArray) {
            go.update();
        }

        for (final GameObject go : this.gameObjectArray) {
            go.render();
        }

        prev_frame_time = current_time_ms;
    }

    public void dispose(){
        for (final GameObject go : this.gameObjectArray) {
            go.dispose();
         }
    }


    public void SendEvent(GameEvent event){



    }


}
