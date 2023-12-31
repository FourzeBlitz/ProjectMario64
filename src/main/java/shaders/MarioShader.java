package shaders;


import entities.Camera;
import entities.Light;

import org.lwjgl.util.vector.Matrix4f;
import toolbox.Maths;

public class MarioShader extends ShaderProgram{


    private static final String VERTEX_FILE = "src/main/java/shaders/marioVertexShader.vert";
    private static final String FRAGMENT_FILE = "src/main/java/shaders/marioFragmentShader.frag";

    private int location_transformationMatrix;
    private int location_projectionMatrix;
    private int location_viewMatrix;
    private int location_lightPosition;
    private int location_lightColour;
    private int location_shineDamper;
    private int location_reflectivity;
    private int location_texture1;
    private int location_texture2;
    private int location_texture3;
    private int location_texture4;
    private int location_texture5;
    private int location_texture6;
    private int location_texture7;
    private int location_texture8;
    private int location_texture9;
    private int location_texture10;

    public MarioShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textureCoordinates");
        super.bindAttribute(2, "normal");
    }

    @Override
    protected void getAllUniformLocations() {
        location_transformationMatrix = super.getUniformLocation("transformationMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_viewMatrix = super.getUniformLocation("viewMatrix");
        location_lightPosition = super.getUniformLocation("lightPosition");
        location_lightColour = super.getUniformLocation("lightColour");
        location_shineDamper = super.getUniformLocation("shineDamper");
        location_reflectivity = super.getUniformLocation("reflectivity");
        location_texture1 = super.getUniformLocation("texture1");
        location_texture2 = super.getUniformLocation("texture2");
        location_texture3 = super.getUniformLocation("texture3");

    }

    public void connectTextureUnits(){
        super.loadInt(location_texture1, 0);
        super.loadInt(location_texture2, 1);
        super.loadInt(location_texture3, 3);

    }

    public void loadShineVariables(float damper,float reflectivity){
        super.loadFloat(location_shineDamper, damper);
        super.loadFloat(location_reflectivity, reflectivity);
    }

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadLight(Light light){
        super.loadVector(location_lightPosition, light.getPosition());
        super.loadVector(location_lightColour, light.getColour());
    }

    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        super.loadMatrix(location_viewMatrix, viewMatrix);
    }

    public void loadProjectionMatrix(Matrix4f projection){
        super.loadMatrix(location_projectionMatrix, projection);
    }


}
