import com.mongodb.client.*;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;

/**
 * Class to connections to MongoDB.
 * Includes methods do add Bson document, find specific key and update values.
 * @author Karl Svensson
 * @coauthor Teo Becerra
 * @version 1.8
 */
public class MongoDB{
    /**
     * The collection on from MongoDB containing data.
     */
    MongoCollection<Document> mongoCollection;
    /**
     * The database at MongoDB.
     */
    MongoDatabase mongoDatabase;

    /**
     * Creates a new connection to selected DB and Collection
     * @param DB which DB to connect to.
     * @param collection which collection within DB to connect to.
     * @since 1.1
     */
    public MongoDB(String DB, String collection){
        String uri = "mongodb+srv://admin:abcd@bigmood-1h8lf.mongodb.net/test?retryWrites=true&w=majority";
        MongoClientURI clientURI = new MongoClientURI(uri);
        MongoClient mongoClient = new MongoClient(clientURI);
        mongoDatabase = mongoClient.getDatabase(DB);
        this.mongoCollection = mongoDatabase.getCollection(collection);
    }

    /**
     * Searches the DB for a specific key and value.
     * @param key DB key.
     * @param what value of the key you want to find.
     * @return true if "what" is found, else false.
     * @since 1.8
     */
    public boolean find(String key, String what){
        Document found = mongoCollection.find(new Document(key, what)).first();
        return found != null;
    }

    /**
     * This functions gets all comments from users mood submission. It adds the comments to the lists
     * happyComments and sadComments in MoodHashMap.
     * @param mood decides which submissions to get comments from.
     * @since 1.8
     */

    public void getComments(String mood){
        Bson moodFilter = Filters.eq("Mood", mood);
        Bson incCommentProjection = Projections.include("Comment");
        Bson excIdProjection = Projections.excludeId();

        FindIterable submissions = mongoCollection.find(moodFilter).projection(and(incCommentProjection, excIdProjection));

        if(mood.equals("Happy")){
            submissions.forEach(MoodHashMap.toHappyCommentArray);
        } else {
            submissions.forEach(MoodHashMap.toSadCommentArray);
        }

    }

    /**
     * Counts the number of submissions of a certain mood (for all users).
     *  @param mood decides which submissions to count.
     * @since 1.8
     */
    public long countMood(String mood){
        Bson moodFilter = Filters.eq("Mood", mood);
        long count = mongoCollection.countDocuments(moodFilter);
        return count;
    }

    /**
     * Counts total number of submissions in database.
     * @since 1.8
     */
    public long countSubmissions(){
        long count = mongoCollection.countDocuments();
        return count;
    }

    /**
     * Counts the number of submissions of a certain user.
     *  @param user decides which users submissions to count.
     * @since 1.8
     */
    public long countUserSubmissions(String user){
        Bson userFilter = Filters.eq("User",user);
        long count = mongoCollection.countDocuments(userFilter);
        return count;
    }

    /**
     * Counts the number of submissions of a certain mood (for each individual user).
     * @param mood decides which mood to count submissions from.
     * @param user decides which users submissions to count.
     * @since 1.8
     */
    public long countUserMood(String mood, String user){
        Bson moodFilter = Filters.eq("Mood", mood);
        Bson userFilter = Filters.eq("User",user);

        long count = mongoCollection.countDocuments(and(userFilter,moodFilter));
        return count;
    }

    /**
     * Counts total amount of users in the database.
     * @since 1.8
     */
    public long countUsers(){
        long userCount = 0;

        MongoCursor users = mongoCollection.distinct("User", String.class).iterator();

        while (users.hasNext()){
            userCount++;
            users.next();
        }
        return userCount;
    }

    /**
     * Finds the submitted mood of a certain date and user.
     * @since 1.8
     */
    public String findUserMood(String user, String date){
        Bson userFilter = Filters.eq("User",user);
        Bson dateFilter = Filters.eq("Date",date);
        Document userMood = mongoCollection.find(and(userFilter,dateFilter)).first();
        String mood ="";
        if(userMood !=null) {
            mood = userMood.getString("Mood");
        }
        return mood;
    }

    /**
     * Checks if the user has already submitted a mood today. We limit mood submissions to one a day for users.
     * @param date which date to filter for.
     * @param user which user to filter for.
     * @since 1.8
     */
    public boolean submittedToday(String user, String date){
        Bson userFilter = Filters.eq("User",user);
        Bson dateFilter = Filters.eq("Date",date);

        Document submittedToday = mongoCollection.find(and(userFilter,dateFilter)).first();
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
     * @since 1.1
     */
    public Document getDocument(String key, String who){
        return mongoCollection.find(new Document(key, who)).first();
    }

    /**
     * Adds a Bson document to DB.
     * @param toAdd Bson document to add.
     * @since 1.1
     */
    public void addDoc(Document toAdd){
        mongoCollection.insertOne(toAdd);
    }

    /**
     * Update String value in the DB
     * @param key DB key
     * @param who key value
     * @param valueToUpdate which value of given key and value to update
     * @param to new value after update
     * @return true if update was a success, else false.
     * @since 1.1
     */
    public boolean updateValue(String key, String who, String valueToUpdate, String to){
        Document found = mongoCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            mongoCollection.updateOne(found,updateOperation);
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
     * @since 1.2
     */

    public boolean updateIntValue(String key, String who, String valueToUpdate, Integer to){
        Document found = mongoCollection.find(new Document(key, who)).first();
        if (found != null){
            Bson updatedValue = new Document(valueToUpdate, to);
            Bson updateOperation = new Document("$set", updatedValue);
            mongoCollection.updateOne(found,updateOperation);
            System.out.println("Updated the database");
            return true;
        }
        return false;
    }
}
