package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Array;

import GUI.GUIMainStage;
import events.EventManager;
import events.GameEvent;

/**
 * Created by T510 on 8/2/2017.
 */

public class SceneManager {

    public EventManager eventManager = new EventManager();

    protected Array<GameObject> gameObjectArray = new Array<GameObject>();
    protected Array<GameObject> createGameObjectArray = new Array<GameObject>();

    public float frame_time_s = 0;
    public boolean gamePaused = false;

    public AssetManager assetsManager;
    public boolean  assetsLoaded = false;
    public CollisionManager collisionManager = new CollisionManager();

    public GUIMainStage guiMainStage;

    public Scene    scene = null;

    public SceneManager(){

        assetsManager = new AssetManager();

        assetsManager.setErrorListener(new AssetErrorListener() {
            @Override
            public void error(AssetDescriptor assetDescriptor, Throwable throwable) {
                System.out.println("ASSET: "+assetDescriptor.toString()+" - "+throwable.getMessage());
            }
        });

        guiMainStage = new GUIMainStage(this);
    }

    public Scene getScene(){
        return scene;
    }

    public void setGamePaused(boolean p){
        gamePaused = p;
    }

    public GameObject addGameObject(GameObject object){
        object.onCreate(this);
        this.createGameObjectArray.add(object);
        return object;
    }

    public GameObject getObjectByName(String name){
        for(int i=0; i<gameObjectArray.size; i++){
            GameObject o = gameObjectArray.get(i);
            if(o.getName() == name)return o;
        }
        return null;
    }

    void processFrame() {

        if (!assetsManager.update()) {
            assetsLoaded = false;
            guiMainStage.renderLoader();
            return;
        }
        assetsLoaded = true;

        frame_time_s = Gdx.graphics.getDeltaTime();

        if (frame_time_s > 0.025f) frame_time_s = 0.025f;

        for (final GameObject go : this.gameObjectArray) {
            go.render();
        }

        if(gamePaused != true) {

            if (scene != null) scene.onUpdate();

            for (final GameObject go : this.gameObjectArray) {
                go.onUpdate();
            }

            //Process events
            eventManager.process();

            //Remove marked GameObjects
            for (int i = 0; i < gameObjectArray.size; i++) {
                GameObject o = gameObjectArray.get(i);
                if (o.isDisposable()) {
                    o.dispose();
                    o = null;
                    gameObjectArray.removeIndex(i);
                }
            }

            //Load all assets before creating new objects
            if (assetsManager.getQueuedAssets() > 0 && createGameObjectArray.size > 0) {
                assetsManager.finishLoading();
            }

            CollisionTest();
        }
        //Add new game GameObjects
        for (int i = 0; i < createGameObjectArray.size; i++) {
            GameObject o = createGameObjectArray.get(i);
            gameObjectArray.add(o);
            o.onInit();
        }

        createGameObjectArray.clear();

        if(scene == null) guiMainStage.renderMainMenu();

        if(scene != null)scene.guiGameStage.render();   //guiMainStage.renderGameHud(scene);
    }

    public void dispose(){
        for (final GameObject go : this.gameObjectArray) {
            go.dispose();
        }
        this.gameObjectArray.clear();
    }

    public void CreateScene(Scene s){
        if(scene != null)scene.onDispose();
        dispose();
        scene = s;
        scene.onCreate(this);
    }

    public void UnloadScene(){
        if(scene != null)scene.onDispose();
        dispose();
        scene = null;
        Gdx.input.setInputProcessor(guiMainStage.getStage());
    }


    public void sendEvent(GameEvent e){
        eventManager.addEvent(null, e, null);
    }

    public void sendEvent(GameEvent e, String targetName){
        eventManager.addEvent(null, e, getObjectByName(targetName));
    }

    public void sendEvent(GameEvent e, GameObject target){
        eventManager.addEvent(null, e, target);
    }

    private void CollisionTest(){
        for (int a = 0; a < gameObjectArray.size; a++) {
            for (int b = a; b < gameObjectArray.size; b++) {
                if(a != b){
                    GameObject ao = gameObjectArray.get(a);
                    GameObject bo = gameObjectArray.get(b);
                    if(ao.collide && bo.collide) {
                        Vector3 pt = new Vector3();
                        if (collisionManager.Collide(ao, bo, pt)) {
                            ao.onCollision(bo, pt);
                            bo.onCollision(ao, pt);
                        }
                    }
                }
            }
        }
    }

    //TODO: bounding box check
    public GameObject traceRay(Ray ray, Vector3 out){

        GameObject obj = null;
        float prevT = 100000;
        Vector3 inter = new Vector3();
        //boolean hasInt = false;
        GameObject o = null;
        for (int i = 0; i < gameObjectArray.size; i++) {
            o = gameObjectArray.get(i);
            if (o.collide) {
                if (o.intersectRay(ray, inter)) {
                    float l = out.cpy().sub(ray.origin).len2();
                    if(l < prevT){
                        prevT = l;
                        out.set(inter);
                        //hasInt = true;
                        obj = o;
                    }
                }
            }
        }
        return obj;
    }
}
