import Engine.*;
import Engine.Object;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Vector3f;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class tutorial {
    private static Window window =
            new Window
                    (1280, 720, "Mario64");
    private ArrayList<Object> objects
            = new ArrayList<>();
    private MouseInput mouseInput;
    Projection projection = new Projection(window.getWidth(), window.getHeight());
    Camera camera = new Camera();

    public static void main(String[] args) {
        // init window
        window.init();
        GL.createCapabilities();

//        glEnable(GL_DEPTH_TEST);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        // init objects
        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f,//v3
        };

        int[] indices = {
                0,1,3,//top left triangle (v0, v1, v3)
                3,1,2//bottom right triangle (v3, v1, v2)
        };

        float[] textureCoords = {
                0,0, //v0
                0,1, //v1
                1,1, //v2
                1,0 //v3
        };

        RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png"));
        TexturedModel texturedModel = new TexturedModel(model, texture);

        Entity entity = new Entity(texturedModel, new Vector3f(-1,0,0),0,0,0,1);


        // loop
        while (window.isOpen()) {
            window.update();
            GL.createCapabilities();

            // game logic
            entity.increasePosition(0.002f,0,0);
            entity.increaseRotation(0,1,0);
            renderer.prepare();
            shader.start();
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