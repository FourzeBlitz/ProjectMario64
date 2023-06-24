import Engine.*;
import Engine.Object;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;
import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Vector3f;
import org.joml.*;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.OBJLoader;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class tutorial {
    private static Window window =
            new Window
                    (1280, 720, "Mario64");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    Camera camera = new Camera(window);

    public static void main(String[] args) {
        // init window
        window.init();
        GL.createCapabilities();

//        glEnable(GL_DEPTH_TEST);

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader, window);

        // init objects
        RawModel model = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png"));
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_2.png"));
        ModelTexture texture3 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_3.png"));

        TexturedModel texturedModel = new TexturedModel(model, texture1);
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectiviy(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-10),0,0,0,1);
        Light light = new Light(new org.joml.Vector3f(0, 0, -5), new org.joml.Vector3f(1,1,1));
        Camera camera = new Camera(window);

        // loop
        while (window.isOpen()) {
            window.update();
            GL.createCapabilities();

            // game logic
            entity.increaseRotation(0,1,0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadLight(light);
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();

            /** Poll for window events.
             * The key callback above will only be
             * invoked during this call.
             * lek ga ada ini jd not responding window nya
             */
            glfwPollEvents();
        }

        shader.cleanUp();
        loader.cleanUp();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}