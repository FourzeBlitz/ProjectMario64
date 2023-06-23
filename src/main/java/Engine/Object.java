package Engine;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Object extends ShaderProgram {
    List<Vector3f> vertices = new ArrayList<>();
    List<Vector2f> textures = new ArrayList<>();
    List<Vector3f> normal = new ArrayList<>();
    List<Integer> indicies = new ArrayList<>();
    int[] textureIDs;

    int vao;
    int vbo;
    UniformsMap uniformsMap;
    Vector4f color;

    Matrix4f model;

    int vboColor;
    int nbo, vtbo;
    List<Object> childObject;
    List<Float> centerPoint;

    public List<Vector3f> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vector3f> vertices) {
        this.vertices = vertices;
        setupVAOVBO();
    }

    public List<Vector3f> getNormal() {
        return normal;
    }

    public void setNormal(List<Vector3f> normals) {
        this.normal = normals;
        setupVAOVBO();
    }

    public List<Integer> getIndicies() {
        return indicies;
    }

    public void setIndicies(List<Integer> indicies) {
        this.indicies = indicies;
        setupVAOVBO();
    }

    public List<Vector2f> getTextures() {
        return textures;
    }

    public void setTextures(List<Vector2f> textures) {
        this.textures = textures;
        setupVAOVBO();
    }

    public List<Object> getChildObject() {
        return childObject;
    }

    public void setChildObject(List<Object> childObject) {
        this.childObject = childObject;
    }

    public List<Float> getCenterPoint() {
        updateCenterPoint();
        return centerPoint;
    }

    public void setCenterPoint(List<Float> centerPoint) {
        this.centerPoint = centerPoint;
    }

    List<Vector3f> verticesColor;

    public Object(List<ShaderModuleData> shaderModuleDataList
            , List<Vector3f> vertices
            , Vector4f color) {
        super(shaderModuleDataList);
        this.vertices = vertices;
//        setupVAOVBO();
        uniformsMap = new UniformsMap(getProgramId());
//        uniformsMap.createUniform(
//                "uni_color");
//        uniformsMap.createUniform(
//                "model");
//        uniformsMap.createUniform(
//                "projection");
//        uniformsMap.createUniform(
//                "view");
        this.color = color;
        model = new Matrix4f().identity();
        childObject = new ArrayList<>();
        centerPoint = Arrays.asList(0f, 0f, 0f);
    }

    public Object(List<ShaderModuleData> shaderModuleDataList,
                  List<Vector3f> vertices,
                  List<Vector3f> verticesColor) {
        super(shaderModuleDataList);
        this.vertices = vertices;
        this.verticesColor = verticesColor;
        setupVAOVBOWithVerticesColor();
    }

    public void setupVAOVBO() {
        //set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //set vbo (vertex positions biasa)
        glEnableVertexAttribArray(0);
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(vertices),
                GL_STATIC_DRAW);

        glEnable(GL_TEXTURE_2D);

        // Load and bind the texture image
        textureIDs = new int[3];
        textureIDs[0] = loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_1.png", 0);
        textureIDs[1] = loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_2.png", 1);
        textureIDs[2] = loadTexture("resources/model/GameCube - Mario Superstar Baseball - Mario/Mario/mario_3.png", 2);

        //set vtbo (vertex texture positions)
        glEnableVertexAttribArray(1);
        vtbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vtbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listToFloatTexture(textures),
                GL_STATIC_DRAW);

        //set nbo (vertex normal)
        glEnableVertexAttribArray(2);
        nbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, nbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(normal),
                GL_STATIC_DRAW);

        // Create EBO for face indices
        int ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.listoInt(indicies), GL_STATIC_DRAW);

        //        uniformsMap.createUniform("lightColor");
        //        uniformsMap.createUniform("lightPos");
    }

    public void setupVAOVBOWithVerticesColor() {
        //set vao
        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        //set vbo
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(vertices),
                GL_STATIC_DRAW);

        //set vboColor
        vboColor = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glBufferData(GL_ARRAY_BUFFER,
                Utils.listoFloat(verticesColor),
                GL_STATIC_DRAW);
    }

    public void drawSetup(Camera camera, Projection projection) {
        bind();
        uniformsMap.setUniform(
                "uni_color", color);
        uniformsMap.setUniform(
                "model", model);
        uniformsMap.setUniform(
                "view", camera.getViewMatrix());
        uniformsMap.setUniform(
                "projection", projection.getProjMatrix());
        uniformsMap.setUniformTexture("ourTexture0", 0);
//        uniformsMap.setUniformTexture("ourTexture1", 1);
//        uniformsMap.setUniformTexture("ourTexture2", 2);
        uniformsMap.setUniform("brightnessFactor", 1f);
        // Bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3,
                GL_FLOAT,
                false,
                0, 0);
        //directional Light
        uniformsMap.setUniform("dirLight.direction", new Vector3f(-0.2f, -1.0f, -0.3f));
        uniformsMap.setUniform("dirLight.ambient", new Vector3f(0.05f, 0.05f, 0.05f));
        uniformsMap.setUniform("dirLight.diffuse", new Vector3f(0.4f, 0.4f, 0.4f));
        uniformsMap.setUniform("dirLight.specular", new Vector3f(0.5f, 0.5f, 0.5f));
        //posisi pointLight
        Vector3f[] _pointLightPositions = {
                new Vector3f(0.7f, 0.2f, 2.0f),
                new Vector3f(2.3f, -3.3f, -4.0f),
                new Vector3f(-4.0f, 2.0f, -12.0f),
                new Vector3f(0.0f, 0.0f, -3.0f)
        };
        for (int i = 0; i < _pointLightPositions.length; i++) {
            uniformsMap.setUniform("pointLights[" + i + "].position", _pointLightPositions[i]);
            uniformsMap.setUniform("pointLights[" + i + "].ambient", new Vector3f(0.05f, 0.05f, 0.05f));
            uniformsMap.setUniform("pointLights[" + i + "].diffuse", new Vector3f(0.8f, 0.8f, 0.8f));
            uniformsMap.setUniform("pointLights[" + i + "].specular", new Vector3f(1.0f, 1.0f, 1.0f));
            uniformsMap.setUniform("pointLights[" + i + "].constant", 1.0f);
            uniformsMap.setUniform("pointLights[" + i + "].linear", 0.09f);
            uniformsMap.setUniform("pointLights[" + i + "].quadratic", 0.032f);
        }
        //spotlight
        uniformsMap.setUniform("spotLight.position", camera.getPosition());
        uniformsMap.setUniform("spotLight.direction", camera.getDirection());
        uniformsMap.setUniform("spotLight.ambient", new Vector3f(0.0f, 0.0f, 0.0f));
        uniformsMap.setUniform("spotLight.diffuse", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.specular", new Vector3f(1.0f, 1.0f, 1.0f));
        uniformsMap.setUniform("spotLight.constant", 1.0f);
        uniformsMap.setUniform("spotLight.linear", 0.09f);
        uniformsMap.setUniform("spotLight.quadratic", 0.032f);
        uniformsMap.setUniform("spotLight.cutOff", (float) Math.cos(Math.toRadians(12.5f)));
        uniformsMap.setUniform("spotLight.outerCutOff", (float) Math.cos(Math.toRadians(12.5f)));

        uniformsMap.setUniform("viewPos", camera.getPosition());
    }

    public void drawSetupWithVerticesColor() {
        bind();
        // Bind VBO
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3,
                GL_FLOAT,
                false,
                0, 0);

        // Bind VBOColor
        glEnableVertexAttribArray(1);
        glBindBuffer(GL_ARRAY_BUFFER, vboColor);
        glVertexAttribPointer(1, 3,
                GL_FLOAT,
                false,
                0, 0);
    }

    public void draw(Camera camera, Projection projection) {
        drawSetup(camera, projection);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_TRIANGLES,
                0,
                vertices.size());
        for (Object child : childObject) {
            child.draw(camera, projection);
        }
    }

    public void drawWithInput(Camera camera, Projection projection, int draw) {
        drawSetup(camera, projection);
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
//        glDrawArrays(draw,0,vertices.size());
        // gtw knp pake bawah malah ga bisa
        glDrawElements(draw, indicies.size(), GL_UNSIGNED_INT, 0);
        for(Object child:childObject){
            child.drawWithInput(camera,projection, draw);
        }
    }

    public void drawWithVerticesColor() {
        drawSetupWithVerticesColor();
        // Draw the vertices
        //optional
        glLineWidth(10); //ketebalan garis
        glPointSize(10); //besar kecil vertex
        //wajib
        //GL_LINES
        //GL_LINE_STRIP
        //GL_LINE_LOOP
        //GL_TRIANGLES
        //GL_TRIANGLE_FAN
        //GL_POINT
        glDrawArrays(GL_TRIANGLES,
                0,
                vertices.size());
    }

    //    public void drawLine(){
//        drawSetup();
//        // Draw the vertices
//        //optional
//        glLineWidth(1); //ketebalan garis
//        glPointSize(1); //besar kecil vertex
//        glDrawArrays(GL_LINE_STRIP,
//                0,
//                vertices.size());
//    }
    public void addVertices(Vector3f newVertices) {
        vertices.add(newVertices);
        setupVAOVBO();
    }

    public void translateObject(Float offsetX, Float offsetY, Float offsetZ) {
        model = new Matrix4f().translate(offsetX, offsetY, offsetZ).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Object child : childObject) {
            child.translateObject(offsetX, offsetY, offsetZ);
        }
    }

    public void rotateObject(Float degree, Float x, Float y, Float z) {
        model = new Matrix4f().rotate(degree, x, y, z).mul(new Matrix4f(model));
        updateCenterPoint();
        for (Object child : childObject) {
            child.rotateObject(degree, x, y, z);
        }

    }

    public void updateCenterPoint() {
        Vector3f destTemp = new Vector3f();
        model.transformPosition(0.0f, 0.0f, 0.0f, destTemp);
        centerPoint.set(0, destTemp.x);
        centerPoint.set(1, destTemp.y);
        centerPoint.set(2, destTemp.z);
        System.out.println(centerPoint.get(0) + " " + centerPoint.get(1));
    }

    public void scaleObject(Float scaleX, Float scaleY, Float scaleZ) {
        model = new Matrix4f().scale(scaleX, scaleY, scaleZ).mul(new Matrix4f(model));
        for (Object child : childObject) {
            child.translateObject(scaleX, scaleY, scaleZ);
        }
    }

    /**
     * Setiap texture image dr obj diload
     * @param filePath filepath dari texture image tsb
     * @return texture id nya direturn trs disimpan ke dlm array textureIDs
     */
    public static int loadTexture(String filePath, int textureUnit) {
        // Generate a texture ID
        int textureID = glGenTextures();

        // Bind the texture unit
        glActiveTexture(GL_TEXTURE0 + textureUnit);

        // Bind the texture
        glBindTexture(GL_TEXTURE_2D, textureID);

        // Set texture parameters (gtw ngaruh apa ga)
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        // Load image data into ByteBuffer using STBImage (you can replace this with your preferred image loading library)
        int[] width = new int[1];
        int[] height = new int[1];
        int[] channels = new int[1];
        ByteBuffer imageBuffer = STBImage.stbi_load(filePath, width, height, channels, 4);

        // Upload image data to the texture
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width[0], height[0], 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);

        // Generate mipmaps (optional)
        GL30.glGenerateMipmap(GL_TEXTURE_2D);

        // Free the image buffer
        STBImage.stbi_image_free(imageBuffer);

        // Unbind the texture
//        glBindTexture(GL_TEXTURE_2D, 0);

        return textureID;
    }

}