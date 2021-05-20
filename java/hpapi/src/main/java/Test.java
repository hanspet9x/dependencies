import hp.io.Console;
import hp.net.HttpFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class Test {

    static void post(){

        HttpFactory.Form form = new HttpFactory.Form();
        Iterable<byte[]> b = form.add("name", "ade").build();

        HttpFactory.setOnServerResponse(new HttpFactory.OnServerResponse() {
                                            @Override
                                            public void response(String data) {
                                                Console.log(data);
                                            }
                                        });
                HttpFactory.postForm("http://localhost:8080/test", b, HttpFactory.HttpResponses.TEXT);
    }

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


    public static void main(String[] args) throws IOException, MessagingException {
//        String [] address = {"payodeji7@gmail.com", "peterakinlolu1@gmail.com"};
//        String address1 = "payodeji7@gmail.com";
//        Mailer mailer = new Mailer("tawoltechnologies@gmail.com", address, "Testing", "hello there");
//        String username = "test@hanspet.com";
//        String password = "theyoungshallgrow";
//        mailer.testMail(username, password);
        new Thread(Test::post).start();
       //mailer.sendMessage(username, password);
        //mailer.sendAttachment(username, password, new File("C:\\Users\\payod\\OneDrive\\Pictures\\git\\init.PNG"));
    }
}
