//package Engine;
//
//import org.joml.Vector2f;
//import org.joml.Vector3f;
//
//import java.io.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.lwjgl.assimp.*;
//
//
//public class ObjectLoader {
//    List<String> lines;
//    public List<Vector3f> vertices = new ArrayList<>();
//    public List<Vector3f> normals = new ArrayList<>();
//    public List<Vector2f> textures = new ArrayList<>();
//    public List<Integer> indicies = new ArrayList<>();
//    float[] normalsArrays = null;
//    float[] textureArrays = null;
//    AIScene scene;
//    private List<Integer> vaos = new ArrayList<>();
//    private List<Integer> vbos = new ArrayList<>();
////    private List<Integer> textures = new ArrayList<>();
//
//    /**
//     * function untuk baca file obj/fbx kemudian memanggil type loadernya sesuai dgn extenstion filenya
//     * Jika file adalah obj maka dibaca loadObjFiles() begitu juga dengan fbx
//     * @param fileName
//     * @param type
//     */
//    public ObjectLoader(String fileName, String type) {
//        if (type.equalsIgnoreCase("obj")) {
//            //obj
//            List<String> list = new ArrayList<>();
//            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    list.add(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            lines = list;
//            loadObjFiles();
//        } else if (type.equalsIgnoreCase("fbx")) {
//            //fbx
//            scene = Assimp.aiImportFile(fileName, Assimp.aiProcess_Triangulate | Assimp.aiProcess_FlipUVs);
//            loadFbxFiles();
//        }
//    }
//
//    public void loadFbxFiles() {
//        int numMeshes = scene.mNumMeshes();
//        for (int x = 0; x < numMeshes; x++) { //kalo ada beberapa model
//            AIMesh mesh = AIMesh.create(scene.mMeshes().get(x));
//
//            // vertices
//            AIVector3D.Buffer verticesBuffer = mesh.mVertices();
//            int numVertices = mesh.mNumVertices();
//
//
//            for (int i = 0; i < numVertices; i++) {
//                AIVector3D vertex = verticesBuffer.get(i);
//                Vector3f verticesVec = new Vector3f(vertex.x(), vertex.y(), vertex.z());
//                vertices.add(verticesVec);
//            }
//
//            //  normal
//            AIVector3D.Buffer normalsBuffer = mesh.mNormals();
//            int numNormals = mesh.mNumVertices();
//
//            for (int i = 0; i < numNormals; i++) {
//                AIVector3D vertex = normalsBuffer.get(i);
//                Vector3f verticesVec = new Vector3f(vertex.x(), vertex.y(), vertex.z());
//                normals.add(verticesVec);
//            }
//
//            //indices
//            AIFace.Buffer facesBuffer = mesh.mFaces();
//            int numFaces = mesh.mNumFaces();
//
//            for (int i = 0; i < numFaces; i++) {
//                AIFace face = facesBuffer.get(i);
//                int numIndices = face.mNumIndices();
//                for (int j = 0; j < numIndices; j++) {
//                    int index = face.mIndices().get(j);
//                    indicies.add(index);
//                }
//                System.out.println();
//            }
//        }
//
//    }
//
//    public void loadObjFiles() {
//        // baca filenya
//        for (String line : lines) {
//            String[] tokens = line.split("\\s+");
//            // ambil geometric vertexnya
//            if (line.startsWith("v ")) {
//                //vertices
//                Vector3f verticesVec = new Vector3f(
//                        Float.parseFloat(tokens[1]),
//                        Float.parseFloat(tokens[2]),
//                        Float.parseFloat(tokens[3])
//                );
//                vertices.add(verticesVec);
//            }
//            // ambil vextex texturenya
//            else if (line.startsWith("vt ")) {
//                Vector2f textureVec = new Vector2f(
//                        Float.parseFloat(tokens[1]),
//                        Float.parseFloat(tokens[2])
//                );
//                textures.add(textureVec);
//            }
////             ambil vertex normalnya (kalau ada vertex normal di file obj nya pake ini aja)
//            else if (line.startsWith("vn ")) {
//                //vertex normal
//                Vector3f normalVec = new Vector3f(
//                        Float.parseFloat(tokens[1]),
//                        Float.parseFloat(tokens[2]),
//                        Float.parseFloat(tokens[3])
//                );
//                normals.add(normalVec);
//            }
//
//        }
//
//        // buat simpan hasilnya
//        textureArrays = new float[vertices.size() * 2];
//        normalsArrays = new float[vertices.size() * 3];
//
//        // baca facenya v/vt/vn untuk menentukan urutan gambar
//        for (String line : lines) {
//            if (line.startsWith("f")) {
//                String[] currentLine = line.split(" ");
//                String[] vertex1 = currentLine[1].split("/"); // v
//                String[] vertex2 = currentLine[2].split("/"); // vt
//                String[] vertex3 = currentLine[3].split("/"); // vn
//
//                // kalau baca file obj indiciesnya kosong. Diisi waktu processVertex
//                processVertex(vertex1, indicies, textures, normals, textureArrays, normalsArrays);
//                processVertex(vertex2, indicies, textures, normals, textureArrays, normalsArrays);
//                processVertex(vertex3, indicies, textures, normals, textureArrays, normalsArrays);
//            }
//        }
//
//    }
//
//    /**
//     * Setiap face akan dibaca vertex, texture, dan normalnya
//     * @param vertexData face nya
//     * @param indicies urutan gambar
//     * @param textures list texture dari obj tersebut
//     * @param normals list normal vertex dari obj tersebut
//     * @param textureArrays list kosong utk simpan hasil
//     * @param normalsArrays list kosong utk simpan hasil
//     */
//    public static void processVertex(String[] vertexData, List<Integer> indicies, List<Vector2f> textures,
//                                     List<Vector3f> normals, float[] textureArrays, float[] normalsArrays) {
//        //vertices
//        System.out.println(Arrays.toString(vertexData));
//        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
//        indicies.add(currentVertexPointer);
//
//        //texture
//        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1]) - 1);
//        textureArrays[currentVertexPointer * 2] = currentTexture.x;
//        textureArrays[currentVertexPointer * 2 + 1] = 1 - currentTexture.y;
//
//        //normals
//        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
//        normalsArrays[currentVertexPointer * 3] = currentNorm.x;
//        normalsArrays[currentVertexPointer * 3 + 1] = currentNorm.y;
//        normalsArrays[currentVertexPointer * 3 + 2] = currentNorm.z;
//    }
//
////    public Model loadModel(float[] vertices, float[] textureCoords, int[] indicies) {
////        int id = createVAO();
////        storeIndiciesBuffer(indicies);
////        storeDataInAttribList(0, 3, vertices);
////        storeDataInAttribList(1, 2, vertices);
////        unbind();
////        return new Model(id, indicies.length / 3);
////    }
//
////    public int loadTexture(String filename) throws Exception {
////        int width, height;
////        ByteBuffer buffer;
////        try (MemoryStack stack = MemoryStack.stackPush()) {
////            IntBuffer w = stack.mallocInt(1);
////            IntBuffer h = stack.mallocInt(1);
////            IntBuffer c = stack.mallocInt(1);
////
////            buffer = STBImage.stbi_load(filename, w, h, c, 4);
////            if (buffer == null)
////                throw new Exception("Image File " + filename + " not loaded " + STBImage.stbi_failure_reason());
////
////            width = w.get();
////            height = h.get();
////        }
////        int id = glGenTextures();
////        textures.add(id);
////        glBindTexture(GL_TEXTURE_2D, id);
////        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
////        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
////        glGenerateMipmap(GL_TEXTURE_2D);
////        STBImage.stbi_image_free(buffer);
////        return id;
////    }
//
////    private int createVAO() {
////        int id = glGenVertexArrays();
////        vaos.add(id);
////        glBindVertexArray(id);
////        return id;
////    }
//
////    private void storeIndiciesBuffer(int[] indicies) {
////        int vbo = glGenBuffers();
////        vbos.add(vbo);
////        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
////        IntBuffer buffer = storeDataInIntBuffer(indicies);
////        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
////    }
//
////    private void storeDataInAttribList(int attribNo, int vertexCount, float[] data) {
////        int vbo = glGenBuffers();
////        vbos.add(vbo);
////        glBindBuffer(GL_ARRAY_BUFFER, vbo);
////        FloatBuffer buffer = storeDataInFloatBuffer(data);
////        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
////        glVertexAttribPointer(attribNo, vertexCount, GL_FLOAT, false, 0, 0);
////        glBindBuffer(GL_ARRAY_BUFFER, 0);
////    }
////
////    public void unbind() {
////        glBindVertexArray(0);
////    }
//
////    public void cleanup() {
////        for (int vao : vaos)
////            glDeleteVertexArrays(vao);
////        for (int vbo : vbos)
////            glDeleteBuffers(vbo);
////        for (int texture : textures)
////            glDeleteBuffers(texture);
////    }
//
////    public static String loadResource(String filename) throws Exception {
////        String result;
////        try (InputStream in = Utils.class.getResourceAsStream(filename);
////             Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
////            result = scanner.useDelimiter("\\A").next();
////        }
////        return result;
////    }
//
////    public static FloatBuffer storeDataInFloatBuffer(float[] data) {
////        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
////        buffer.put(data).flip();
////        return buffer;
////    }
////
////    public static IntBuffer storeDataInIntBuffer(int[] data) {
////        IntBuffer buffer = MemoryUtil.memAllocInt(data.length);
////        buffer.put(data).flip();
////        return buffer;
////    }
//
//}
