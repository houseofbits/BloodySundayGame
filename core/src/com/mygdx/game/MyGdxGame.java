package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

import java.util.Random;

import Utils.RandomDistribution;

public class MyGdxGame implements ApplicationListener {

    protected SceneManager sceneManager;

	@Override
	public void create () {
        sceneManager = new SceneManager();
    }

	@Override
	public void render () {

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClearColor(0.5f,0.5f,0.5f,1.0f);
        Gdx.gl.glEnable(GL30.GL_DEPTH_TEST);

        sceneManager.processFrame();
	}

    @Override
    public void resize(int width, int height) {
    }

	@Override
	public void dispose () {
        sceneManager.dispose();
	}
    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

}
