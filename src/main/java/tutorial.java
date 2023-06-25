
import characters.Mario;
import characters.Player;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import models.RawModel;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.MarioTexturePack;
import textures.PlayerTexturePack;

import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;



public class tutorial {

    private ArrayList<Object> objects
            = new ArrayList<>();

    public static void main(String[] args) {
        // init window
        DisplayManager.createDisplay();

        Loader loader = new Loader();

        // init mario raw model and mario textures
        RawModel marioModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        RawModel playerModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        RawModel yoshiModel = OBJLoader.loadObjModel("resources/model/Super Mario 64 Yoshi Model Remake/N64 Styled Classic Yoshi.obj", loader);
        RawModel booModel = OBJLoader.loadObjModel("resources/model/Dr. Toad/DrToad.obj", loader);

        ModelTexture texture1 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png"));
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_2.png"));
        ModelTexture texture3 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_3.png"));
        ModelTexture texture4 = new ModelTexture(loader.loadTexture("resources/model/Super Mario 64 Yoshi Model Remake/texture_8_6195987693227547908.png"));
        ModelTexture texture5 = new ModelTexture(loader.loadTexture("resources/model/Dr. Toad/dr_kinopio_di.png"));
//        ModelTexture texture6 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Party 4 - Luigi/Luigi/S3c001m0_eye.png"));
//        ModelTexture texture7 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Party 4 - Luigi/Luigi/S3c001m0_hat.png"));

        TexturedModel texturedModel = new TexturedModel(yoshiModel, texture4);
        ModelTexture texture = texturedModel.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel texturedModel1 = new TexturedModel(booModel, texture5);
        ModelTexture textureBoo = texturedModel1.getTexture();
        texture.setShineDamper(10);
        texture.setReflectivity(1);

        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-10),0,0,0,1);
        Entity entityBoo = new Entity(texturedModel1, new Vector3f(-1f,0,-5),0,0,0,1f);
        List<Entity> entities = new ArrayList<>();
        entities.add(entity);
        entities.add(entityBoo);

        // init mario texture pack and Mario object
        MarioTexturePack marioTexturePack = new MarioTexturePack(texture1, texture2, texture3);
        Mario mario = new Mario(marioModel, new Vector3f(0,0,-10),0,0,0,1, marioTexturePack);
        mario.setTexturePack(marioTexturePack);
//        MarioTexturePack luigiTexturePack = new MarioTexturePack(texture5, texture6, texture7);
//        Mario luigi = new Mario(booModel, new Vector3f(-1,0,-10),0,0,0,0.5f, luigiTexturePack);
//        luigi.setTexturePack(luigiTexturePack);

        // init player texture pack and Player object
        PlayerTexturePack playerTexturePack = new PlayerTexturePack(texture1, texture2, texture3);
        Player player = new Player(playerModel, new Vector3f(0,0,0),0,180f,0,1, playerTexturePack);
        player.setTexturePack(playerTexturePack);

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

        Camera camera = new Camera(player);

        MasterRenderer renderer = new MasterRenderer(loader);

//        //player
//        TexturedModel texturedModel2 = new TexturedModel(marioModel, texture2);
//        Player player = new Player(texturedModel2, new Vector3f(2,0,-10),0,0,0,1,window);


//        RawModel marioPlayerModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
//        TexturedModel baseballMario = new TexturedModel(marioPlayerModel, new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png")));
//        Player player = new Player(baseballMario, new Vector3f(0, 0, -10), 0, 0, 0, 1, window);

        // loop
        while(!Display.isCloseRequested()){

            // game logic
            camera.move();
//            player.move();

//            renderer.processEntity(player);
//            System.out.println();
//            renderer.processMario(luigi);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);

            for (Entity entity1: entities){
                renderer.processEntity(entity1);
            }

            //Jalankan player
            player.move();
            renderer.processPlayer(player);

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }
}