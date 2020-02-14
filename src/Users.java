
import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.bson.conversions.Bson;


public class Users {

    private String email;
    private int password;
    private String name;
    private int Dob;
    private int height;

    public Users(String email, String password, String name, int Dob, int height){
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

            Document newUser = new Document("Email", email);
            newUser.append("Password", password);
            newUser.append("Name", name);
            newUser.append("Date Of Birth", Dob);
            newUser.append("Height", height);

            conn.addDoc(newUser);
        }
        else{
            AlertBox.display("Invalid Email", "User already exist");
            return false;
        }
        return true;
    }

    private boolean checkEmail(String email, MongoCollection usersCollection){
        Document found = (Document) usersCollection.find(new Document("Email", email)).first();
        if(found != null){
            return false;
        }
        else{
            return true;
        }
    }
}











