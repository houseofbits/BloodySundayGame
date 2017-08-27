package GameObjects;

import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameObject;
import com.mygdx.game.SceneManager;

/**
 * Created by T510 on 7/30/2017.
 */

public class BulletObject extends GameObject {

    private float t = 0;
    private float tPrev = 0;
    private Vector3  position = new Vector3(0,0,0);
    private Ray ray;

    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    public BulletObject(Ray r){
        this.collide = true;
        ray = r.cpy();

        //System.out.println(ray);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);

        modelBatch = new ModelBatch();

        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createSphere(0.1f, 0.1f, 0.1f, 2, 2,
                new Material(ColorAttribute.createDiffuse(1, 1, 1, 0f)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);

    }

    public void onInit(){

    }
    public void onUpdate() {

        tPrev = t;
        t = t + sceneManager.frame_time_s * 20; //m/s

        ray.getEndPoint(this.position, t);

        if(t > 20)this.setDispose(true);
    }
    public void render () {

        instance.transform.idt();
        instance.transform.translate(this.position);

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
