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


        /*
        RandomDistribution<String> dstr = new RandomDistribution<String>();

        dstr.add("A", 0.1f);
        dstr.add("B", 0.1f);
        dstr.add("C", 0.1f);
        dstr.add("D", 0.1f);
        dstr.add("E", 0.1f);
        dstr.add("F", 0.1f);
        dstr.add("G", 0.1f);
        dstr.add("H", 0.1f);
        dstr.add("I", 0.1f);

        for(int i = 0; i<200; i++) {
            RandomDistribution<String>.Node g = dstr.get();
            if(g != null){
                dstr.saveDebugLine(g);
            }
        }

        dstr.saveDebugFile("dstr_debug.html");

        */
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
