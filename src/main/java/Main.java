import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

public class Main {
    boolean keyRditekan = false;
    float derajatkamera;
    private Window window =
            new Window
                    (1920,1080,"Hello World");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private ArrayList<Object> objectsRectangle
            = new ArrayList<>();

    private ArrayList<Object> objectsPointsControl
            = new ArrayList<>();

    private MouseInput mouseInput;

    static float rot = 0f;
    int countDegree = 0;
    Projection projection = new Projection(window.getWidth(),window.getHeight());
    Camera camera = new Camera();
    public void init(){
        window.init();
        GL.createCapabilities();
        mouseInput = window.getMouseInput();
        camera.setPosition(0,1f,1.7f);
        camera.moveDown(0.6f);
//        camera.setRotation((float)Math.toRadians(0.0f),(float)Math.toRadians(30.0f));

        //code
//        objects.add(new Object2d(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));
//        objects.add(new Object(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/" +
//                    "sceneWithVerticesColor.vert"
//                        , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                    ("resources/shaders/" +
//                    "sceneWithVerticesColor.frag"
//                            , GL_FRAGMENT_SHADER)
//        ),
//        new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f(-0.5f,-0.5f,0.0f),
//                    new Vector3f(0.5f,-0.5f,0.0f)
//                )
//            ),
//        new ArrayList<>(
//            List.of(
//                new Vector3f(1.0f,0.0f,0.0f),
//                new Vector3f(0.0f,1.0f,0.0f),
//                new Vector3f(0.0f,0.0f,1.0f)
//            )
//        )
//        ));
//        objectsRectangle.add(new Rectangle(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(
//                List.of(
//                    new Vector3f(0.0f,0.0f,0.0f),
//                    new Vector3f(0.5f,0.0f,0.0f),
//                    new Vector3f(0.0f,0.5f,0.0f),
//                    new Vector3f( 0.5f,0.5f,0.0f)
//                )
//            ),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f),
//            Arrays.asList(0,1,2,1,2,3)
//
//        ));
//        objectsPointsControl.add(new Object(
//            Arrays.asList(
//                //shaderFile lokasi menyesuaikan objectnya
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.vert"
//                , GL_VERTEX_SHADER),
//                new ShaderProgram.ShaderModuleData
//                ("resources/shaders/scene.frag"
//                , GL_FRAGMENT_SHADER)
//            ),
//            new ArrayList<>(),
//            new Vector4f(0.0f,1.0f,1.0f,1.0f)
//        ));
        objects.add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f),
                Arrays.asList(0.0f,0.0f,0.0f),
                0.125f,
                0.125f,
                0.125f,
                36,
                18
        ));
//        objects.get(0).translateObject(0.5f,0.0f,0.0f);
        objects.get(0).scaleObject(1f,1f,1f);
        //camera.setPosition(objects.get(0).getCenterPoint().get(0)-0.1f,objects.get(0).getCenterPoint().get(1)+0.1f,0.5f+objects.get(0).getCenterPoint().get(2));

        objects.add(new Sphere(
                Arrays.asList(
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
                ),
                new ArrayList<>(),
                new Vector4f(0.0f,1.0f,0.0f,1.0f),
                Arrays.asList(0.0f,0.0f,0.0f),
                0.125f,
                0.125f,
                0.125f,
                36,
                18
        ));
        objects.get(1).scaleObject(1f,1f,1f);
        objects.get(1).translateObject(-0.5f,0f,0f);
//        objects.get(0).getChildObject().add(new Sphere(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f,1.0f,0.0f,1.0f),
//                Arrays.asList(0.0f,0.0f,0.0f),
//                0.125f,
//                0.125f,
//                0.125f,
//                36,
//                18
//        ));
//        objects.get(0).getChildObject().get(0).translateObject(0.25f,0.0f,0.0f);
////        objects.get(0).getChildObject().get(0).setCenterPoint(Arrays.asList(0.25f,0.0f,0.0f));
//
//        objects.get(0).getChildObject().add(new Sphere(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f,1.0f,0.0f,1.0f),
//                Arrays.asList(0.0f,0.0f,0.0f),
//                0.125f,
//                0.125f,
//                0.125f,
//                36,
//                18
//        ));
//        objects.get(0).getChildObject().get(1).translateObject(0.5f,0.0f,0.0f);
////        objects.get(0).getChildObject().get(1).setCenterPoint(Arrays.asList(0.5f,0.0f,0.0f));
//
//        objects.get(0).getChildObject().get(1).getChildObject().add(new Sphere(
//                Arrays.asList(
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.vert", GL_VERTEX_SHADER),
//                        new ShaderProgram.ShaderModuleData("resources/shaders/scene.frag", GL_FRAGMENT_SHADER)
//                ),
//                new ArrayList<>(),
//                new Vector4f(0.0f,1.0f,0.0f,1.0f),
//                Arrays.asList(0.0f,0.0f,0.0f),
//                0.125f,
//                0.125f,
//                0.125f,
//                36,
//                18
//        ));
//        objects.get(0).getChildObject().get(1).getChildObject().get(0).scaleObject(0.5f,0.5f,0.5f);
//        objects.get(0).getChildObject().get(1).getChildObject().get(0).translateObject(0.5f,-0.1f,0.0f);
////        objects.get(0).getChildObject().get(1).getChildObject().get(0).setCenterPoint(Arrays.asList(0.5f,-0.1f,0.0f));
    }
    public void input(){
        float move = 0.01f;
        if (window.isKeyPressed(GLFW_KEY_W)) {
            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(-rot));
            camera.moveBackwards(1.7f);

            objects.get(0).translateObject(0f,0f,-move);
            camera.moveForward(move);

            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(rot));
            camera.moveBackwards(1.7f);
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(-rot));
            camera.moveBackwards(1.7f);

            objects.get(0).translateObject(-move,0f,0f);
            camera.moveLeft(move);

            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(rot));
            camera.moveBackwards(1.7f);
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(-rot));
            camera.moveBackwards(1.7f);

            objects.get(0).translateObject(0f,0f,move);
            camera.moveBackwards(move);

            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(rot));
            camera.moveBackwards(1.7f);
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(-rot));
            camera.moveBackwards(1.7f);

            objects.get(0).translateObject(move,0f,0f);
            camera.moveRight(move);

            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(rot));
            camera.moveBackwards(1.7f);
        }

        if (window.isKeyPressed(GLFW_KEY_UP)){
            camera.moveUp(move);
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.moveDown(move);
        }
        if(window.isKeyPressed(GLFW_KEY_R)){
            keyRditekan = true;
//            for(float i = 0f; i < 360f; i += 0.5f) {
//                List<Float> temp = new ArrayList<>(objects.get(0).getCenterPoint());
//                camera.setPosition(temp.get(0) * -1, temp.get(1) * -1, camera.getPosition().z);
//                camera.addRotation(0, (float) Math.toRadians(0.5f));
//                camera.setPosition(temp.get(0) * 1, temp.get(1) * 1, camera.getPosition().z);
//            }
        }
        if (window.isKeyPressed(GLFW_KEY_G)){
//            float zCAM = camera.getPosition().z;
//            float xCAM = camera.getPosition().x;
//            float zOBJ = objects.get(0).getCenterPoint().get(2);
//            float xOBJ = objects.get(0).getCenterPoint().get(0);
//            float x = (float) Math.pow(xCAM-xOBJ,2);
//            float y = (float) Math.pow(zCAM-zOBJ,2);
//            float temp = (float) Math.sqrt(x+y)*0.01f;
//            camera.moveRight(move+temp);
//            camera.addRotation(0f,-move-(temp));
            camera.moveForward(1.7f);
            camera.addRotation(0,(float)Math.toRadians(1f));
            setRot(1f);
            camera.moveBackwards(1.7f);
        }

        float move2 = 0.5f;
        if(keyRditekan){
            float posisiX = camera.getPosition().x;
            float posisiY = camera.getPosition().y;
            float posisiZ = camera.getPosition().z;
            camera.setPosition(-posisiX,-posisiY,-posisiZ);
            camera.addRotation(0.0f,(float)Math.toRadians(move2));
            camera.setPosition(posisiX,posisiY,posisiZ);
            derajatkamera+=move2;
            if(derajatkamera>=360.0f){
                derajatkamera=0f;
                keyRditekan=false;
            }
        }
        if(mouseInput.isLeftButtonPressed()){
            Vector2f displayVec = window.getMouseInput().getDisplVec();
            camera.addRotation((float)Math.toRadians(displayVec.x * 0.1f),
                    (float)Math.toRadians(displayVec.y * 0.1f));
        }
        if(window.getMouseInput().getScroll().y != 0){
            projection.setFOV(projection.getFOV()- (window.getMouseInput().getScroll().y*0.01f));
            window.getMouseInput().setScroll(new Vector2f());
        }
    }
    public void loop(){
        while (window.isOpen()) {
            window.update();
            glClearColor(0.0f,
                    0.0f, 0.0f,
                    0.0f);
            GL.createCapabilities();
            input();

            //code
            for(Object object: objects){
                object.draw(camera,projection);
            }
//            for(Object object: objectsRectangle){
//                object.draw();
//            }
//            for(Object object: objectsPointsControl){
//                object.drawLine();
//            }

            // Restore state
            glDisableVertexAttribArray(0);

            // Poll for window events.
            // The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
    public void run() {

        init();
        loop();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static float getRot() {
        return rot;
    }

    public static void setRot(float rot) {
        Main.rot += rot;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}