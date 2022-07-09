package authentication;

import clients.ClientAuthentication;
import server.IAuctionSystem;

import java.io.IOException;
import java.security.*;

public class Authentication {

    private final byte[] serverChallenge = "This is a server challenge".getBytes();

    //Creates both a KeyPair which can be used to initialise public and private keys.
    public KeyPair formKeys() throws NoSuchAlgorithmException {
        KeyPair pair;
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); //RSA for asymmetric, AES for symmetric
        keyGen.initialize(2048);
        pair = keyGen.generateKeyPair();
        return pair;
    }

    //Signs the signature with the server's private key and then returns it
    public byte[] signChallenge(byte[] challenge, PrivateKey key) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(key);
        signature.update(challenge);
        return signature.sign();
    }

    //Returns true if server has been verified by the client.
    public boolean verifyServer(IAuctionSystem server) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        byte[] signatureBytes;
        signatureBytes = server.signChallenge();
        Signature publicServerSignature = Signature.getInstance("SHA256withRSA");
        publicServerSignature.initVerify(server.getPublicKey());
        publicServerSignature.update(serverChallenge);
        return publicServerSignature.verify(signatureBytes);
    }

    //Returns true if client has been verified by the server.
    public boolean verifyClient(ClientAuthentication client) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        byte[] signatureBytes;
        signatureBytes = client.signChallenge();
        Signature ClientSignature = Signature.getInstance("SHA256withRSA");
        ClientSignature.initVerify(client.getClientPublicKey());
        ClientSignature.update(client.getClientChallenge());
        return ClientSignature.verify(signatureBytes);
    }
}