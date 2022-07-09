package server;

import users.Buyers;
import users.Sellers;
import users.Users;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;


public interface IAuctionSystem extends Remote {

    boolean getClientAuthentication() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException;

    PublicKey getPublicKey() throws RemoteException;

    byte[] signChallenge() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException;

    void addUser(Users user) throws RemoteException;

    boolean verifyLogin(String name, String email) throws RemoteException;

    int createAuction(double startPrice, String itemDescription, double reservePrice, int uniqueID, Sellers seller) throws RemoteException;

    String showAuctions() throws RemoteException;

    String placeBid(int id, double bid, Buyers buyer) throws RemoteException;

    Users getUser(String email) throws RemoteException;

    String closeAuction(int uniqueID, Sellers seller) throws RemoteException;

}
