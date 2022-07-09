package clients;

import authentication.Authentication;
import server.IAuctionSystem;
import users.Buyers;
import users.Sellers;
import users.UserLogin;
import users.Users;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Scanner;

public class ClientBuy {

    private final ClientAuthentication auth = new ClientAuthentication();


    public ClientBuy() throws NoSuchAlgorithmException {

    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: java Client n");
            return;
        }

        int n = Integer.parseInt(args[0]);

        // RMI Registry lookup.
        try {
            UserLogin account = new UserLogin();
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IAuctionSystem server = (IAuctionSystem) registry.lookup(name);

            //User registration and login
            account.signUp(server);
            Users user = account.login(server);
            Buyers buyer = new Buyers(user.getName(), user.getEmail());
            showAuctions(server);
            placeBid(server, buyer);

        } catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }

    }

    // Displays the current items available.
    private static void showAuctions(IAuctionSystem server) throws RemoteException {
        System.out.println("Current available items for bidding");
        System.out.println(server.showAuctions());
    }

    // Allows the client to place their bed.
    private static void placeBid(IAuctionSystem server, Buyers buyer) throws RemoteException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the ID of the item you would like to bid on");
        int id = scan.nextInt();
        System.out.println("Please enter your desired bid amount");
        double bid = scan.nextDouble();
        System.out.println(server.placeBid(id, bid, buyer));
    }
}