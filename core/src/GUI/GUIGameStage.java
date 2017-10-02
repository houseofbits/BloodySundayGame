package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameScenes.GameScene1;
import com.mygdx.game.GameScenes.GameScene2;
import com.mygdx.game.GameScenes.GameScene3;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

/**
 * Created by KristsPudzens on 07.09.2017.
 */

public class GUIGameStage extends InputListener {

    Scene scene = null;
    protected Stage stage = new Stage();
    protected BitmapFont font;

    //tmp
    private Image blackOverlayImage;
    private Image playerHurtOverlay;

    public Label missionLabel;
    private Label fpsLabel;

    protected Table confirmPopupTable;
    protected Table gameLostPopupTable;
    protected Table gameWonPopupTable;

    private ProgressBar healthBar;

    protected Image health;
    protected Image cops;

    protected Skin hudSkin;

    public GUIGameStage(Scene s) {

        scene = s;

        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f);

        //healthLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));

        //Gdx.input.setInputProcessor(stage);

        TextureAtlas buttonsAtlas = new TextureAtlas("gui/gui.pack.txt");
        hudSkin = new Skin();
        hudSkin.addRegions(buttonsAtlas);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = hudSkin.getDrawable("buttonon");
        style.up = hudSkin.getDrawable("buttonoff");
        style.font = font;


        Button button1 = new Button(hudSkin.getDrawable("key"), hudSkin.getDrawable("keyon"));
        button1.setName("MAIN");
        button1.addListener(this);

        Texture board = new Texture(Gdx.files.internal("hurt_overlay.png"));
        playerHurtOverlay = new Image();
        playerHurtOverlay.setDrawable(new TextureRegionDrawable(new TextureRegion(board)));
        playerHurtOverlay.setSize(stage.getWidth(),stage.getHeight());
        playerHurtOverlay.setVisible(false);

        stage.addActor(playerHurtOverlay);

        Table gtable = new Table();
        //gtable.setDebug(true);
        gtable.setFillParent(true);
        stage.addActor(gtable);


        fpsLabel = new Label("sssss", new Label.LabelStyle(font, Color.WHITE));

        missionLabel = new Label("11", new Label.LabelStyle(font, Color.GOLD));

        health = new Image();
        health.setDrawable(hudSkin.getDrawable("health5"));

        cops = new Image();
        cops.setDrawable(hudSkin.getDrawable("cops0"));

        gtable.add(button1)
                .width(Value.percentWidth(0.139f, gtable))
                .height(Value.percentWidth(0.080f, gtable))
                .top()
                .left()
                .pad(10);
        gtable.add().expand();
        gtable.add(missionLabel).top().right().pad(10).expandY();
        gtable.row();
        gtable.add(health)
                .width(Value.percentWidth(0.242f, gtable))
                .height(Value.percentHeight(0.124f, gtable))
                .bottom()
                .pad(10);
        gtable.add(fpsLabel).bottom().expandX();
        gtable.add(cops)
                .width(Value.percentWidth(0.230f, gtable))
                .height(Value.percentHeight(0.124f, gtable))
                .right()
                .bottom()
                .pad(10);

        //Progress bar
        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        blackOverlayImage = new Image();
        blackOverlayImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        blackOverlayImage.setSize(stage.getWidth(),stage.getHeight());

        //pixmap.setColor(1,0,0, 0.5f);
        //pixmap.fill();
        pixmap.dispose();





        stage.addActor(blackOverlayImage);


        //Exit confirm popup
        confirmPopupTable = new Table();
        //confirmPopupTable.setDebug(true);
        confirmPopupTable.setFillParent(true);
        stage.addActor(confirmPopupTable);

        TextButton buttonExit = new TextButton("Exit to main menu", style);
        buttonExit.setName("EXITMAIN");
        buttonExit.addListener(this);

        TextButton buttonCancel = new TextButton("Back to game", style);
        buttonCancel.setName("CANCEL");
        buttonCancel.addListener(this);

        confirmPopupTable.add(buttonExit)
                .width(Value.percentWidth(0.4f, confirmPopupTable))
                .height(Value.percentHeight(0.2f, confirmPopupTable));
        confirmPopupTable.row();
        confirmPopupTable.add(buttonCancel)
                .width(Value.percentWidth(0.4f, confirmPopupTable))
                .height(Value.percentHeight(0.2f, confirmPopupTable));

        confirmPopupTable.setVisible(false);


        //Game lost popup
        gameLostPopupTable = new Table();
        //confirmPopupTable.setDebug(true);
        gameLostPopupTable.setFillParent(true);
        stage.addActor(gameLostPopupTable);

        TextButton buttonExit2 = new TextButton("Exit to main menu", style);
        buttonExit2.setName("EXITMAIN");
        buttonExit2.addListener(this);

        TextButton buttonRestart = new TextButton("Play again", style);
        buttonRestart.setName("RESTART");
        buttonRestart.addListener(this);

        gameLostPopupTable.add(buttonExit2)
                .width(Value.percentWidth(0.4f, gameLostPopupTable))
                .height(Value.percentHeight(0.2f, gameLostPopupTable));
        gameLostPopupTable.row();
        gameLostPopupTable.add(buttonRestart)
                .width(Value.percentWidth(0.4f, gameLostPopupTable))
                .height(Value.percentHeight(0.2f, gameLostPopupTable));

        gameLostPopupTable.setVisible(false);

        //Game won popup
        gameWonPopupTable = new Table();
        //gameWonPopupTable.setDebug(true);
        gameWonPopupTable.setFillParent(true);
        stage.addActor(gameWonPopupTable);

        TextButton buttonExit3 = new TextButton("Exit to main menu", style);
        buttonExit3.setName("EXITMAIN");
        buttonExit3.addListener(this);

        TextButton buttonNext = new TextButton("Continue", style);
        buttonNext.setName("CONTINUE");
        buttonNext.addListener(this);

        gameWonPopupTable.add(buttonExit3)
                .width(Value.percentWidth(0.4f, gameWonPopupTable))
                .height(Value.percentHeight(0.2f, gameWonPopupTable));
        gameWonPopupTable.row();
        gameWonPopupTable.add(buttonNext)
                .width(Value.percentWidth(0.4f, gameWonPopupTable))
                .height(Value.percentHeight(0.2f, gameWonPopupTable));

        gameWonPopupTable.setVisible(false);

        FadeFromBlack(1);
        scene.sceneManager.setGamePaused(false);
    }

    public void SetHealthState(int val){
        if(val >= 0 && val <= 5){
            health.setDrawable(hudSkin.getDrawable("health"+val));
        }
    }
    public void SetWantedState(int val){
        if(val >= 0 && val <= 4){
            cops.setDrawable(hudSkin.getDrawable("cops"+val));
        }
    }

    public void FadeToBlack(float time){
        blackOverlayImage.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(time)));
    }

    public void FadeFromBlack(float time){
        blackOverlayImage.addAction(Actions.sequence(Actions.show(), Actions.fadeOut(time), Actions.hide()));
    }
    public void PlayerHurtOverlay(){
        playerHurtOverlay.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(0.5f), Actions.fadeOut(1.5f), Actions.hide()));
    }

    public void ShowGameOverPopup(){
        FadeToBlack(1);
        gameLostPopupTable.setVisible(true);
        scene.sceneManager.setGamePaused(true);
    }

    public void render() {

        blackOverlayImage.act(Gdx.graphics.getDeltaTime());

        fpsLabel.setText("FPS: "+Gdx.graphics.getFramesPerSecond());
//
//        healthLabel.setText("HEALTH: "+scene.getPlayerHealth()+"% ");

        stage.draw();
        stage.act();

    }

    public Stage getStage(){
        return stage;
    }

    public void dispose(){
        stage.dispose();
    }

    public void touchUp (InputEvent e, float x, float y, int pointer, int button) {

        Actor a = e.getListenerActor();
        if(a.getName() == "MAIN"){
            gameLostPopupTable.setVisible(false);
            playerHurtOverlay.setVisible(false);
            confirmPopupTable.setVisible(true);
            FadeToBlack(1);
            scene.sceneManager.setGamePaused(true);
        }else if(a.getName() == "CANCEL"){
            confirmPopupTable.setVisible(false);
            FadeFromBlack(1);
            scene.sceneManager.setGamePaused(false);
        }else if(a.getName() == "EXITMAIN"){
            this.scene.sceneManager.UnloadScene();
        }else if(a.getName() == "RESTART"){
            FadeFromBlack(1);
            scene.sceneManager.setGamePaused(false);
            gameLostPopupTable.setVisible(false);
        }else if(a.getName() == "CONTINUE") {
            Scene s = scene.getNextGameSceneInstance();
            if(s != null){
                scene.sceneManager.CreateScene(s);
            }
        }
    }
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }
}





