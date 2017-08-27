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
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

/**
 * Created by T510 on 7/30/2017.
 */

public class StaticObject extends GameObject {

    Renderable renderable = null;
    IntersectionMesh intersectionMesh = null;

    public StaticObject (String modelName){
        this.collide = true;
        renderable = new Renderable(this, modelName);
        intersectionMesh = new IntersectionMesh(this, modelName);
    }
    public StaticObject (String modelName, String intName){
        this.collide = true;
        renderable = new Renderable(this, modelName);
        intersectionMesh = new IntersectionMesh(this, intName);
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
        renderable.create();
        intersectionMesh.create();
    }

    public void onInit(){
        renderable.init();
        intersectionMesh.init();
    }

    public void render () {
        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }
    public boolean intersectRay(Ray ray, Vector3 inter){
        return intersectionMesh.IntersectRay(ray, inter);
    }

}
