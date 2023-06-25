package renderEngine;

import characters.Player;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import shaders.PlayerShader;
import textures.PlayerTexturePack;
import toolbox.Maths;

import java.util.List;

public class PlayerRenderer{

    private PlayerShader shader;

    public PlayerRenderer(PlayerShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(Player player) {
        preparePlayer(player);
        loadModelMatrix(player);
        GL11.glDrawElements(GL11.GL_TRIANGLES, player.getRawModel().getVertexCount(),
                GL11.GL_UNSIGNED_INT, 0);
        unbindTexturedModel();
    }

    public void render(List<Player> players) {
        for (Player player : players) {
            preparePlayer(player);
            loadModelMatrix(player);
            GL11.glDrawElements(GL11.GL_TRIANGLES, player.getRawModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void preparePlayer(Player player) {
        RawModel rawModel = player.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        bindTextures(player);
        shader.loadShineVariables(99999, 0);
    }

    private void bindTextures(Player player) {
        PlayerTexturePack texturePack = player.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture1().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture2().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture3().getTextureID());

    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Player player) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(player.getPosition(),
                player.getRotX(), player.getRotY(), player.getRotZ(), player.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }


}
