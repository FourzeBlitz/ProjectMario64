package renderEngine;

import characters.Mario;
import characters.Player;
import entities.Camera;
import entities.Entity;
import entities.Light;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import org.lwjgl.util.vector.Matrix4f;
import shaders.MarioShader;
import shaders.PlayerShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrains.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000;
    private Matrix4f projectionMatrix;
    private StaticShader shader = new StaticShader();
    private EntityRenderer entityRenderer;
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader = new TerrainShader();

    private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
    private List<Terrain> terrains = new ArrayList<Terrain>();
    private SkyboxRenderer skyboxRenderer;

    private MarioRenderer marioRenderer;
    private PlayerRenderer playerRenderer;

    private MarioShader marioShader = new MarioShader();
    private PlayerShader playerShader = new PlayerShader();
    private List<Mario> marios = new ArrayList<>();
    private List<Player> players = new ArrayList<>();

//    private Map<TexturedModel, List<Player>> players = new HashMap<TexturedModel, List<Player>>();


    public MasterRenderer(Loader loader) {
//        GL11.glEnable(GL11.GL_CULL_FACE);
//         GL_FRONT, GL_BACK ini menyesuaikan. Bagian depan mario malah back ternyata, frontnya blkg
//        GL11.glCullFace(GL11.GL_FRONT);
        createProjectionMatrix();
        entityRenderer = new EntityRenderer(shader, projectionMatrix);
        marioRenderer = new MarioRenderer(marioShader, projectionMatrix);
        playerRenderer = new PlayerRenderer(playerShader, projectionMatrix);

        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
        skyboxRenderer =new SkyboxRenderer(loader,projectionMatrix);
    }

    public void render(Light sun, Camera camera) {
        prepare();
        // entity
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        entityRenderer.render(entities);
        playerRenderer.render(players);
        marioRenderer.render(marios);
        shader.stop();
        // player
        playerShader.start();
        playerShader.loadLight(sun);
        playerShader.loadViewMatrix(camera);

        playerShader.stop();
        // mario
        marioShader.start();
        marioShader.loadLight(sun);
        marioShader.loadViewMatrix(camera);
        marioShader.stop();

        // terrain
        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        terrains.clear();
        skyboxRenderer.render(camera);
        entities.clear();
        players.clear();
    }

    public void processTerrain(Terrain terrain) {
        terrains.add(terrain);
    }

    public void processMario(Mario mario) {
        marios.add(mario);
    }
    public void processPlayer(Player player) {
        players.add(player);
    }


    public void processEntity(Entity entity) {
        TexturedModel entityModel = entity.getModel();
        // which texturedModel is that entity using
        List<Entity> batch = entities.get(entityModel);
        if (batch != null) {
            batch.add(entity);
        } else {
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }

//    public void processPlayer(Player player){
//        TexturedModel playerModel = player.getModel();
//        // which texturedmodel is that entity using
//        List<Player> batch = players.get(playerModel);
//        if(batch!=null){
//            batch.add(player);
//        }else {
//            List<Player> newBatch = new ArrayList<>();
//            newBatch.add(player);
//            players.put(playerModel, newBatch);
//        }
//    }

    /**
     * This method must be called each frame, before any rendering is carried
     * out. It basically clears the screen of everything that was rendered last
     * frame (using the glClear() method). The glClearColor() method determines
     * the colour that it uses to clear the screen. In this example it makes the
     * entire screen red at the start of each frame.
     */
    public void prepare() {
        // depth biar tau triangle mana yg hrs dirender dluan trs hrs diclear setiap frame
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        // warna bg
        GL11.glClearColor(0f, 0f, 1f, 1);
    }

    private void createProjectionMatrix() {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }

    public void cleanUp() {
        shader.cleanUp();
        terrainShader.cleanUp();
    }

}
