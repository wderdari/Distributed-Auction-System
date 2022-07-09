package clients;
import server.*;
import users.*;
import authentication.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.*;
import java.util.Scanner;


public class ClientSell {

    private final ClientAuthentication auth = new ClientAuthentication();

    public ClientSell() throws NoSuchAlgorithmException {

    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        if (args.length < 1) {
            System.out.println("Usage: java Client n");
            return;
        }

        int n = Integer.parseInt(args[0]);

        try {
            UserLogin account = new UserLogin();
            String name = "myserver";
            Registry registry = LocateRegistry.getRegistry("localhost");
            IAuctionSystem server = (IAuctionSystem) registry.lookup(name);
            account.signUp(server);
            Users user = account.login(server);
            Sellers seller = new Sellers(user.getName(), user.getEmail());
            createAuction(server, seller);
            closeAuction(server, seller);

        }
        catch (Exception e) {
            System.err.println("Exception:");
            e.printStackTrace();
        }
    }

    // Create user auction.
    private static void createAuction(IAuctionSystem server, Sellers seller) throws RemoteException{
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter an item description");
        String itemDescription = scan.nextLine();
        System.out.println("Please enter your item starting price");
        double startPrice = scan.nextDouble();
        scan.nextLine();
        System.out.println("Please enter the reserve price");
        double reservePrice = scan.nextDouble();
        System.out.println("Please enter a unique ID for the item");
        int uniqueID = scan.nextInt();
        server.createAuction(startPrice, itemDescription, reservePrice, uniqueID, seller);
        System.out.println("Your unique ID is " + uniqueID + ", you will need it to close the auction");
    }

    // Close user auction.
    private static void closeAuction(IAuctionSystem server, Sellers seller) throws RemoteException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the unique ID for the auction you would like to close");
        int uniqueID = scan.nextInt();
        scan.nextLine();
        scan.close();
        System.out.println(server.closeAuction(uniqueID, seller));
    }

    public ClientAuthentication getClientAuthentication() {
        return auth;
    }
}