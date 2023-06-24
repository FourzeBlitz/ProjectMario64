package textures;

public class ModelTexture {

    private int textureID;
    // jarak supaya camera ttp dpt light reflection
    private float shineDamper = 1;
    private float reflectiviy = 0;

    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }
    public int getTextureID() {
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectiviy() {
        return reflectiviy;
    }

    public void setReflectiviy(float reflectiviy) {
        this.reflectiviy = reflectiviy;
    }
}
