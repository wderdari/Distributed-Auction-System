package server;

import users.Buyers;
import users.Sellers;
import users.Users;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SignatureException;

public class Server implements IAuctionSystem {

    private final ServerRMI serverRMI; //Server will invoke methods from this class.

    public Server() throws NoSuchAlgorithmException {
        serverRMI = new ServerRMI();
    }

public static void main(String[] args) {
    try {
        Server s = new Server();
        String name = "myserver";
        IAuctionSystem stub = (IAuctionSystem) UnicastRemoteObject.exportObject(s, 0);
        Registry registry = LocateRegistry.getRegistry();
        registry.rebind(name, stub);
        System.out.println("Server ready");
    } catch (Exception e) {
        System.err.println("Exception:");
        e.printStackTrace();
    }
}

@Override
public int createAuction(double startPrice, String itemDescription, double reservePrice, int uniqueID, Sellers seller) throws RemoteException {
    return serverRMI.createAuction(reservePrice, itemDescription, reservePrice, uniqueID, seller);
}

@Override
public String closeAuction(int uniqueID, Sellers seller) throws RemoteException {
    return serverRMI.closeAuction(uniqueID, seller);
}

@Override
public String showAuctions() throws RemoteException {
    return serverRMI.showAuctions();
}


    @Override
    public boolean getClientAuthentication() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
         return serverRMI.getClientAuthentication();
    }

    @Override
    public PublicKey getPublicKey() {
        return serverRMI.getPublicKey();
    }

    @Override
    public byte[] signChallenge() throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {
        return serverRMI.signChallenge();
    }

    @Override
public void addUser(Users user) throws RemoteException {
    serverRMI.addUser(user);
}

    @Override
    public boolean verifyLogin(String name, String email) throws RemoteException {
        return serverRMI.verifyLogin(name, email);
    }

    @Override
public String placeBid(int id, double bid, Buyers buyer) throws RemoteException {
    return serverRMI.placeBid(id, bid, buyer);
}

@Override
public Users getUser(String email) throws RemoteException {
    return serverRMI.getUser(email);
}
}