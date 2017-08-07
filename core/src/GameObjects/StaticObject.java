package GameObjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

/**
 * Created by T510 on 7/30/2017.
 */

public class StaticObject extends GameObject {

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public StaticObject (Vector3 pos, Vector3 size, Color color){

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(size.x, size.y, size.z,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(pos);

    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

    }

    public void onInit(){

    }

    public void render () {
        modelBatch.begin(sceneManager.cam);
        modelBatch.render(instance, sceneManager.environment);
        modelBatch.end();
    }
    public void dispose () {
        super.dispose();
        modelBatch.dispose();
        model.dispose();
    }


}
