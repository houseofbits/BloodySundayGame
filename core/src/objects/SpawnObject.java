package objects;

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
 * Created by T510 on 7/31/2017.
 */

public class SpawnObject extends GameObject {

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public Vector3 position;

    public SpawnObject(Vector3 pos){
        position = pos;
    }

    public void init(SceneManager sceneManagerRef){

        super.init(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        /*
        model = modelBuilder.createBox(size.x, size.y, size.z,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(pos);
        */

    }

    public void update() {



    }

    public void render () {



    }
    public void dispose () {


    }

}
