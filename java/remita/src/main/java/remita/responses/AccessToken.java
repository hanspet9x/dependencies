package remita.responses;

import java.util.List;

public class AccessToken {
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



/*	{
    "status": "00",
    "message":"Successful",
    "data": [
        {
            "accessToken": "9MF6XeyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5cDV2022N7PED",
            "expiresIn": 9999
        }
    ]
  }*/