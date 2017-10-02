package GameObjects;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.mygdx.game.GameObject;
import com.mygdx.game.Renderable;
import com.mygdx.game.SceneManager;

import GameEvents.ActorEvent;
import GameEvents.PlayerEvent;
import GameEvents.SpawnEvent;
import Utils.Error;

/**
 * Created by T510 on 7/30/2017.
 */

public class PlayerObject extends GameObject {

    Renderable renderable = null;

    public int wantedLevel = 0;
    public float wantedLevelTimer = 0;
    public final int wantedLevelTimeout = 10;

    public int  npcActorsShot = 0;
    public int  enemyActorsShot = 0;

    public int bulletsInMag = 8;
    public float shotDelayTime = 0.2f;
    public float reloadDelayTime = 0.5f;

    public float timer = 0;
    public int magazine = bulletsInMag;

    public Vector3 gunPosition;

    private float fadeTimer = 0;

    public int health = 5;

    public PlayerObject(String modelName){
        this.collide = false;
        renderable = new Renderable(this, modelName);
    }
    public PlayerObject(String modelName, String intName){
        this.collide = true;
        renderable = new Renderable(this, modelName);
    }

    public void Fire(Ray r){

        //cast some effects

        sceneManager.addGameObject(new BulletObject(r));
    }

    public void onActorEvent(ActorEvent e){
        switch(e.state){
            case SHOOT:
                //sceneManager.scene.decreasePlayerHealth(25);
                if(health>0)health--;
                sceneManager.scene.guiGameStage.PlayerHurtOverlay();
                sceneManager.scene.guiGameStage.SetHealthState(health);
                sendEvent(new SpawnEvent(SpawnEvent.Action.ADD_ACTOR, ActorObject.ActorType.NPC_DOCTOR, 0.5f));
                //invoke PlayerShot animation
                if(health <= 0){
                    sceneManager.scene.guiGameStage.ShowGameOverPopup();
                }
                break;
            case DIE:
                if(e.senderObject.getClass() == ActorEnemyObject.class){
                    enemyActorsShot++;
                }
                if(e.senderObject.getClass() == ActorNPCObject.class){

                    npcActorsShot++;
                    if(wantedLevel < 4) {
                        wantedLevel++;
                        wantedLevelTimer = wantedLevelTimeout;
                        sceneManager.scene.guiGameStage.SetWantedState(wantedLevel);
                        sendEvent(new SpawnEvent(SpawnEvent.Action.ADD_ACTOR, ActorObject.ActorType.ENEMY_POLICE, (float) wantedLevel));
                    }
                }
                break;
        }
    }

    public void onPlayerEvent(PlayerEvent e) {

        if (e.state == PlayerEvent.State.TOUCH_DOWN) {
            if(e.gameObject != null){
                if(e.gameObject.getClass() == ActorNPCObject.class){
                    ActorNPCObject npcObject = (ActorNPCObject)e.gameObject;
                    switch(npcObject.actorType){
                        case NPC_DOCTOR:
                            if(health < 5 && npcObject.doctorHealth > 0){
                                health++;
                                npcObject.doctorHealth = 0;
                            }else{
                                sendEvent(new SpawnEvent(SpawnEvent.Action.REMOVE_ACTOR, ActorObject.ActorType.NPC_DOCTOR));
                            }
                            sceneManager.scene.guiGameStage.SetHealthState(health);
                            return;
                    }
                }
            }

            Ray r = new Ray(gunPosition, e.pointOfInterest.sub(gunPosition).nor());
            LookAt(r);

            fadeTimer = 0.5f;

            if (timer <= 0) {
                timer = shotDelayTime;

                Fire(r);

                //magazine--;
                //if (magazine <= 0) {
                 //   timer = reloadDelayTime;
                //}
            }
        }
    }

    public void onUpdate() {

        timer = timer - sceneManager.frame_time_s;

        if(wantedLevelTimer>0)wantedLevelTimer = wantedLevelTimer - sceneManager.frame_time_s;
        if(wantedLevelTimer <= 0 && wantedLevel > 0 && wantedLevel < 4){
            wantedLevelTimer = wantedLevelTimeout;
            wantedLevel--;
            sceneManager.scene.guiGameStage.SetWantedState(wantedLevel);
            if(wantedLevel == 0){
                sendEvent(new SpawnEvent(SpawnEvent.Action.REMOVE_ACTOR, ActorObject.ActorType.ENEMY_POLICE));
            }
        }


        if(timer <= 0 && magazine <= 0)magazine = bulletsInMag;

        //sceneManager.guiStage.addInfoString2 = magazine+" / "+bulletsInMag;
        /*
        if(fadeTimer > 0){
            fadeTimer = fadeTimer - (sceneManager.frame_time_s * 0.1f);

            Matrix4 mnorm = sceneManager.scene.cam.view.cpy();
            mnorm.setToTranslation(gunPosition);

            float f = (0.5f - fadeTimer) / 0.5f;

            renderable.modelInstance.transform.lerp(mnorm, f);
        }*/
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
        renderable.create();

        Vector3 gunLocalPos = new Vector3(0.3f, -0.5f, -1.0f);

        gunPosition = sceneManager.scene.cam.position.add(gunLocalPos);

    }

    public void LookAt(Ray r){

        Vector3 up = new Vector3(0,1,0);
        Vector3 to = r.direction.cpy();

        to.nor().scl(1,1,-1);

        Vector3 left = up.cpy().crs(to).nor();

        up = to.cpy().crs(left).nor();

        Matrix4 mat = new Matrix4();
        mat.idt();
        mat.set(left, up, to, gunPosition);

        renderable.modelInstance.transform = mat;
    }

    public void onInit(){
        renderable.init();

        LookAt(new Ray(new Vector3(0,0,0), new Vector3(0,0,-5)));
    }

    public void render () {
        renderable.render(sceneManager.scene.cam, sceneManager.scene.environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }
}
