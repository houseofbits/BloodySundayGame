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
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    Image blackOverlayImage;
    private Label healthLabel;

    protected Table confirmPopupTable;

    public GUIGameStage(Scene s) {

        scene = s;

        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f);

        healthLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));

        //Gdx.input.setInputProcessor(stage);

        /*
        Texture texture = new Texture(Gdx.files.internal("DoorCompleteMap.png"));
        Image itemImage = new Image();
        itemImage.setSize(50,50);
        itemImage.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        */
        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;


        TextButton button1 = new TextButton("MAIN", style);
        button1.setName("MAIN");
        button1.addListener(this);

        Table gtable = new Table();
        //gtable.setDebug(true);
        gtable.setFillParent(true);
        stage.addActor(gtable);

        gtable.add(button1).top().expandY().width(Value.percentWidth(0.1f, gtable));
        gtable.add().expand();
        gtable.add().expandY().width(Value.percentWidth(0.1f, gtable));
        gtable.row();
        gtable.add(healthLabel).pad(10).fill().height(Value.percentHeight(0.1f, gtable)).colspan(3);

        healthLabel.setAlignment(Align.bottom);

        // fpsLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        // fpsLabel.setPosition(10, 10);
        // gameStage.addActor(fpsLabel);

        //Progress bar
        Pixmap pixmap = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();

        blackOverlayImage = new Image();
        blackOverlayImage.setDrawable(new TextureRegionDrawable(new TextureRegion(new Texture(pixmap))));
        blackOverlayImage.setSize(stage.getWidth(),stage.getHeight());

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

        confirmPopupTable.add(buttonExit);
        confirmPopupTable.row();
        confirmPopupTable.add(buttonCancel);

        confirmPopupTable.setVisible(false);



        FadeFromBlack(1);
        scene.sceneManager.setGamePaused(false);
    }

    public void FadeToBlack(float time){
        blackOverlayImage.addAction(Actions.sequence(Actions.show(), Actions.fadeIn(time)));
    }

    public void FadeFromBlack(float time){
        blackOverlayImage.addAction(Actions.sequence(Actions.show(), Actions.fadeOut(time), Actions.hide()));
    }

    public void render() {

        blackOverlayImage.act(Gdx.graphics.getDeltaTime());

        healthLabel.setText("HEALTH: "+scene.getPlayerHealth()+"% ");

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
            confirmPopupTable.setVisible(true);
            FadeToBlack(1);
            scene.sceneManager.setGamePaused(true);
        }else if(a.getName() == "CANCEL"){
            confirmPopupTable.setVisible(false);
            FadeFromBlack(1);
            scene.sceneManager.setGamePaused(false);
        }else if(a.getName() == "EXITMAIN"){
            this.scene.sceneManager.UnloadScene();
        }
    }
    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }
}





