package remita.requests;

public class AccessTokenReq {
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

