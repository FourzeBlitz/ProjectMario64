package entities;

import models.TexturedModel;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.input.Keyboard;
import static org.lwjgl.glfw.GLFW.*;

import Engine.Window;

public class Player {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;
    private Window window;

    private static final float RUN_SPEED = 20;
    private static final float TURN_SPEED = 500;
    private static final float GRAVITY = -50;
    private static final float JUMP_POWER = 30;

    private static final float TERRAIN_HEIGHT = 0;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    private boolean isInAir = false;
    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Window window) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.window = window;

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
    public TexturedModel getModel() {
        return model;
    }
    public void setModel(TexturedModel model) {
        this.model = model;
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

//    done
    public void move(){
        checkInputs();
        float delta = (float) 0.001; //karena tidak pakai DisplayManager maka buat variabel disini

        increaseRotation(0, currentTurnSpeed * delta, 0);
        float distance = currentSpeed * delta;

        float dx = (float) (distance * Math.sin(Math.toRadians(getRotY())));
        float dz = (float) (distance * Math.cos(Math.toRadians(getRotY())));
        increasePosition(dx, 0, dz);
        upwardsSpeed += GRAVITY * delta;
        increasePosition(0, upwardsSpeed * delta, 0);
        if(getPosition().y < TERRAIN_HEIGHT){
            upwardsSpeed = 0;
            isInAir = false;
            getPosition().y = TERRAIN_HEIGHT;
        }
//        Batas tinggi lompatan
        else if(getPosition().y > 1){
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
        if(window.isKeyPressed(GLFW_KEY_W)){
            this.currentSpeed = RUN_SPEED;

        }else if(window.isKeyPressed(GLFW_KEY_S)){
            this.currentSpeed = -RUN_SPEED;

        }else{
            this.currentSpeed = 0;
        }

        if(window.isKeyPressed(GLFW_KEY_D)){
            this.currentTurnSpeed = -TURN_SPEED;
        }else if(window.isKeyPressed(GLFW_KEY_A)){
            this.currentTurnSpeed = TURN_SPEED;
        }else{
            this.currentTurnSpeed = 0;
        }

        if(window.isKeyPressed(GLFW_KEY_SPACE)){
            jump();
        }
    }

}
