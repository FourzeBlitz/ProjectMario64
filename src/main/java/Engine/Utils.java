//package Engine;
//import org.joml.Vector2f;
//import org.joml.Vector3f;
//
//import java.io.IOException;
//import java.nio.file.*;
//import java.util.List;
//
//public class Utils {
//
//    public static String readFile(String filePath) {
//        String str;
//        try {
//            str = new String(Files.readAllBytes(Paths.get(filePath)));
//        } catch (IOException excp) {
//            throw new RuntimeException("Error reading file [" + filePath + "]", excp);
//        }
//        return str;
//    }
//
//    public static float[] listoFloat(List<Vector3f> arraylist){
//        float[] arr = new float[arraylist.size()*3];
//        int index = 0;
//        for(int i = 0;i<arraylist.size();i++){
//            arr[index++] = arraylist.get(i).x;
//            arr[index++] = arraylist.get(i).y;
//            arr[index++] = arraylist.get(i).z;
//        }
//        return arr;
//    }
//
//    public static float[] listToFloatTexture(List<Vector2f> list) {
//        float[] array = new float[list.size() * 2];
//        for (int i = 0; i < list.size(); i++) {
//            Vector2f vector = list.get(i);
//            array[i * 2] = vector.x;
//            array[i * 2 + 1] = vector.y;
//        }
//        return array;
//    }
//
//
//    public static int[] listoInt(List<Integer> arraylist){
//        int[] arr = new int[arraylist.size()];
//        for(int i = 0;i<arraylist.size();i++){
//            arr[i] = arraylist.get(i);
//        }
//        return arr;
//    }
//
//
//}
