package users;
import clients.*;
import server.*;
import authentication.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.*;
import java.util.Scanner;

//Used for clients to register and login to the server.
public class UserLogin {

    private final Authentication auth = new Authentication();


    public void signUp(IAuctionSystem server) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please register your name");
        String name = scan.nextLine();
        System.out.println("Please register your email address");
        String email = scan.nextLine();

        //Once details have been entered, authentication process between client and server starts.
        if (auth.verifyServer(server)  && server.getClientAuthentication()){
            System.out.println("5-Stage Authentication has been established!");{
                Users users = new Users(name, email);
                server.addUser(users);
            }
        }
        else{
            System.out.println("Unable to authenticate server and client");
            signUp(server); //Reattempts to authenticate
        }
    }

    public Users login(IAuctionSystem server) throws IOException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your name");
        String name = scan.nextLine();
        System.out.println("Please enter your email address");
        String email = scan.nextLine();

        //Authentication happens when logging in as well.
        if (auth.verifyServer(server)  && server.getClientAuthentication()){
            if (!server.verifyLogin(name, email)) {
                System.out.println("Your name or email address is incorrect!");
                login(server); //Reattempts to login
            }
        }
        return server.getUser(email);
    }
}
