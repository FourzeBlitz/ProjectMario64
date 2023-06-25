
import characters.Mario;
import characters.Player;
import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector;
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

        List<Entity> entities = new ArrayList<>();

        ArrayList<Vector3f> collisions = new ArrayList<>();
        ArrayList<Float> radius = new ArrayList<>();

        // init mario raw model and mario textures
        RawModel marioModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        RawModel playerModel = OBJLoader.loadObjModel("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario.obj", loader);
        ModelTexture texture1 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png"));
        ModelTexture texture2 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_2.png"));
        ModelTexture texture3 = new ModelTexture(loader.loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_3.png"));
        // init mario texture pack and Mario object
        MarioTexturePack marioTexturePack = new MarioTexturePack(texture1, texture2, texture3);
        Mario mario = new Mario(marioModel, new Vector3f(0,0,-10),0,0,0,1, marioTexturePack);
        mario.setTexturePack(marioTexturePack);

        // init yoshi
        RawModel yoshiModel = OBJLoader.loadObjModel("resources/model/Super Mario 64 Yoshi Model Remake/N64 Styled Classic Yoshi.obj", loader);
        ModelTexture textureYoshi = new ModelTexture(loader.loadTexture("resources/model/Super Mario 64 Yoshi Model Remake/texture_8_6195987693227547908.png"));
        textureYoshi.setShineDamper(10);
        textureYoshi.setReflectivity(1);
        TexturedModel texturedModel = new TexturedModel(yoshiModel, textureYoshi);
        entities.add(new Entity(texturedModel, new Vector3f(0,0,-10),0,0,0,1));
        collisions.add(new Vector3f(0,0,-10));
        radius.add(1f);

        // init dr toad
        RawModel drToadModel = OBJLoader.loadObjModel("resources/model/Dr. Toad/DrToad.obj", loader);
        ModelTexture textureToad = new ModelTexture(loader.loadTexture("resources/model/Dr. Toad/dr_kinopio_di.png"));
        textureToad.setShineDamper(10);
        textureToad.setReflectivity(1);
        TexturedModel texturedModel1 = new TexturedModel(drToadModel, textureToad);
        entities.add(new Entity(texturedModel1, new Vector3f(-1f,0,-5),0,0,0,1f));
        collisions.add(new Vector3f(-1,0,-5));
        radius.add(1f);

        // init bowser
        RawModel bowserModel = OBJLoader.loadObjModel("resources/model/Bowser/koopa_body01a.obj", loader);
        ModelTexture textureBowser = new ModelTexture(loader.loadTexture("resources/model/Bowser/koopa_body01a.png"));
        textureBowser.setShineDamper(10);
        textureBowser.setReflectivity(1);
        TexturedModel texturedModelBowser = new TexturedModel(bowserModel, textureBowser);
        entities.add(new Entity(texturedModelBowser, new Vector3f(3f,0,-5),0,0,0,0.03f));
        collisions.add(new Vector3f(3f,0,-5));
        radius.add(1f);

        // init castle
        RawModel castleModel = OBJLoader.loadObjModel("resources/model/3DS - New Super Mario Bros 2 - Castle/cobCastle.obj", loader);
        ModelTexture textureCastle = new ModelTexture(loader.loadTexture("resources/model/3DS - New Super Mario Bros 2 - Castle/Tex/Castle01.png"));
        textureBowser.setShineDamper(10);
        textureBowser.setReflectivity(1);
        TexturedModel texturedModelCastle = new TexturedModel(castleModel, textureCastle);
        entities.add(new Entity(texturedModelCastle, new Vector3f(-0.5f,0,-80),0,0,0,0.2f));
        collisions.add(new Vector3f(-0.5f,0,-80));
        radius.add(1f);

        // init Luigi
        RawModel luigiModel = OBJLoader.loadObjModel("resources/model/3DS - Mario & Sonic at the London 2012 Olympic Games - Luigi/lui_M.obj", loader);
        ModelTexture textureLuigi = new ModelTexture(loader.loadTexture("resources/model/3DS - Mario & Sonic at the London 2012 Olympic Games - Luigi/CTR_lui_body_M.png"));
        textureBowser.setShineDamper(10);
        textureBowser.setReflectivity(1);
        TexturedModel texturedModelLuigi = new TexturedModel(luigiModel, textureLuigi);
        entities.add(new Entity(texturedModelLuigi, new Vector3f(50f,0,-20),0,0,0,0.1f));
        collisions.add(new Vector3f(50f,0,-20));
        radius.add(1f);

        // init Luigi House
        RawModel luigiHouseModel = OBJLoader.loadObjModel("resources/model/Luigi's House/luigihouse.obj", loader);
        ModelTexture textureLuigiHouse = new ModelTexture(loader.loadTexture("resources/model/Luigi's House/Tex_0074_0.png"));
        textureBowser.setShineDamper(10);
        textureBowser.setReflectivity(1);
        TexturedModel texturedMOdelLuigiHouse = new TexturedModel(luigiHouseModel, textureLuigiHouse);
        entities.add(new Entity(texturedMOdelLuigiHouse, new Vector3f(50,0,-15),0,0,0,1f));
        collisions.add(new Vector3f(50,0,-15));
        radius.add(1f);

        // init player texture pack and Player object
        PlayerTexturePack playerTexturePack = new PlayerTexturePack(texture1, texture2, texture3);
        Player player = new Player(playerModel, new Vector3f(0,0,0),0,180f,0,1, playerTexturePack);
        player.setTexturePack(playerTexturePack);

        player.setObjects(collisions);
        player.setRadius(radius);

        //lighting
        Light light = new Light(new org.joml.Vector3f(3000,2000,2000), new org.joml.Vector3f(1f,1f,1f));

        //terrain
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("res/grassy.png"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("res/dirt.png"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("res/pinkFlowers.png"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("res/path.png"));

        // digabung dalam 1 pack texture2nya
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);

        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("res/grassy.png"));

        Terrain terrain = new Terrain(-1, -1, loader, texturePack, blendMap);
        Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain3 = new Terrain(1, -1, loader, texturePack, blendMap);
        Terrain terrain4 = new Terrain(0, -1, loader, texturePack, blendMap);
        Terrain terrain5 = new Terrain(0, 0, loader, texturePack, blendMap);
        Terrain terrain6 = new Terrain(0, 1, loader, texturePack, blendMap);
        Terrain terrain7 = new Terrain(1, -1, loader, texturePack, blendMap);
        Terrain terrain8 = new Terrain(1, 0, loader, texturePack, blendMap);
        Terrain terrain9 = new Terrain(1, 1, loader, texturePack, blendMap);

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
            //Jalankan player
            player.move();
            renderer.processPlayer(player);
//            renderer.processEntity(player);
//            System.out.println();
//            renderer.processMario(luigi);
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);
            renderer.processTerrain(terrain5);
            renderer.processTerrain(terrain6);
            renderer.processTerrain(terrain7);
            renderer.processTerrain(terrain8);
            renderer.processTerrain(terrain9);

            for (Entity entity1: entities){
                renderer.processEntity(entity1);
            }

            renderer.render(light, camera);

            DisplayManager.updateDisplay();
        }

        renderer.cleanUp();
        loader.cleanUp();

        DisplayManager.closeDisplay();
    }
}