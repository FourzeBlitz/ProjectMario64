package characters;

import Engine.Window;
import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjglx.util.vector.Vector3f;
import renderEngine.Loader;
import textures.MarioTexturePack;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import Engine.Window;


public class Mario {

    private MarioTexturePack texturePack;
    private TexturedModel model;
    private RawModel rawModel;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;


    public Mario(RawModel rawModel, Vector3f position, float rotX, float rotY, float rotZ, float scale, MarioTexturePack texturePack) {
        this.rawModel = rawModel;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
        this.texturePack = texturePack;
    }

    public void setTexturePack(MarioTexturePack texturePack) {
        this.texturePack = texturePack;
    }

    public MarioTexturePack getTexturePack() {
        return texturePack;
    }
    public RawModel getRawModel() {
        return rawModel;
    }
    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRotX() {
        return rotX;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    public float getRotY() {
        return rotY;
    }

    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    public float getRotZ() {
        return rotZ;
    }

    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
