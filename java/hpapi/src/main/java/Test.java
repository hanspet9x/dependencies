import io.Terminal;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.CompletableFuture;

public class Test {

    static CompletableFuture<String> testCompletable(){

        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        completableFuture.completeAsync(() -> {

            /*try {
                Thread.sleep(1000);
                return "Ade";
            } catch (InterruptedException e) {
                e.printStackTrace();
                return "Tola";
            }*/

            return "Tola";
        });
        return completableFuture;
    }


    public static void main(String[] args) throws IOException {
        Terminal.deleteFolder("C:\\Users\\payod\\OneDrive\\Documents\\books\\tola");
    }
}
