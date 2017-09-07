package GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Value;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Scene;
import com.mygdx.game.SceneManager;

/**
 * Created by KristsPudzens on 07.09.2017.
 */

public class GUIGameStage extends InputListener {

    Scene scene = null;
    protected Stage gameStage = new Stage();
    protected BitmapFont font;

    //tmp
    private Label healthLabel;

    public GUIGameStage(Scene s) {

        scene = s;

        font = new BitmapFont();

        font.getData().setScale(1.5f, 1.5f);


        //////////////////////////////////////////////////////////////////////////////////////////////////////////
        //In-game gui
        healthLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));


        Texture texture = new Texture(Gdx.files.internal("DoorCompleteMap.png"));
        Image itemImage = new Image();
        itemImage.setSize(50,50);
        itemImage.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));

        Table gtable = new Table();
        gtable.setDebug(true);
        gtable.setFillParent(true);
        gameStage.addActor(gtable);

        gtable.add(itemImage).expandY().width(Value.percentWidth(0.1f, gtable));
        gtable.add().expand();
        gtable.add().expandY().width(Value.percentWidth(0.1f, gtable));
        gtable.row();
        gtable.add(healthLabel).pad(10).fill().height(Value.percentHeight(0.1f, gtable)).colspan(3);

        healthLabel.setAlignment(Align.bottom);

        // fpsLabel = new Label(" ", new Label.LabelStyle(font, Color.WHITE));
        // fpsLabel.setPosition(10, 10);
        // gameStage.addActor(fpsLabel);





    }

    public void render() {

        //gameTable.act(Gdx.graphics.getDeltaTime());

        healthLabel.setText("HEALTH: "+scene.getPlayerHealth()+"% ");



        gameStage.draw();

    }

}





