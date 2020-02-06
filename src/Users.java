import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.bson.conversions.Bson;


public class Users {

    String email;
    String password;
    String name;

    public Users(String email, String password, String name){
        this.email = email;
        this.password = password;
        this.name = name;

        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        MongoDatabase userDatabase = mongoClient.getDatabase("UsersDB");
        MongoCollection usersCollection = userDatabase.getCollection("Users");

        System.out.println("Connected to DB");

        if(checkEmail(email, usersCollection)) {

            Document newUser = new Document("Email", email);
            newUser.append("Password", password);
            newUser.append("Name", name);

            usersCollection.insertOne(newUser);
        }
        else{
            AlertBox.display("Invalid Email", "User already exist");
        }

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










