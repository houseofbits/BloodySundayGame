package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.GameScenes.GameScene1;
import com.mygdx.game.GameScenes.GameScene2;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;
import GameObjects.DoorObject;

/**
 * Created by T510 on 8/8/2017.
 */

public class GUIStage extends InputListener {

    protected Stage stage;
    protected BitmapFont font;
    private TextButton button1;
    private TextButton button2;

    SceneManager sceneManager = null;

    public GUIStage(SceneManager mgr){
        sceneManager = mgr;
        stage = new Stage();
        font = new BitmapFont();

        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;

        button1 = new TextButton("LOAD SCENE 1", style);
        button1.setPosition(10, 145);
        button1.setHeight(30);
        button1.setWidth(150);
        button1.setName("BUTTON1");
        stage.addActor(button1);
        button1.addListener(this);


        button2 = new TextButton("LOAD SCENE 2", style);
        button2.setPosition(10, 110);
        button2.setHeight(30);
        button2.setWidth(150);
        button2.setName("BUTTON2");
        stage.addActor(button2);
        button2.addListener(this);

    }

    public void render(){
        stage.draw();
        stage.act();
    }

    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {

    }
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        Actor a = e.getListenerActor();

        if(a.getName() == "BUTTON1") {
            sceneManager.scene = new GameScene1(sceneManager);
            sceneManager.scene.onCreate();
        }
        if(a.getName() == "BUTTON2") {
            sceneManager.scene = new GameScene2(sceneManager);
            sceneManager.scene.onCreate();
        }
        return true;
    }

}
