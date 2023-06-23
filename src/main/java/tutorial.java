import Engine.*;
import Engine.Object;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL30.*;

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
        // initialize
        window.init();
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        // init objects
        float[] vertices = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        // loop
        while (window.isOpen()) {
            window.update();
            GL.createCapabilities();
            renderer.prepare();
            renderer.render(model);
        }

        loader.cleanUp();

        // Poll for window events.
        // The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
    }
}