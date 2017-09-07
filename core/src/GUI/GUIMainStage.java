package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.StringBuilder;
import com.mygdx.game.GameScenes.GameScene1;
import com.mygdx.game.GameScenes.GameScene2;
import com.mygdx.game.GameScenes.GameScene3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

import GameEvents.DoorEvent;
import GameObjects.DoorObject;

/**
 * Created by T510 on 8/8/2017.
 */

public class GUIMainStage extends InputListener {

    protected Stage mainMenuStage;
    protected Stage loaderStage;

//    protected Stage gameStage;

    protected BitmapFont font;

    private TextButton button1;
    private TextButton button2;

    private Label loadingLabel;
    private ProgressBar progressBar;

    SceneManager sceneManager = null;

    public GUIMainStage(SceneManager mgr){
        sceneManager = mgr;
        mainMenuStage = new Stage();
        loaderStage = new Stage();
        //gameStage = new Stage();

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
        button1.setName("BUTTON1");
        button1.addListener(this);

        button2 = new TextButton("SCENE 2", style);
        button2.setName("BUTTON2");
        button2.addListener(this);

        TextButton button3 = new TextButton("SCENE 3", style);
        button3.setName("BUTTON3");
        button3.addListener(this);

        ///////////////////////////////////////////////////////////////////////////////////////////
        //Loading screen

        loadingLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        loadingLabel.setPosition(500, 260);

        Table table = new Table();
        table.setFillParent(true);
        mainMenuStage.addActor(table);

        //table.setDebug(true);
        table.add(button1)
                .width(Value.percentWidth(0.4f, table))
                .height(Value.percentHeight(0.2f, table));
        table.add(button3)
                .width(Value.percentWidth(0.4f, table))
                .height(Value.percentHeight(0.2f, table));
        table.row().pad(10, 10, 10, 10);
        table.add(button2)
                .width(Value.percentWidth(0.4f, table))
                .height(Value.percentHeight(0.2f, table));
        //table.row().pad(10, 0, 10, 0);

        Texture board = new Texture(Gdx.files.internal("menu_background.jpg"));
        table.background(new TextureRegionDrawable(new TextureRegion(board)));

        Table table2 = new Table();
        table2.setFillParent(true);
        loaderStage.addActor(table2);

        //table.setDebug(true);
        table2.add(loadingLabel).center();
        Texture board2 = new Texture(Gdx.files.internal("loading.jpg"));
        table2.background(new TextureRegionDrawable(new TextureRegion(board2)));

        //Progress bar
        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;

        pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        progressBarStyle.knobBefore = drawable;

        progressBar = new ProgressBar(0.0f, 1.0f, 0.01f, false, progressBarStyle);

        table2.row().pad(10, 0, 10, 0);
        table2.add(progressBar)
                .width(Value.percentWidth(1, table))
                .height(Value.percentHeight(0.3f, table));


    }

    public Stage getStage(){
        return mainMenuStage;
    }

    public void renderLoader(){

        float progress = sceneManager.assetsManager.getProgress() * 100;
        loadingLabel.setText(""+(int)progress+"%");
        progressBar.setValue(progress);
        loaderStage.draw();
    }

    public void renderMainMenu(){

       // fpsLabel.setText("FPS: "+Gdx.graphics.getFramesPerSecond());

        mainMenuStage.draw();
        mainMenuStage.act();
    }

    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {

        Actor a = e.getListenerActor();

        if(a.getName() == "BUTTON1") {
            sceneManager.CreateScene(new GameScene1());
        }
        if(a.getName() == "BUTTON2") {
            sceneManager.CreateScene(new GameScene2());
        }
        if(a.getName() == "BUTTON3") {
            sceneManager.CreateScene(new GameScene3());
        }
    }
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {


        return true;   //return true stops event propagation
    }

}
