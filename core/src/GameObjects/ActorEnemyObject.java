package GameObjects;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by T510 on 9/12/2017.
 */

public class ActorEnemyObject extends ActorObject {

    public ActorEnemyObject(Vector3 pos) {

        super(pos, "test_actor_anim.g3dj", "test_actor.g3dj");

        AddStateAnimation(State.APPEAR, "APPEAR1");
        AddStateAnimation(State.IDLE, "IDLE1");

    }
}
