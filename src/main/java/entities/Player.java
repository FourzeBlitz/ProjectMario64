package entities;//package entities;
//
//import Engine.Window;
//import models.TexturedModel;
//import org.lwjglx.util.vector.Vector3f;
//
//import static org.lwjgl.glfw.GLFW.*;
//import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
//
//public class Player extends Entity{
//    private Window window;
//    private static final float RUN_SPEED = 20;
//    private static final float TURN_SPEED = 160;
//    private float currentSpeed = 0;
//    private float currentTurnSpeed = 0;
//    public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Window window) {
//        super(model, position, rotX, rotY, rotZ, scale);
//        this.window = window;
//    }
//
//    public void move(){
//        checkInputs();
//        super.increaseRotation(0, currentTurnSpeed * Window.getFrameTimeSeconds(), 0);
//        float distance = currentSpeed * Window.getFrameTimeSeconds();
//        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
//        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
//        super.increasePosition(dx, 0, dz);
//    }
//
//    private void checkInputs(){
//        // maju
//        if(window.isKeyPressed(GLFW_KEY_W)){
//            this.currentSpeed = RUN_SPEED;
//        }
//        // mundur
//        else if(window.isKeyPressed(GLFW_KEY_S)){
//            this.currentSpeed = -RUN_SPEED;
//        }
//        else {
//            this.currentSpeed = 0;
//        }
//
//        // rotasi kanan
//        if(window.isKeyPressed(GLFW_KEY_D)){
//            this.currentTurnSpeed = -TURN_SPEED;
//        }
//        // rotasi kiri
//        else if(window.isKeyPressed(GLFW_KEY_A)){
//            this.currentTurnSpeed = TURN_SPEED;
//        }
//        else {
//            this.currentTurnSpeed = 0;
//        }
//
//        // atas
//        if(window.isKeyPressed(GLFW_KEY_UP)){
//
//        }
//        // bawah
//        if(window.isKeyPressed(GLFW_KEY_DOWN)){
//
//        }
//    }
//}
