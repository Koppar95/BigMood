import org.bson.Document;

/**
 * Class that contains information about a session.
 * @author Samuel Leckborn
 * @version 1.1
 */
public class Session {
    /**
     * Document representing current user.
     */
    public Document currentUser;

    /**
     * Creates a Session.
     * @param currentUser is the Bson Document of the session user
     * @since 1.1
     */
    public Session(Document currentUser){
        this.currentUser=currentUser;
    }

    /**
     * Get current user document from DB
     * @return Current user Bson Document
     * @since 1.1
     */
    public Document getCurrentUser(){
        return currentUser;
    }

    /**
     * Get username from DB of the user that started the session.
     * @return username from DB as string.
     * @since 1.1
     */
    public String getCurrentUserName(){
        return currentUser.getString("Username");
    }

    /**
     * Set current user in the session.
     * @param user The user Bson document.
     * @since 1.1
     */
    public void setCurrentUser(Document user){
        currentUser=user;
    }

    /**
     * Updates the current user in the session with data from DB.
     * @param userName
     * @since 1.1
     */
    public void updateCurrentUser(String userName){
        Document user=Main.userConn.getDocument("Username",userName);
        currentUser=user;
    }

}
