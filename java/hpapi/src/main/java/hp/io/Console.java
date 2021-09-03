package hp.io;

import com.google.gson.Gson;

public class Console {

    public static void  log(Class className, Object ...os){
        System.out.println(className.getName());
        for (int i = 0; i < os.length; i++) {
            System.out.print(os[i]);
            if(i < os.length-1){
                System.out.print(", ");
            }
        }
        System.out.println();
    }


    public static void log(Object ...os){

        for (int i = 0; i < os.length; i++) {
            System.out.print(os[i]);
            if(i < os.length-1){
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void log(Object bean){
        System.out.println(new Gson().toJson(bean));
    }

    public static void error(Object ...os){


        for (int i = 0; i < os.length; i++) {
            System.err.print(os[i]);
            if(i < os.length-1){
                System.err.print(", ");
            }
        }
        System.out.println();
    }
}
