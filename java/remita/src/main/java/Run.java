import remita.Remita;
import remita.requests.RemitaCredentials;
import remita.requests.RemitaKeys;

public class Run {
    public static void main(String[] args) {

        String publicKey = "UzAwMDAzOTAzNzd8MTA3ODg3NDE0MTExfGMzMjM0YTBhNjg1N2M5NWNiODY3ZWU4YjI5ZmI1NjQ2OGY5NDk5YmJmZmUzOTY1OTNkZGM2ZDNiMjRiZWIwZmY2NTlkNGM3NDNkZTU1MWJlNTc5NjlhYzFmOTdjY2VmZDJlNWJmZjY2YzMyYmMzZGNmMTFkNzY4MjVkNDVlN2I1";
        String privateKey = "f06cc73c43e21c0770481c6d9cd1038bdbc2b9c901883afa026af065982d0de43c0355cf95f79acc8d206db048da2814d04c8bf4cfa867a516a2e031b1c44092";
        RemitaCredentials credentials = RemitaCredentials.getInstance(new RemitaKeys(publicKey, privateKey));
        Remita remita = Remita.getInstance(credentials);
    }
}
