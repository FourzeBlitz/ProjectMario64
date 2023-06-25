package textures;

public class PlayerTexturePack {

    private ModelTexture texture1;
    private ModelTexture texture2;
    private ModelTexture texture3;
//    private ModelTexture texture4;
//    private ModelTexture texture5;
//    private ModelTexture texture6;
//    private ModelTexture texture7;
//    private ModelTexture texture8;
//    private ModelTexture texture9;
//    private ModelTexture texture10;

    public PlayerTexturePack(ModelTexture texture1, ModelTexture texture2, ModelTexture texture3) {
        this.texture1 = texture1;
        this.texture2 = texture2;
        this.texture3 = texture3;
    }

    public ModelTexture getTexture1() {
        return texture1;
    }

    public ModelTexture getTexture2() {
        return texture2;
    }

    public ModelTexture getTexture3() {
        return texture3;
    }


}
