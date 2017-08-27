package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;

import GameObjects.ActorObject;
import GameObjects.BulletObject;
import GameObjects.DoorObject;
import GameObjects.StaticObject;

/**
 * Created by T510 on 8/27/2017.
 */

public class CollisionManager {

    static public boolean Collide(GameObject a, GameObject b, Vector3 out){




        return false;
    }

    static private boolean Collide(BulletObject a, ActorObject b, Vector3 out){
        return false;
    }
    static private boolean Collide(BulletObject a, StaticObject b, Vector3 out){
        return false;
    }
    static private boolean Collide(BulletObject a, DoorObject b, Vector3 out){
        return false;
    }
}