package events;

import com.mygdx.game.GameObject;

/**
 * Created by T510 on 8/2/2017.
 */

//public interface GameEvent {    }

public class GameEvent {

    public GameObject senderObject = null;
    public GameObject targetObject = null;

    public String getName(){
        return this.senderObject.name;
    }
}
