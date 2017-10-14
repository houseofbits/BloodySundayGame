package GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.badlogic.gdx.utils.Timer;
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
    public Timer.Task wantedLevelTimerTask = null;
    public final int wantedLevelTimeout = 10;

    public int  npcActorsShot = 0;
    public int  enemyActorsShot = 0;
    public float shotDelayTime = 0.2f;
    public float timer = 0;
    public Vector3 gunPosition;

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
        getSceneManager().addGameObject(new BulletObject(r));
    }

    public void onActorEvent(ActorEvent e){
        switch(e.state){
            case SHOOT:
                if(health>0)health--;
                getScene().getUI().playerHurtOverlay();
                getScene().getUI().SetHealthState(health);
                getScene().getActorDist().setActorWeight(ActorObject.ActorType.NPC_DOCTOR, 0.5f);

                if(health <= 0){
                    getScene().getUI().showGameOverPopup();
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
                        startWantedLevelTimer();

                        getScene().getUI().SetWantedState(wantedLevel);
                        getScene().getActorDist().setActorWeight(ActorObject.ActorType.ENEMY_POLICE, (float) wantedLevel);

                    }else if(wantedLevel == 4){
                        getScene().getUI().showGameOverPopup();
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
                                getScene().getActorDist().setActorWeight(ActorObject.ActorType.NPC_DOCTOR, 0);
                            }
                            getScene().getUI().SetHealthState(health);
                            return;
                    }
                }
            }

            Ray r = new Ray(gunPosition, e.pointOfInterest.sub(gunPosition).nor());
            LookAt(r);

            if (timer <= 0) {
                timer = shotDelayTime;
                Fire(r);
            }
        }
    }

    private void startWantedLevelTimer(){
        if(wantedLevel > 0){
            if(wantedLevelTimerTask != null){
                wantedLevelTimerTask.cancel();
                wantedLevelTimerTask = null;
            }
            wantedLevelTimerTask = Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    wantedLevel--;
                    getScene().getUI().SetWantedState(wantedLevel);
                    if(wantedLevel == 0){
                        getScene().getActorDist().setActorWeight(ActorObject.ActorType.ENEMY_POLICE, 0);
                    }else{
                        wantedLevelTimerTask = Timer.schedule(this, wantedLevelTimeout);
                    }
                }
            }, wantedLevelTimeout);
        }
    }

    public void onUpdate() {
        timer = timer - Gdx.graphics.getDeltaTime();
    }

    public void onCreate(SceneManager sceneManagerRef){
        super.onCreate(sceneManagerRef);
        renderable.create();

        Vector3 gunLocalPos = new Vector3(0.3f, -0.5f, -1.0f);

        gunPosition = getScene().cam.position.add(gunLocalPos);
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
        renderable.render(getScene().cam, getScene().environment);
    }
    public void dispose () {
        super.dispose();
        renderable.dispose();
    }
}
