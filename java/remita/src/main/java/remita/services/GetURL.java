package remita.services;

public class GetURL {


    public static String queryTransaction(boolean isLive){
        return isLive ? "https://login.remita.net/remita/exapp/api/v1/send/api" : "https://remitademo.net/remita/exapp/api/v1/send/api";
    }

    public static String accessToken(boolean isLive) {
        String path = "/uaasvc/uaa/token";
        return isLive ? "https://login.remita.net/remita/exapp/api/v1/send/api"+path : "https://remitademo.net/remita/exapp/api/v1/send/api"+path;
    }
}
