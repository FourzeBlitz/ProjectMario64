package entities;

import Engine.MouseInput;
import Engine.Window;
import characters.Player;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.input.Mouse;
//import org.lwjglx.input.Keyboard;
//import org.lwjglx.input.Mouse;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    //    3rd person camera
    private float distanceFromPlayer = 50;
    private float angleAroundPlayer = 0;
    private Vector3f position;
    // how high / low the camera is
    private float pitch = 0;
    private float yaw;
    private float roll;

    private Player player;

    public Camera(Player player){
        this.player = player;
        position= new Vector3f(0,0.2f,0);
        mouseInput = window.getMouseInput();
    }

    private Window window;
    private MouseInput mouseInput;

    public Camera(Window window){
        this.window = window;
        position= new Vector3f(0,0.2f,0);
        mouseInput = window.getMouseInput();
    }
    public Camera(Window window,Player player){
        this.window=window;
        this.player=player;
        mouseInput = window.getMouseInput();
        position= new Vector3f(player.getPosition().x,player.getPosition().y+3f,player.getPosition().z+10f);
    }

    public void move(){
        // maju
        if(window.isKeyPressed(GLFW_KEY_W)){
            position.z-=0.02f;
        }
        // mundur
        if(window.isKeyPressed(GLFW_KEY_S)){
            position.z+=0.02f;
        }
        // kanan
        if(window.isKeyPressed(GLFW_KEY_D)){
            position.x+=0.02f;
        }
        // kiri
        if(window.isKeyPressed(GLFW_KEY_A)){
            position.x-=0.02f;
        }
        // atas
        if(window.isKeyPressed(GLFW_KEY_UP)){
            position.y+=0.02f;
        }
        // bawah
        if(window.isKeyPressed(GLFW_KEY_DOWN)){
            position.y-=0.02f;
        }
        calculateZoom();
        calculatePitch();
        calculateAngleAroundPlayer();
        float horizontalDistance = calculateHorizontalDistance();
        float verticalDistance = calculateVerticalDistance();
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
    private void calculateCameraPosition(float horizDistance, float vertiDistance){
        float theta = player.getRotY() + angleAroundPlayer;
        float offsetX = (float) (horizDistance + Math.sin(Math.toRadians((theta))));
        position.y = player.getPosition().y + vertiDistance;
    }

    private float calculateHorizontalDistance(){
        return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
    }

    private float calculateVerticalDistance(){
        return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
    }
    private void calculateZoom(){
        float zoomLevel = Mouse.getDWheel() * 0.1f;
        distanceFromPlayer -= zoomLevel;
    }

    private void calculatePitch(){
        if(mouseInput.isLeftButtonPressed()){
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch -= pitchChange;
        }
    }

    private void calculateAngleAroundPlayer(){
        if(mouseInput.isRightButtonPressed()){
            float angleChange = Mouse.getDX() * 0.3f;
            angleAroundPlayer -= angleChange;
        }
    }

}