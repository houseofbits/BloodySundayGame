package events;


import com.badlogic.gdx.Gdx;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by T510 on 8/3/2017.
 */

public class EventManager {
    private final Map<Class<? extends GameEvent>, Collection<EventHandler>> bindings;
    private ArrayList<GameEvent> eventArray = new ArrayList<GameEvent>();


    public EventManager() {
        this.bindings = new HashMap<Class<? extends GameEvent>, Collection<EventHandler>>();
    }

    public void registerListener(final EventListener listener) {

        //if (registeredListeners.contains(listener)) {
         //   Gdx.app.log("Listener already registred: ", listener.toString());
        //    return;
        ///}

        Method[] methods = listener.getClass().getDeclaredMethods();

        for (final Method method : methods) {

            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 1) // all listener methods should only have one parameter
                continue;

            Class<?> param = parameters[0];

            if (!method.getReturnType().equals(void.class)) {
                continue;
            }

            if (GameEvent.class.isAssignableFrom(param)) {
                @SuppressWarnings("unchecked") // Java just doesn't understand that this actually is a safe cast because of the above if-statement
                        Class<? extends GameEvent> realParam = (Class<? extends GameEvent>) param;

                if (!this.bindings.containsKey(realParam)) {
                    this.bindings.put(realParam, new TreeSet<EventHandler>());
                }
                Collection<EventHandler> eventHandlersForEvent = this.bindings.get(realParam);
                //CustomFacade.getLog().v("Add listener method: " + method.getName() + " for event " + realParam.getSimpleName());
                eventHandlersForEvent.add(new EventHandler(listener, method));
            }
        }
    }
    public void clearListeners() {
        this.bindings.clear();
    }

    public void removeListener(EventListener listener) {
        for (Map.Entry<Class<? extends GameEvent>, Collection<EventHandler>> ee : bindings.entrySet()) {
            Iterator<EventHandler> it = ee.getValue().iterator();
            while (it.hasNext()) {
                EventHandler curr = it.next();
                if (curr.listener == listener)
                    it.remove();
            }
        }
    }
    public <T extends GameEvent> T executeEvent(T event) {
        Collection<EventHandler> handlers = this.bindings.get(event.getClass());
        if (handlers == null) {
            return event;
        }
        for (EventHandler handler : handlers) {
             handler.execute(event);
        }
        return event;
    }

    public void sendEvent(GameEvent e){
        this.eventArray.add(e);
    }
    public void process(){
        for(GameEvent evt:this.eventArray){
            this.executeEvent(evt);
            evt = null;
        }
        this.eventArray.clear();
    }
}
