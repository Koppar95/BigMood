import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;

public class MongoDB {
    MongoCollection<Document> usersCollection;
    MongoDatabase userDatabase;

    public MongoDB(String DB, String collection){
        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        userDatabase = mongoClient.getDatabase(DB);
        usersCollection = userDatabase.getCollection(collection);
    }

    public boolean checkEmail(String email){
        Document found = usersCollection.find(new Document("Email", email)).first();
        if(found != null){
            return false;
        }
        else{
            return true;
        }
    }

    public void addDoc(Document toAdd){
        usersCollection.insertOne(toAdd);
    }



}
