package GameEvents;

import com.badlogic.gdx.math.collision.Ray;

import events.GameEvent;

/**
 * Created by T510 on 8/6/2017.
 */

public class PlayerEvent extends GameEvent {

    public enum State{
        FIRE,
    }
    public PlayerEvent.State state = null;
    public Ray sight = null;

    public PlayerEvent(PlayerEvent.State s){ state = s; }
    public PlayerEvent(PlayerEvent.State s, Ray r){
        state = s;
        sight = r;
    }

}
