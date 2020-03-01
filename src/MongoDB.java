import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;

/**
 * Class to connections to MongoDB.
 * Includes methods do add Bson document, find specific key and update values.
 * @author Karl Svensson
 * @version 1.1
 * @since 2020-02-19
 */
public class MongoDB{
    MongoCollection<Document> usersCollection;
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
        this.usersCollection = userDatabase.getCollection(collection);
    }

    //List allow for adding and not defining size when initilialized
    public static List<String> happyComments = new ArrayList<String>();
    public static List<String> sadComments = new ArrayList<String>();

    //function to call forEach comment in submissions could probably be combined with toSadArray
    Block<Document> toHappyArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            happyComments.add(document.get("Comment").toString());
        }
    };

    Block<Document> toSadArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            sadComments.add(document.get("Comment").toString());
        }
    };

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

    public long countTotal(){
        long count = usersCollection.countDocuments();
        return count;
    }

    //Gets comments from submissions
    public void getComments(String mood){
        Bson moodFilter = Filters.eq("Mood", mood);
        Bson incCommentProjection = Projections.include("Comment");
        Bson excIdProjection = Projections.excludeId();

        FindIterable submissions = usersCollection.find(moodFilter).projection(and(incCommentProjection, excIdProjection));

        if (mood.equals("Happy")) {
            submissions.forEach(toHappyArray);
        } else {
            submissions.forEach(toSadArray);
        }

    }

    public long countMood(String mood){
        Bson moodFilter = Filters.eq("Mood", mood);
        long count = usersCollection.countDocuments(moodFilter);
        return count;
    }

    public long countUserMood(String mood, String user){
        Bson moodFilter = Filters.eq("Mood", mood);
        Bson userFilter = Filters.eq("User",user);

        long count = usersCollection.countDocuments(and(userFilter,moodFilter));
        return count;
    }

    public String[] getUsers(){
        Bson incUserProjection = Projections.include("User");
        Bson excIdProjection = Projections.excludeId();

        MongoCursor users = usersCollection.distinct("User", String.class).iterator();

        List<String> userList= new ArrayList<String>();

        while( users.hasNext()){
            userList.add(users.toString());
            users.next();
        }

        String[] userArr = new String[userList.size()];
        userArr = userList.toArray(userArr);

        return userArr;
    }

    //funkar ej
    public long countUsers(){
        long userCount = 0;

        MongoCursor users = usersCollection.distinct("User", String.class).iterator();

        while (users.hasNext()){
            userCount++;
            users.next();
        }
        return userCount;
    }
    // clean up and turn into one function??

    public String findUserMood(String user, String date){
        Bson userFilter = Filters.eq("User",user);
        Bson dateFilter = Filters.eq("Date",date);
        Document userMood = usersCollection.find(and(userFilter,dateFilter)).first();
        String mood ="";
        if(userMood !=null) {
            mood = userMood.getString("Mood");
        }
        return mood;
    }

    public boolean submittedToday(String user, String date){
        Bson userFilter = Filters.eq("User",user);
        Bson dateFilter = Filters.eq("Date",date);

        Document submittedToday = usersCollection.find(and(userFilter,dateFilter)).first();
        if (submittedToday!= null){
            System.out.println("User posted this today: " + submittedToday.toString());
            return true;
        } else{
            return false;
        }
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
    public boolean updateValue(String key, String who, String valueToUpdate, String to){
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

    public boolean updateIntValue(String key, String who, String valueToUpdate, Integer to){
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
