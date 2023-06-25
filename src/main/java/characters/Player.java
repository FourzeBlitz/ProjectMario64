package characters;

import models.RawModel;
import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import textures.PlayerTexturePack;

import java.util.ArrayList;


public class Player {
    private PlayerTexturePack texturePack;
    private TexturedModel model;
    private RawModel rawModel;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    private static final float RUN_SPEED = 10;
    private static final float TURN_SPEED = 200;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 10;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;
    private boolean isInAir = false;

    ArrayList<Vector3f> objects;
    ArrayList<Float> radius;

    public void setObjects(ArrayList<Vector3f> objects) {
        this.objects = objects;
    }

    public void setRadius(ArrayList<Float> radius) {
        this.radius = radius;
    }

    public Player(RawModel rawModel, Vector3f position, float rotX, float rotY, float rotZ, float scale, PlayerTexturePack texturePack) {
        this.rawModel = rawModel;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.texturePack = texturePack;

    }

    //    FROM ENTITY
    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    public void increaseRotation(float dx, float dy, float dz) {
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    public void setTexturePack(PlayerTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public PlayerTexturePack getTexturePack() {
        return texturePack;
    }
    public RawModel getRawModel() {
        return rawModel;
    }
    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public Vector3f getPosition() {
        return position;
    }
    public void setPosition(Vector3f position) {
        this.position = position;
    }
    public float getRotX() {
        return rotX;
    }
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
    public float getRotY() {
        return rotY;
    }
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
    public float getRotZ() {
        return rotZ;
    }
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(float currentSpeed) {
        this.currentSpeed = currentSpeed;
    }

    //    done
    public void move(){
        checkInputs();

        increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
        increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
        if(getPosition().y < TERRAIN_HEIGHT){
            upwardsSpeed = 0;
            isInAir = false;
            getPosition().y = TERRAIN_HEIGHT;
        }
//        Batas tinggi lompatan
        else if(getPosition().y > TERRAIN_HEIGHT+1){
            isInAir = true;
            upwardsSpeed = -JUMP_POWER;
        }
    }

    // Jika di udara maka tdk bisa lompat, tp kalau spasi ditahan jadi lompat terus
    private void jump(){
        if(!isInAir) {
            this.upwardsSpeed = JUMP_POWER;
            isInAir = true;
        }
    }
    private void checkInputs(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            if(checkNotCollision(objects, radius)) {
                this.currentSpeed = RUN_SPEED;
            }
        }else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            if(checkNotCollision(objects, radius)) {
                this.currentSpeed = -RUN_SPEED;
            }
        }else{
            this.currentSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
//            this.currentTurnSpeed = -TURN_SPEED;
            if(checkNotCollision(objects, radius)) {
                increasePosition(0.05f, 0, 0);
            }
        }else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
//            this.currentTurnSpeed = TURN_SPEED;
            if(checkNotCollision(objects, radius)) {
                increasePosition(-0.05f, 0, 0);
            }
        }else{
            this.currentTurnSpeed = 0;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_R)){
            position.set(0,0,0);
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            jump();
        }
    }

    private boolean checkNotCollision(ArrayList<Vector3f> objects, ArrayList<Float> radius){
        // -1      0      1
        //radius center radius
      for(int i = 0; i < objects.size(); i++){
        if (position.x >= (objects.get(i).getX() - radius.get(i)) && position.x <= (objects.get(i).getX() + radius.get(i))
        && position.z >= (objects.get(i).getZ() - radius.get(i)) && position.z <= (objects.get(i).getZ() + radius.get(i))){
//            position.x = objects.get(i).getX()-radius.get(i);
            System.out.println("collison");
            return false;
        }

        // baca posisi karakter dmn biar tau ga bisa lanjut kmn
        // -x -z
        // x -z
        // -x z
        // x z

          // z = w, x = a, -x = d, -z = s
      }
      return true;
    }

}