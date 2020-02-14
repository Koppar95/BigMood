
import org.bson.Document;

import java.time.LocalDate;


public class Users {

    private String username;
    private int password;
    private String name;
    private LocalDate Dob;
    private int height;

    public Users(String username, String password, String name, LocalDate Dob, int height){
        this.username = username;
        this.password = password.hashCode();
        this.name = name;
        this.Dob = Dob;
        this.height = height;
    }

    /* Creates a new connection do DB and creates a Bson document for that user and adds this to DB*/
    public boolean addToDB(){

        MongoDB conn = new MongoDB("UsersDB", "Users");

        System.out.println("Connected to DB");

        if(conn.find("Username", username)) {
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











