import Engine.*;
import Engine.Object;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import org.lwjgl.opengl.GL;
import org.lwjglx.util.vector.Vector3f;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;

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

        Loader loader = new Loader();
//        StaticShader shader = new StaticShader();
//        Renderer renderer = new Renderer(shader, window);

        // init objects
        RawModel model = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png"));
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_2.png"));
        ModelTexture texture3 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_3.png"));

        TexturedModel texturedModel = new TexturedModel(model, texture1);
        TexturedModel texturedModel2 = new TexturedModel(model, texture2);

        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-10),0,0,0,1);
//        Player
        Player player = new Player(texturedModel2, new Vector3f(2,0,-10),0,0,0,1,window);

        Light light = new Light(new org.joml.Vector3f(3000,2000,2000), new org.joml.Vector3f(1,1,1));

        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("res/grassy.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("res/dirt.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("res/pinkFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("res/path.png"));

        // digabung dalam 1 pack texture2nya
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/grassy.png"));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        Camera camera = new Camera(player, window);

        MasterRenderer renderer = new MasterRenderer(window,loader);

        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
//        entities.add(player);

        // loop
        while (window.isOpen()) {
            window.update();
//            DisplayManager.updateDisplay();
            GL.createCapabilities();

            // game logic
//            entity.increaseRotation(0,1,0);
            camera.move();


//            renderer.prepare();
//            shader.start();
//            shader.loadLight(light);
//            shader.loadViewMatrix(camera);
//            renderer.render(entity, shader);
//            shader.stop();

            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity entity1: entities){
                renderer.processEntity(entity1);
            }

//            Jalankan player
            player.move();
            renderer.processPlayer(player);


            renderer.render(light, camera);

            /** Poll for window events.
             * The key callback above will only be
             * invoked during this call.
             * lek ga ada ini jd not responding window nya
             */
            glfwPollEvents();
        }

        renderer.cleanUp();
//        shader.cleanUp();
        loader.cleanUp();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}