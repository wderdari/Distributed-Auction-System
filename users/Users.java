package users;

import java.io.Serializable;

//Buyers and Sellers will have their classes extended from here 

public class Users implements Serializable{

    private final String name;
    private final String email;

    public Users(String name, String email){
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getName(){
        return name;
    }


}
