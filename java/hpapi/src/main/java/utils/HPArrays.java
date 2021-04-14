package utils;

public class HPArrays {

    public static String toString(Object ...objects){
        String data = java.util.Arrays.toString(objects);
        return data.substring(1, data.length()-1);
    }

    public static String toString(String ...objects){
        String data = java.util.Arrays.toString(objects);
        return data.substring(1, data.length()-1);
    }
}
