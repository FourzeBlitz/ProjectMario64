package entities;

import Engine.Window;
import org.joml.Vector3f;
import org.lwjglx.input.Keyboard;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    private Window window;

    public Camera(Window window){
        this.window = window;
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



}
