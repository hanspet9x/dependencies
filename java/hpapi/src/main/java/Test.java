import hp.io.Console;
import hp.net.HttpFactory;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Test {

    public static class AccessTokenReq {
        private String username;
        private String password;

        public AccessTokenReq(String publicKey, String privateKey) {
            this.username = publicKey;
            this.password = privateKey;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
    public static class AccessToken {
        private String status;
        private String message;
        private List<AccessTokenData> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<AccessTokenData> getData() {
            return data;
        }

        public void setData(List<AccessTokenData> data) {
            this.data = data;
        }

        private class AccessTokenData {
            private String accessToken;
            private String expiresIn;

            public String getAccessToken() {
                return accessToken;
            }

            public void setAccessToken(String accessToken) {
                this.accessToken = accessToken;
            }

            public String getExpiresIn() {
                return expiresIn;
            }

            public void setExpiresIn(String expiresIn) {
                this.expiresIn = expiresIn;
            }
        }
    }

    static void post() {

/*        HttpFactory.Form form = new HttpFactory.Form();
        Iterable<byte[]> b = form.add("name", "ade").build();

        HttpFactory.setOnServerResponse(new HttpFactory.OnServerResponse() {
            @Override
            public void response(String data) {
                Console.log(data);
            }
        });
        HttpFactory.postForm("http://localhost:8080/test", b, HttpFactory.HttpResponses.TEXT);*/
        String publicKey = "0BIJBHG5756QOW6N";
        String privateKey = "QL5F7YKFYYF2SX4RSDTOYBHOIDIXPR24";

        AccessTokenReq accessTokenReq = new AccessTokenReq(publicKey, privateKey);
        HttpFactory.postObject("https://remitademo.net/remita/exapp/api/v1/send/api/uaasvc/uaa/token",
                accessTokenReq, AccessToken.class, (res) -> {
            Console.log(res);
        });
    }

    static CompletableFuture<String> testCompletable() {

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
