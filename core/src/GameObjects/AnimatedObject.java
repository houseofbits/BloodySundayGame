package GameObjects;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.AnimatedRenderable;
import com.mygdx.game.GameObject;
import com.mygdx.game.IntersectionMesh;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

/**
 * Created by T510 on 7/30/2017.
 */

public class AnimatedObject extends GameObject {

    AnimatedRenderable renderable = null;
    IntersectionMesh intersectionMesh = null;

    public AnimatedObject(String modelName){
        this.collide = true;
        renderable = new AnimatedRenderable(this, modelName);
        intersectionMesh = new IntersectionMesh(this, modelName);
    }
    public AnimatedObject(String modelName, String intName){
        this.collide = true;
        renderable = new AnimatedRenderable(this, modelName);
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
