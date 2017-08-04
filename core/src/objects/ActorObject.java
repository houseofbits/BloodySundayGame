package objects;

import com.badlogic.gdx.graphics.VertexAttributes;
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
 * Created by KristsPudzens on 04.08.2017.
 */

public class ActorObject extends GameObject {

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public Vector3 position;

    public ActorObject(Vector3 pos){
        position = pos;
    }

    public void init(SceneManager sceneManagerRef){

        super.init(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();

        model = modelBuilder.createBox(0.65f, 1.8f, 0.4f,
                new Material(ColorAttribute.createDiffuse(0.5f,0.5f,0.7f, 1.0f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

        instance.transform.idt();
        instance.transform.translate(position);

    }

    public void update() {



    }

    public void render () {



    }
    public void dispose () {
        super.dispose();

    }
}
