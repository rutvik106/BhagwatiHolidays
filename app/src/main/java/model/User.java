package model;

/**
 * Created by ACER on 02-Mar-16.
 */
public class User {

    public User(String name, String email, String profilePic){
        this.name=name;
        this.email=email;
        this.profilePic=profilePic;
    }

    public String getEmail() {
        return email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getName() {
        return name;
    }

    String name,email,profilePic;



}
