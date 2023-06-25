package renderEngine;

import characters.Mario;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjglx.util.vector.Matrix4f;
import org.lwjglx.util.vector.Vector3f;
import shaders.MarioShader;
import shaders.StaticShader;
import terrains.Terrain;
import textures.MarioTexturePack;
import toolbox.Maths;

import java.util.List;
import java.util.Map;

public class MarioRenderer{

    private MarioShader shader;

    public MarioRenderer(MarioShader shader, Matrix4f projectionMatrix) {
        this.shader = shader;
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.connectTextureUnits();
        shader.stop();
    }

    public void render(Mario mario) {
        prepareMario(mario);
        loadModelMatrix(mario);
        GL11.glDrawElements(GL11.GL_TRIANGLES, mario.getRawModel().getVertexCount(),
                GL11.GL_UNSIGNED_INT, 0);
        unbindTexturedModel();
    }

    public void render(List<Mario> marios) {
        for (Mario mario : marios) {
            prepareMario(mario);
            loadModelMatrix(mario);
            GL11.glDrawElements(GL11.GL_TRIANGLES, mario.getRawModel().getVertexCount(),
                    GL11.GL_UNSIGNED_INT, 0);
            unbindTexturedModel();
        }
    }

    private void prepareMario(Mario mario) {
        RawModel rawModel = mario.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        bindTextures(mario);
        shader.loadShineVariables(10, 1);
    }

    private void bindTextures(Mario mario) {
        MarioTexturePack texturePack = mario.getTexturePack();
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture1().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE1);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture2().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE2);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture3().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE3);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture1().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE4);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture2().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE5);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture3().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE6);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture3().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE7);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture3().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE8);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture1().getTextureID());
        GL13.glActiveTexture(GL13.GL_TEXTURE9);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturePack.getTexture2().getTextureID());
    }

    private void unbindTexturedModel() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    private void loadModelMatrix(Mario mario) {
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(mario.getPosition(),
                mario.getRotX(), mario.getRotY(), mario.getRotZ(), mario.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
    }


}
