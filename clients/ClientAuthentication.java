package clients;

import authentication.Authentication;
import java.io.IOException;
import java.security.*;

//Authentication process for the clients.

public class ClientAuthentication {

    private final Authentication auth;
    private final PublicKey clientPublicKey;
    private final PrivateKey clientPrivateKey;
    private final byte[] clientChallenge = "This is a client challenge".getBytes();

    // Asymmetric authentication. Client keys obtained
    public ClientAuthentication() throws NoSuchAlgorithmException {
        auth = new Authentication();
        KeyPair pair = auth.formKeys();
        clientPublicKey = pair.getPublic();
        clientPrivateKey = pair.getPrivate();
    }


    public byte[] signChallenge() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return auth.signChallenge(clientChallenge, clientPrivateKey);
    }

    public PublicKey getClientPublicKey() {
        return clientPublicKey;
    }

    public byte[] getClientChallenge() {
        return clientChallenge;
    }

}

