package GameObjects;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

/**
 * Created by T510 on 7/30/2017.
 */

public class BulletSplashObject extends GameObject {

    private Vector3  position;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    float   stateTimer = 0;

    public BulletSplashObject(Vector3 pos){
        this.receive_hits = false;
        position = pos;
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(0.1f, 0.1f, 0.1f, 6, 6,
                new Material(ColorAttribute.createDiffuse(1, 0.4f, 0f, 0f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(this.position);
    }

    public void onInit(){

        stateTimer = 0.5f;

    }
    public void onUpdate() {

        stateTimer = stateTimer - sceneManager.frame_time_s;

        if(stateTimer <= 0){
        //    this.setDispose(true);
        }
    }
    public void render () {
        modelBatch.begin(sceneManager.scene.cam);
        modelBatch.render(instance, sceneManager.scene.environment);
        modelBatch.end();
    }

    public void dispose () {
        super.dispose();
        modelBatch.dispose();
        model.dispose();
    }


}
