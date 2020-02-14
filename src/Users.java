
import org.bson.Document;

import java.time.LocalDate;


public class Users {

    private String email;
    private int password;
    private String name;
    private LocalDate Dob;
    private int height;

    public Users(String email, String password, String name, LocalDate Dob, int height){
        this.email = email;
        this.password = password.hashCode();
        this.name = name;
        this.Dob = Dob;
        this.height = height;
    }

    public boolean addToDB(){

        MongoDB conn = new MongoDB("UsersDB", "Users");

        System.out.println("Connected to DB");

        if(conn.find("Email", email)) {
            AlertBox.display("Invalid Email", "User already exist");
            return false;

        }
        else{
            Document newUser = new Document("Email", email);
            newUser.append("Password", password);
            newUser.append("Name", name);
            newUser.append("Date Of Birth", Dob);
            newUser.append("Height", height);

            conn.addDoc(newUser);
            return true;
        }
    }
}











