package com.mygdx.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

import java.util.Random;

public class MyGdxGame implements ApplicationListener {

    protected SceneManager sceneManager;

	@Override
	public void create () {
        sceneManager = new SceneManager();


        float a1 = 0.8f;
        float a2 = 0.1f;
        float a3 = 0.1f;

        a1 = a1 / (a1 + a2 + a3);
        a2 = a2 / (a1 + a2 + a3);
        a3 = a3 / (a1 + a2 + a3);

        float sp1 = 1;
        float sp2 = 1;
        float sp3 = 1;

        float fact1 = 0;
        float fact2 = 0;
        float fact3 = 0;

        Random r = new Random();

        for(int i = 0; i<200; i++){

            float spf1 = sp1 / (sp1 + sp2 + sp3);
            float spf2 = sp2 / (sp1 + sp2 + sp3);
            float spf3 = sp3 / (sp1 + sp2 + sp3);

            fact1 = a1 / spf1;
            fact2 = a2 / spf2;
            fact3 = a3 / spf3;

            if(sp1 == 0)fact1 = 0;
            if(sp2 == 0)fact2 = 0;
            if(sp3 == 0)fact3 = 0;

            float treshold = 1.1f;

            if(fact3 >= treshold){
                sp3++;
                System.out.print("[ ]" + "[ ]" + "[x]");
                System.out.println(" "+spf3+", "+fact3);
            }else
            if(fact2 >= treshold){
                sp2++;
                System.out.print("[ ]" + "[x]" + "[ ]");
                System.out.println(" "+spf2+", "+fact2);
            }else
            if(fact1 >= treshold){
                sp1++;
                System.out.print("[x]" + "[ ]" + "[ ]");
                System.out.println(" "+spf1+", "+fact1);
            }else{
                float rv = r.nextFloat();

                if(rv > 0.8){
                    sp1++;
                    System.out.print("[x]" + "[ ]" + "[ ]");
                    System.out.println(" "+spf1+", "+fact1);
                }else if(rv > 0.4){
                    sp2++;
                    System.out.print("[ ]" + "[x]" + "[ ]");
                    System.out.println(" "+spf2+", "+fact2);
                }else{
                    sp3++;
                    System.out.print("[ ]" + "[ ]" + "[x]");
                    System.out.println(" "+spf3+", "+fact3);
                }
            }



        }

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
