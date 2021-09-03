package remita;

import net.HttpFactory;
import org.json.JSONObject;
import remita.requests.*;
import remita.services.*;

import java.io.IOException;

public class Remita {

    private static Remita remita;
    public String publicKey = null;
    public String privateKey = null;
    public boolean isLive = false;
    public String accessToken = null;

    private Remita(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }
    public static Remita getInstance(RemitaCredentials remitaCredentials){
        if(remita == null){
            remita = new Remita(remitaCredentials.getPublicKeys(), remitaCredentials.getPrivateKeys());
            remita.isLive = remitaCredentials.isLive();
            return remita;
        }
        return remita;
    }

    public void confirmPayment() {
        String url = GetURL.queryTransaction(isLive);

    }

    public void getAccessToken () {
        JSONObject object = new JSONObject();
        object.put("username", publicKey);
        object.put("password", privateKey);

        HttpFactory.postObject(GetURL.accessToken(isLive), new AccessTokenReq(publicKey, privateKey), AccessToken.class, (token) -> {

        });

        Htt

        HttpFactory.setOnServerResponse(new HttpFactory.OnServerResponse() {
            @Override
            public void response(JSONObject data) throws IOException {
                super.response(data);
            }
        }, HttpFactory.HttpResponses.JSON);
        HttpFactory.postObject(GetURL.accessToken(isLive), object);
    }

}
