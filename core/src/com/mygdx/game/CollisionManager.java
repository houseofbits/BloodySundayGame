package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import GameObjects.ActorObject;
import GameObjects.BulletObject;
import GameObjects.DoorObject;
import GameObjects.StaticObject;

/**
 * Created by T510 on 8/27/2017.
 */

public class CollisionManager {

    public boolean Collide(GameObject a, GameObject b, Vector3 out){

        Method[] methods = this.getClass().getDeclaredMethods();

        for (final Method method : methods) {

            Class<?>[] parameters = method.getParameterTypes();
            if (parameters.length != 3)
                continue;

            try {
                if(parameters[0] == a.getClass() && parameters[1] == b.getClass()){
                   // System.out.println(a.getClass()+" - "+b.getClass());
                    boolean ret = (Boolean)method.invoke(this, a,b,out);

                    return ret;

                }else if(parameters[1] == a.getClass() && parameters[0] == b.getClass()){
                   // System.out.println(a.getClass()+" - "+b.getClass());
                    boolean ret = (Boolean)method.invoke(this, b,a,out);

                    return ret;
                }
            } catch (IllegalAccessException e1) {
                Gdx.app.log("Exception when performing CollisionManager ", e1.toString());
            } catch (IllegalArgumentException e1) {
                Gdx.app.log("Exception when performing CollisionManager ", e1.toString());
            } catch (InvocationTargetException e1) {
                Gdx.app.log("Exception when performing CollisionManager ", e1.toString());
            }
        }

        return false;
    }

    private boolean Collide(BulletObject a, ActorObject b, Vector3 out){

        Vector3 iv = new Vector3();

        if(b.intersectRay(a.ray, iv)){

            out.set(iv);

            iv.sub(a.ray.origin);

            float l = iv.len();
            float tpos = l - a.t;
            float tposprev = l - a.tPrev;

            if(tpos <= 0 && tposprev >= 0){

                //System.out.println("Intersection: "+l);
                return true;
            }
        }
        return false;
    }
    private boolean Collide(BulletObject a, StaticObject b, Vector3 out){

        Vector3 iv = new Vector3();

        if(b.intersectRay(a.ray, iv)){

            out.set(iv);

            iv.sub(a.ray.origin);

            float l = iv.len();
            float tpos = l - a.t;
            float tposprev = l - a.tPrev;

            if(tpos <= 0 && tposprev >= 0){

                //System.out.println("Intersection: "+l);
                return true;
            }
        }
        //System.out.println("Collide bullet-static");
        return false;
    }
    private boolean Collide(BulletObject a, DoorObject b, Vector3 out){

        Vector3 iv = new Vector3();

        if(b.intersectRay(a.ray, iv)){

            out.set(iv);

            iv.sub(a.ray.origin);

            float l = iv.len();
            float tpos = l - a.t;
            float tposprev = l - a.tPrev;

            if(tpos <= 0 && tposprev >= 0){

                //System.out.println("Intersection door: "+l);
                return true;
            }
        }
        return false;
    }
}