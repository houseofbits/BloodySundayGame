package GameEvents;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class PlayerEvent extends GameEvent {

    public enum State{
        TOUCH_DOWN,
        FIRE,
    }
    public PlayerEvent.State state = null;
    public Ray sight = null;
    public Vector3 pointOfInterest = null;

    public PlayerEvent(PlayerEvent.State s){ state = s; }
    public PlayerEvent(PlayerEvent.State s, Ray r, Vector3 poi){
        state = s;
        sight = r;
        pointOfInterest = poi;
    }

}
