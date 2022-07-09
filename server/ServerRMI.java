package server;

import authentication.Authentication;
import clients.ClientAuthentication;
import items.*;
import users.*;
import java.io.IOException;
import java.security.*;
import java.util.*;


public class ServerRMI {

    //Use HashMap since IDs are unique. Easier than ArrayList when implementing closeAuction()
    private final Authentication auth;
    private final HashMap<Integer, AuctionItem> items; //Integer is uniqueID (key) //Each instance of AuctionItem will have its unique ID (Integer)
    private final HashMap<String, Users> users;     //Store user information in this hash map. String is the email address (key)
    private final PublicKey serverPublicKey;
    private final PrivateKey serverPrivateKey;
    private final byte[] serverChallenge = "This is a server challenge".getBytes();


    public ServerRMI() throws NoSuchAlgorithmException {
        items = new HashMap<>();
        users = new HashMap<>();
        auth = new Authentication();
        KeyPair pair = auth.formKeys();
        serverPublicKey = pair.getPublic();
        serverPrivateKey = pair.getPrivate();
    }

    public int createAuction(double startPrice, String itemDescription, double reservePrice, int uniqueID, Sellers seller) {
        AuctionItem item = new AuctionItem(startPrice, itemDescription, reservePrice, uniqueID, seller);
        items.put(uniqueID, item); //Add to hashmap
        System.out.println("Item Price: " + items.get(uniqueID).getStartPrice() + "\n" + "Item Description: " + items.get(uniqueID).getItemDescription() + "\n" + "Item Reserve Price: " + items.get(uniqueID).getReservePrice()); //Return item information
        System.out.println("Hashmap Size: " + items.size() + "\n");
        return uniqueID;
    }

    public String closeAuction(int uniqueID, Sellers seller) {
        if (!Objects.equals(items.get(uniqueID).getSeller().getEmail(), seller.getEmail())) {
            return "You are not authorized to close this auction";
        }
        if (items.containsKey(uniqueID) && Objects.equals(items.get(uniqueID).getSeller().getEmail(), seller.getEmail())) {
            String winner = "Auction has been closed. The winner is " + items.get(uniqueID).getBuyer().getName() + "!";
            items.remove(uniqueID);
            return winner;
        } else {
            return "Invalid Unique ID!";
        }
    }

    public String showAuctions() {
        String items = ""; // Initialize now to add values onto String
        for (Map.Entry<Integer, AuctionItem> entry : this.items.entrySet()) {
            items = entry.getValue().getItemDescription() + "\n" + "Current Price: " + entry.getValue().getCurrentPrice() + "\n" + "ID (Use this ID to place a bid on item): " + entry.getValue().getUniqueID();
        }
        return items;
    }

    //Signs the signature for authentication
    public byte[] signChallenge() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        return auth.signChallenge(serverChallenge, serverPrivateKey);
    }


    //Gets authentication from client.
    public boolean getClientAuthentication() throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        ClientAuthentication clientAuth = new ClientAuthentication();
        clientAuth.signChallenge();
        if (auth.verifyClient(clientAuth)){
            System.out.println("Client has been verified!");
            return true;
        }
        else{
            System.out.println("Client has not been verified!");
            return false;
        }
    }

    public PublicKey getPublicKey() {
        return serverPublicKey;
    }

    public void addUser(Users user) {
        users.put(user.getEmail(), user);
    }

    public Users getUser(String email) {
        return users.get(email);
    }

    //Checks to see if an email address exists within the hash map.
    public boolean verifyLogin(String name, String email) {
        if (!users.containsKey(email)) {
            return false;
        }
        return Objects.equals(users.get(email).getName(), name);
    }


    public String placeBid(int id, double bid, Buyers buyer) {
        String result = "";
        if (!items.containsKey(id)) {
            return result + "Auction Item does not exist";
        }
        if (items.get(id).getCurrentPrice() >= bid) {
            return result + "Bid must be greater than the current price of the item";
        } else {
            items.get(id).setCurrentPrice(bid);
            items.get(id).setBuyers(buyer);
            return result + "Bid successfully placed with amount: " + bid + " by buyer " + items.get(id).getBuyer().getName();
        }

    }
}