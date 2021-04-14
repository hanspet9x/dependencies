import io.Terminal;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

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
        System.out.println(Terminal.execute("assoc"));

    }
}
