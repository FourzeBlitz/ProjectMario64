package entities;

import Engine.Window;
import org.joml.Vector3f;
//import org.lwjglx.input.Keyboard;
//import org.lwjglx.input.Mouse;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    //    3rd person camera
    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;
    private Vector3f position = new Vector3f(0,0.2f,0);
    private float pitch;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
    }

    private Window window;

    public Camera(Window window){
        this.window = window;
    }

    public void move(){
//        // maju
//        if(window.isKeyPressed(GLFW_KEY_W)){
//            position.z-=0.02f;
//        }
//        // mundur
//        if(window.isKeyPressed(GLFW_KEY_S)){
//            position.z+=0.02f;
//        }
//        // kanan
//        if(window.isKeyPressed(GLFW_KEY_D)){
//            position.x+=0.02f;
//        }
//        // kiri
//        if(window.isKeyPressed(GLFW_KEY_A)){
//            position.x-=0.02f;
//        }
        // atas
        if(window.isKeyPressed(GLFW_KEY_UP)){
            position.y+=0.02f;
        }
        // bawah
        if(window.isKeyPressed(GLFW_KEY_DOWN)){
            position.y-=0.02f;
        }
//        calculateZoom();
//        calculatePitch();
//        calculateAngleAroundPlayer();
//        float horizontalDistance = calculateHorizontalDistance();
//        float verticalDistance = calculateVerticalDistance();
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public float getRoll() {
        return roll;
    }
//    private void calculateCameraPosition(float horizDistance, float vertiDistance){
//        float theta = player.getRotY() + angleAroundPlayer;
//        float offsetX = horizDistance + Math.sin(Math.toRadians((theta)));
//        position.y = player.getPosition().y + vertiDistance;
//    }

//    private float calculateHorizontalDistance(){
//        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
//    }

//    private float calculateVerticalDistance(){
//        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
//    }
//    private void calculateZoom(){
//        float zoomLevel = Mouse.getDWheel() * 0.1f;
//        distanceFromPlayer -= zoomLevel;
//    }

//    private void calculatePitch(){
//        IF(Mouse.isButtonDown(1)){
//            float pitchChange = Mouse.getDY() * 0.1f;
//            pitch -= pitchChange;
//        }
//    }

//    private void calculateAngleAroundPlayer(){
//        if(Mouse.isButtonDown(0)){
//            float angleChange = Mouse.getDX() * 0.3f;
//            angleAroundPlayer -= angleChange;
//        }
//    }

}