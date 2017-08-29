package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.GameScenes.GameScene1;
import com.mygdx.game.GameScenes.GameScene2;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;
import GameObjects.DoorObject;

/**
 * Created by T510 on 8/8/2017.
 */

public class GUIStage extends InputListener {

    protected Stage mainMenuStage;
    protected Stage loaderStage;
    protected Stage gameStage;

    protected BitmapFont font;

    private TextButton button1;
    private TextButton button2;

    private Label healthLabel;
    private Label fpsLabel;
    private Label loadingLabel;

    public String addInfoString1 = "";
    public String addInfoString2 = "";

    SceneManager sceneManager = null;

    public GUIStage(SceneManager mgr){
        sceneManager = mgr;
        mainMenuStage = new Stage();
        loaderStage = new Stage();
        gameStage = new Stage();

        font = new BitmapFont();

        font.getData().setScale(1.5f, 1.5f);

        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        Gdx.input.setInputProcessor(mainMenuStage);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;

        button1 = new TextButton("SCENE 1", style);
        button1.setPosition(10, 200);
        button1.setHeight(80);
        button1.setWidth(300);
        button1.setName("BUTTON1");
        mainMenuStage.addActor(button1);
        button1.addListener(this);

        button2 = new TextButton("SCENE 2", style);
        button2.setPosition(10, 100);
        button2.setHeight(80);
        button2.setWidth(300);
        button2.setName("BUTTON2");
        mainMenuStage.addActor(button2);
        button2.addListener(this);

        healthLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        healthLabel.setPosition(10, 10);
        gameStage.addActor(healthLabel);

       // fpsLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
      //  fpsLabel.setPosition(10, 10);
       // gameStage.addActor(fpsLabel);

        loadingLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        loadingLabel.setPosition(500, 260);
        loaderStage.addActor(loadingLabel);

    }

    public Stage getStage(){
        return mainMenuStage;
    }

    public void renderLoader(){

        float progress = sceneManager.assetsManager.getProgress() * 100;

        loadingLabel.setText("  [ LOADING "+progress+"% ] ");

        loaderStage.draw();
    }

    public void renderMainMenu(){

       // fpsLabel.setText("FPS: "+Gdx.graphics.getFramesPerSecond());

        mainMenuStage.draw();
        mainMenuStage.act();
    }

    public void renderGameHud(Scene scene) {

        healthLabel.setText("HEALTH: "+scene.getPlayerHealth()+"% "
                            +"AMMO: "+addInfoString2+" "
                            +"TOTAL SHOT: "+addInfoString1+" "
                           // +"SHOT BAD: 0"
                            );

        gameStage.draw();

    }

    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {

        Actor a = e.getListenerActor();

        if(a.getName() == "BUTTON1") {
            sceneManager.CreateScene(new GameScene1());
        }
        if(a.getName() == "BUTTON2") {
            sceneManager.CreateScene(new GameScene2());
        }
    }
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {


        return true;   //return true stops event propagation
    }

}
