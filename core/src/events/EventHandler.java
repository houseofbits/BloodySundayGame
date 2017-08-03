package events;

import com.badlogic.gdx.Gdx;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by T510 on 8/3/2017.
 */

public class EventHandler implements Comparable<EventHandler> {
    public final EventListener listener;
    private final Method method;

    public EventHandler(EventListener listener, Method method) {
        this.listener = listener;
        this.method = method;
    }
    public void execute(GameEvent event) {
        try {
            method.invoke(listener, event);
        } catch (IllegalAccessException e1) {
            Gdx.app.log("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1.toString());
        } catch (IllegalArgumentException e1) {
            Gdx.app.log("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1.toString());
        } catch (InvocationTargetException e1) {
            Gdx.app.log("Exception when performing EventHandler " + this.listener + " for event " + event.toString(), e1.toString());
        }
    }
    @Override
    public int compareTo(EventHandler other) {
        return this.hashCode() - other.hashCode();
    }
}
