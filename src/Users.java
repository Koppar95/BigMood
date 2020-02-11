
import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.bson.conversions.Bson;


public class Users {

    private String email;
    private int password;
    private String name;
    private int Dof;
    private int height;

    public Users(String email, String password, String name, int Dof, int height){
        this.email = email;
        this.password = password.hashCode();
        this.name = name;
        this.Dof = Dof;
        this.height = height;
    }

    public boolean addToDB(){
        /*
        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase userDatabase = mongoClient.getDatabase("UsersDB");
        MongoCollection usersCollection = userDatabase.getCollection("Users");
        */
        MongoDB conn = new MongoDB("UsersDB", "Users");


        System.out.println("Connected to DB");

        if(conn.checkEmail(email)) {

            Document newUser = new Document("Email", email);
            newUser.append("Password", password);
            newUser.append("Name", name);
            newUser.append("Date Of Birth", Dof);
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











