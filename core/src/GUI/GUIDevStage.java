package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GameScenes.GameScene4;
import com.mygdx.game.Scene;

/**
 * Created by KristsPudzens on 07.09.2017.
 */

public class GUIDevStage extends InputListener {

    GameScene4 scene = null;
    public Stage stage = new Stage();
    protected BitmapFont font;

    public GUIDevStage(GameScene4 s) {

        scene = s;

        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f);

        TextureAtlas buttonsAtlas = new TextureAtlas("gui.pack");
        Skin buttonSkin = new Skin();
        buttonSkin.addRegions(buttonsAtlas);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.down = buttonSkin.getDrawable("buttonon");
        style.up = buttonSkin.getDrawable("buttonoff");
        style.font = font;

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        for (int i = 0; i < scene.animationsArray.size; i++) {
            Animation ani = scene.animationsArray.get(i);
            TextButton button = new TextButton(ani.id +", "+ani.duration, style);
            button.setName(scene.animationsArray.get(i).id);
            button.addListener(this);
            table.add(button).expandX().right().top().width(Value.percentWidth(0.25f, table));
            table.row();

        }


    }

    public void render() {
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

        //if(scene.animationsArray.contains(a.getName(), true)){
            //scene.animatedObject.renderable.PlayAnim(null);
            scene.animatedObject.renderable.PlayAnim(a.getName(), 1,0);
        //}
    }

    public boolean touchDown (InputEvent e, float x, float y, int pointer, int button) {

        return true;   //return true stops event propagation
    }
}





