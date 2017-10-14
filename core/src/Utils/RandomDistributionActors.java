package Utils;

import com.badlogic.gdx.graphics.g3d.Model;
import com.mygdx.game.SceneManager;

import GameObjects.ActorObject;

/**
 * Created by T510 on 10/14/2017.
 */

public class RandomDistributionActors extends RandomDistribution<ActorObject.ActorType> {

    private SceneManager sceneManager = null;

    public RandomDistributionActors(SceneManager mgr){
        sceneManager = mgr;
    }

    public ActorObject.ActorType getRandomActorType(){
        RandomDistribution<ActorObject.ActorType>.Node node = get();
        if(node != null)return node.data;
        return null;
    }
    public RandomDistributionActors addActorType(ActorObject.ActorType type, float weight){
        add(type, weight);
        sceneManager.assetsManager.load(type.modelName, Model.class);
        return this;
    }
    public RandomDistributionActors addActorType(ActorObject.ActorType... type){
        for (int i = 0; i < type.length; i++) {
            add(type[i], 1.0f);
            sceneManager.assetsManager.load(type[i].modelName, Model.class);
        }
        return this;
    }
    public void removeActorType(ActorObject.ActorType type){
        remove(type);
    }
    //public void setActorWeight(ActorObject.ActorType type, float weight){setFraction(type, weight);}
    public RandomDistributionActors setActorWeight(ActorObject.ActorType type, float weight){
        set(type, weight);
        return this;
    }

    public RandomDistributionActors mapActorWeight(ActorObject.ActorType type, float fa){
        map(type, fa);
        return this;
    }

    public RandomDistributionActors addActorType(ActorObject.ActorType type, float[] weights){
        add(type, weights);
        sceneManager.assetsManager.load(type.modelName, Model.class);
        return this;
    }
    public RandomDistributionActors setDifficultyLevel(float fa){
        for (int i = 0; i < nodes.size; i++) {
            map(nodes.get(i).data, fa);
        }
        return this;
    }


}
