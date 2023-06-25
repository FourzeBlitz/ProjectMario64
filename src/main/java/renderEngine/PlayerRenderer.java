package renderEngine;

import entities.Player;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import shaders.StaticShader;
import textures.ModelTexture;
import toolbox.Maths;

import java.util.List;
import java.util.Map;

/**
 * Handles the rendering of a model to the screen.
 *
 */
public class PlayerRenderer {

    private StaticShader shader;

    //ambil window dari main game loop
    public PlayerRenderer(StaticShader shader, Matrix4f projectionMatrix){
        this.shader = shader;

        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }


    public void render(Map<TexturedModel, List<Player>> players){
        for (TexturedModel model: players.keySet()){
            prepareTexturedModel(model);
            // get all the players that uses that textured model
            List<Player> batch = players.get(model);
            for (Player player: batch){
                prepareInstance(player);
                GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            unbindTexturedModel();
        }
    }

    private void prepareTexturedModel(TexturedModel model){
        RawModel rawModel = model.getRawModel();
        // Bind karena mau dipake
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0); //vertex
        GL20.glEnableVertexAttribArray(1); //texture
        GL20.glEnableVertexAttribArray(2); //normal

        // Light reflectivity dan damper on obj surface
        ModelTexture texture = model.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        // activate sampler 2d. By default di texture0
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
    }

    private void unbindTexturedModel(){
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void prepareInstance(Player player){
        // Player transformation
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(player.getPosition(),
                player.getRotX(), player.getRotY(), player.getRotZ(), player.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }

    /**
     * Renders a model to the screen.
     *
     * Before we can render a VAO it needs to be made active, and we can do this
     * by binding it. We also need to enable the relevant attributes of the VAO,
     * which in this case is just attribute 0 where we stored the position data.
     *
     * The VAO can then be rendered to the screen using glDrawArrays(). We tell
     * it what type of shapes to render and the number of vertices that it needs
     * to render.
     *
     * After rendering we unbind the VAO and disable the attribute.
     *
     * @param player
     *            - The model to be rendered.
     */
    public void render(Player player, StaticShader shader) {
        TexturedModel model = player.getModel();
        RawModel rawModel = model.getRawModel();
        // Bind karena mau dipake
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0); //vertex
        GL20.glEnableVertexAttribArray(1); //texture
        GL20.glEnableVertexAttribArray(2); //normal
        // Player transformation
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(player.getPosition(),
                player.getRotX(), player.getRotY(), player.getRotZ(), player.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        // Light reflectivity dan damper on obj surface
        ModelTexture texture = model.getTexture();
        shader.loadShineVariables(texture.getShineDamper(), texture.getReflectivity());
        // activate sampler 2d. By default di texture0
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());

        /**
         * param 1: cara gambar
         * param 2: number of vertices to render
         * param 3: tipe data dr indicesnya, karena index/indices nya integer jd pakai unsigned int
         * param 4: render data start drmn, start dr index 0
         */
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        // Unbind karena sudah selesai dipake
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }


}
