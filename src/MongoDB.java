import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * Class to connections to MongoDB.
 * Includes methods do add Bson document, find specific key and update values.
 * @author Karl Svensson
 * @version 1.1
 * @since 2020-02-19
 */
public class MongoDB{
    static MongoCollection<Document> usersCollection;
    MongoDatabase userDatabase;

    /**
     * Creates a new connection to selected DB and Collection
     * @param DB which DB to connect to.
     * @param collection which collection within DB to connect to.
     */
    public MongoDB(String DB, String collection){
        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        userDatabase = mongoClient.getDatabase(DB);
        usersCollection = userDatabase.getCollection(collection);
    }

    /**
     * Searches the DB for a specific key and value.
     * @param key DB key.
     * @param what value of the key you want to find.
     * @return true if "what" is found, else false.
     */
    public boolean find(String key, String what){
        Document found = usersCollection.find(new Document(key, what)).first();
        return found != null;
    }

    /**
     * Method do get Bson document for a specific user in the DB.
     * @param key DB key ex "Name"
     * @param who Who you want to find
     * @return Bson document included information for the selected name,
     */
    public Document getDocument(String key, String who){
        return usersCollection.find(new Document(key, who)).first();
    }

    /**
     * Adds a Bson document to DB.
     * @param toAdd Bson document to add.
     */
    public void addDoc(Document toAdd){
        usersCollection.insertOne(toAdd);
    }

    /**
     * Update String value in the DB
     * @param key DB key
     * @param who key value
     * @param valueToUpdate which value of given key and value to update
     * @param to new value after update
     * @return true if update was a success, else false.
     */
    public static boolean updateValue(String key, String who, String valueToUpdate, String to){
        Document found = usersCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            usersCollection.updateOne(found,updateOperation);
            System.out.println("Updated the database");
            return true;
        }
        return false;
    }

    /**
     * Update Integer value in DB
     * @param key DB key
     * @param who key value
     * @param valueToUpdate which value of given key and value to update
     * @param to new value after update
     * @return true if update was a success, else false
     */
    public static boolean updateIntValue(String key, String who, String valueToUpdate, Integer to){
        Document found = usersCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            usersCollection.updateOne(found,updateOperation);
            System.out.println("Updated the database");
            return true;
        }
        return false;
    }
}
