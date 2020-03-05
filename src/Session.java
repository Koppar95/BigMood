import org.bson.Document;

/**
 * Class that contains information about a session.
 * @author Samuel Leckborn
 * @version 1.0
 * @since 2020-03-04
 */
public class Session {
    public Document currentUser;
    public Session(Document currentUser){
        this.currentUser=currentUser;
    }

    /**
     * Get current user document from DB
     * @return Current user Bson Document
     */
    public Document getCurrentUser(){
        return currentUser;
    }

    /**
     * Get username from DB of the user that started the session.
     * @return username from DB as string.
     */
    public String getCurrentUserName(){
        return currentUser.getString("Username");
    }

    /**
     * Set current user in the session.
     * @param user The user Bson document.
     */
    public void setCurrentUser(Document user){
        currentUser=user;
    }

    /**
     * Updates the current user in the session with data from DB.
     * @param userName
     */
    public void updateCurrentUser(String userName){
        Document user=Main.userConn.getDocument("Username",userName);
        currentUser=user;
    }

}
