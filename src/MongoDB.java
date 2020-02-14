import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.bson.conversions.Bson;

/*
 * Class to handle connection to MongoDB
 * param
 *
 *
 */
public class MongoDB {
    static MongoCollection<Document> usersCollection;
    MongoDatabase userDatabase;

    public MongoDB(String DB, String collection){
        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        userDatabase = mongoClient.getDatabase(DB);
        usersCollection = userDatabase.getCollection(collection);
    }


    public boolean find(String key, String what){
        Document found = usersCollection.find(new Document(key, what)).first();
        if(found != null){
            return true;
        }
        else{
            return false;
        }
    }

    public Document getDocument(String key, String who){
        Document found = usersCollection.find(new Document(key, who)).first();
        if(found != null){
            return found;
        }
        else{
            return null;
        }
    }


    public void addDoc(Document toAdd){
        usersCollection.insertOne(toAdd);
    }

    public static boolean updateValue(String key, String who, String valueToUpdate, String to) {
        Document found = usersCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            return true;
        }
        return false;
    }

    public static boolean updateIntValue(String key, String who, String valueToUpdate, Integer to) {
        Document found = usersCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            return true;
        }
        return false;
    }



}
