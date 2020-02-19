
import org.bson.Document;

import java.time.LocalDate;

/**
 * Class to create Users for the application.
 * Includes methods to add user to DB.
 * @author Karl Svensson
 * @version 1.0
 * @since 2020-02-18
 */

public class User{

    private String username;
    private int password;
    private String name;
    private LocalDate Dob;
    private int height;

    /**
     * Creates the new user with input from the RegistrationBox
     * @param username new user's selected Username
     * @param password new user's selected Password
     * @param name new user's selected name
     * @param Dob new user's selected Date of Birth
     * @param height new user's selected Height
     */
    public User(String username, String password, String name, LocalDate Dob, int height){
        this.username = username;
        this.password = password.hashCode();
        this.name = name;
        this.Dob = Dob;
        this.height = height;
    }

    /**
     * Creates a connection to UserDB with collection Users. Adds new user to DB.
     * Checks if user already exists in DB, id it does not it adds new user to DB.
     * @return true if new user was added. False is it could not add new user to DB.
     */
    public boolean addToDB(){
        MongoDB conn = new MongoDB("UsersDB", "Users");
        System.out.println("Connected to DB");
        if(conn.find("Username", username)){
            AlertBox.display("Invalid Username", "User already exist");
            return false;
        }
        else{
            Document newUser = new Document("Username", username);
            newUser.append("Password", password);
            newUser.append("Name", name);
            newUser.append("Date Of Birth", Dob);
            newUser.append("Height", height);
            conn.addDoc(newUser);
            return true;
        }
    }
}











