import Engine.*;
import Engine.Object;
import characters.Mario;
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
import textures.MarioTexturePack;
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

    public static void main(String[] args) {
        // init window
        window.init();
        GL.createCapabilities();

        Loader loader = new Loader();

        // init mario raw model and mario textures
        RawModel marioModel = OBJLoader.loadObjModel("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Mario.obj", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_eye1_s3tc.png"));
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_eye2_s3tc.png"));
        ModelTexture texture3 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_eye3_s3tc.png"));
        ModelTexture texture4 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_eye4_s3tc.png"));
        ModelTexture texture5 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_eye5_s3tc.png"));
        ModelTexture texture6 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_mouse1_s3tc.png"));
        ModelTexture texture7 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_new_main_s3tc.png"));
        ModelTexture texture8 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_polmask1_i4.png"));
        ModelTexture texture9 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_ma_rak_dummy.png"));
        ModelTexture texture10 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Super Mario Sunshine - Mario/Mario/Textures/H_toon_2_4i.png"));

        TexturedModel texturedModel = new TexturedModel(marioModel, texture1);
        ModelTexture texture = texturedModel.getTexture();
//        texture.setShineDamper(10);
//        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-10),0,0,0,1);
        List<Entity> entities = new ArrayList<>();
//        entities.add(entity);

        // init mario texture pack and Mario object
        MarioTexturePack marioTexturePack = new MarioTexturePack(texture1, texture2, texture3, texture4, texture5, texture6, texture7, texture8, texture9, texture10);
        Mario mario = new Mario(marioModel, new Vector3f(0,0,-10),0,0,0,1, marioTexturePack);
        mario.setTexturePack(marioTexturePack);

        //lighting
        Light light = new Light(new org.joml.Vector3f(3000,2000,2000), new org.joml.Vector3f(1,1,1));

        //terrain
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("res/grassy.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("res/dirt.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("res/pinkFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("res/path.png"));

        // digabung dalam 1 pack texture2nya
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/grassy.png"));

        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(-1, -1, loader, texturePack, blendMap);

        Camera camera = new Camera(window, new org.joml.Vector3f(0, 0.5f, 0));

        MasterRenderer renderer = new MasterRenderer(window,loader);

        //player
        TexturedModel texturedModel2 = new TexturedModel(marioModel, texture2);
        Player player = new Player(texturedModel2, new Vector3f(2,0,-10),0,0,0,1,window);


//        RawModel marioPlayerModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
//        TexturedModel baseballMario = new TexturedModel(marioPlayerModel, new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png")));
//        Player player = new Player(baseballMario, new Vector3f(0, 0, -10), 0, 0, 0, 1, window);

        // loop
        while (window.isOpen()) {
            window.update();
            GL.createCapabilities();

            // game logic
//            camera.move();
//            player.move();

//            renderer.processEntity(player);
//            System.out.println(mario.getPosition());
            renderer.processMario(mario);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity entity1: entities){
//                renderer.processEntity(entity1);
            }

            //Jalankan player
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
        loader.cleanUp();

        // Terminate GLFW and
        // free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}