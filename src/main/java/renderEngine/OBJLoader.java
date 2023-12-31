package renderEngine;

import models.RawModel;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OBJLoader {

    public static RawModel loadObjModel(String filePath, Loader loader) {
        List<String> lines;
        FileReader fr = null;
        try {
            fr = new FileReader(new File(filePath));
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("src/main/java/renderEngine/coba.txt"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //baca brp lines dalam file tsb
            List<String> list = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(fr)) {
                String line;
                while ((line = br.readLine()) != null) {
                    list.add(line);

                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            lines = list;
            try {
                writer.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not Found");
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        BufferedReader reader = new BufferedReader(fr);
//        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;


        try {
            for (String line : lines) {
//                line = reader.readLine();
                String[] currentLine = line.split("\\s+");
                if (line.startsWith("v ")) {
                    //vertices
                    Vector3f vertex = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    vertices.add(vertex);

                }
                // ambil vextex texturenya
                else if (line.startsWith("vt ")) {
                    Vector2f textureVec = new Vector2f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2])
                    );
                    textures.add(textureVec);

                }
                // ambil vertex normalnya (kalau ada vertex normal di file obj nya pake ini aja)
                else if (line.startsWith("vn ")) {
                    //vertex normal
                    Vector3f normalVec = new Vector3f(
                            Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3])
                    );
                    normals.add(normalVec);

                }
            }

            textureArray = new float[vertices.size() * 2];
            normalsArray = new float[vertices.size() * 3];

            for (String line : lines) {
                // face
                if (line.startsWith("f ")) {
                    String[] currentLine = line.split(" ");
                    String[] vertex1 = currentLine[1].split("/"); // v
                    String[] vertex2 = currentLine[2].split("/"); // vt
                    String[] vertex3 = currentLine[3].split("/"); // vn

                    processVertex(vertex1, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex2, indices, textures, normals, textureArray, normalsArray);
                    processVertex(vertex3, indices, textures, normals, textureArray, normalsArray);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }

        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
//        System.out.println(Arrays.toString(verticesArray));
//        System.out.println(Arrays.toString(textureArray));
//        System.out.println(Arrays.toString(normalsArray));
//        System.out.println(Arrays.toString(indicesArray));
        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);


    }

    private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
                                      List<Vector3f> normals, float[] textureArrays, float[] normalsArrays) {
        //vertices
//        System.out.println(Arrays.toString(vertexData));
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);

        //texture
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArrays[currentVertexPointer * 2] = currentTexture.x;
        textureArrays[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;

        //normals
        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArrays[currentVertexPointer * 3] = currentNorm.x;
        normalsArrays[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArrays[currentVertexPointer * 3 + 2] = currentNorm.z;
    }
}
