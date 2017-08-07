package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
//import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import GameObjects.DoorObject;
import GameObjects.SpawnObject;
import GameObjects.StaticObject;

public class MyGdxGame extends ApplicationAdapter {

    protected Stage stage;
    protected Label label;
    protected BitmapFont font;
    protected StringBuilder stringBuilder;

    protected SceneManager sceneManager;


    public AssetManager assetsManager;

	@Override
	public void create () {

        stage = new Stage();
        font = new BitmapFont();
        label = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        stage.addActor(label);
        stringBuilder = new StringBuilder();

        sceneManager = new SceneManager();

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

        //sceneManager.assetsManager.load("test_actor.g3dj", Model.class);

    }

	@Override
	public void render () {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);

        sceneManager.processFrame();

        //HUD
        stringBuilder.setLength(0);
        stringBuilder.append(" FPS: ").append(Gdx.graphics.getFramesPerSecond());

        if(!sceneManager.assetsLoaded){
            float progress = sceneManager.assetsManager.getProgress() * 100;
            stringBuilder.append("  [ LOADING "+progress+"% ] ");
        }

        label.setText(stringBuilder);
        stage.draw();
	}
	
	@Override
	public void dispose () {

        sceneManager.dispose();

	}
}
