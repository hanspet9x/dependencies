package remita.requests;

public class RemitaCredentials {

    private RemitaKeys testKeys, productionKeys;
    private boolean live = true;
    private String privateKeys;
    private String publicKeys;
    private static RemitaCredentials remitaCredentials = null;
    private static int type = 0;
    private RemitaCredentials(RemitaKeys testKeys) {
        this.testKeys = testKeys;
    }
    private RemitaCredentials(RemitaKeys testKeys, RemitaKeys productionKeys) {
        this.testKeys = testKeys;
        this.productionKeys = productionKeys;
        this.setLive(true);
    }
    public static RemitaCredentials getInstance(RemitaKeys testKeys) {
        type = 0;
        if (remitaCredentials == null) {
            remitaCredentials = new RemitaCredentials(testKeys);
            remitaCredentials.setLive(false);
            remitaCredentials.setPrivateKeys(testKeys.getPrivateKey());
            remitaCredentials.setPublicKeys(testKeys.getPublicKey());
            return remitaCredentials;
        }
        return remitaCredentials;
    }

    public static RemitaCredentials getInstance(RemitaKeys testKeys, RemitaKeys productionKeys, boolean isLive) {
        type = 1;
        if (remitaCredentials == null) {
            remitaCredentials = new RemitaCredentials(testKeys, productionKeys);
            remitaCredentials.setLive(isLive);
            remitaCredentials.setPublicKeys( isLive ? productionKeys.getPublicKey() : testKeys.getPublicKey());
            remitaCredentials.setPrivateKeys( isLive ? productionKeys.getPrivateKey() : testKeys.getPrivateKey());
            return remitaCredentials;
        }
        return remitaCredentials;
    }

    public static RemitaCredentials getInstance(RemitaKeys testKeys, RemitaKeys productionKeys) {
        type = 2;
        if (remitaCredentials == null) {
            remitaCredentials = new RemitaCredentials(testKeys, productionKeys);
            remitaCredentials.setLive(true);
            remitaCredentials.setPublicKeys( remitaCredentials.isLive() ? productionKeys.getPublicKey() : testKeys.getPublicKey());
            remitaCredentials.setPrivateKeys( remitaCredentials.isLive() ? productionKeys.getPrivateKey() : testKeys.getPrivateKey());
            return remitaCredentials;
        }
        return remitaCredentials;
    }

    public RemitaKeys getTestKeys() {
        return testKeys;
    }

    public void setTestKeys(RemitaKeys testKeys) {
        this.testKeys = testKeys;
    }

    public RemitaKeys getProductionKeys() {
        return productionKeys;
    }

    public void setProductionKeys(RemitaKeys productionKeys) {
        this.productionKeys = productionKeys;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
        switch (type){
            case 1 -> getInstance(testKeys).setLive(live);
            case 2 -> getInstance(testKeys, productionKeys).setLive(live);
            case 3 -> getInstance(testKeys, productionKeys, live);
        }
    }

    public String getPrivateKeys() {
        return privateKeys;
    }

    public void setPrivateKeys(String privateKeys) {
        this.privateKeys = privateKeys;
    }

    public String getPublicKeys() {
        return publicKeys;
    }

    public void setPublicKeys(String publicKeys) {
        this.publicKeys = publicKeys;
    }
}
