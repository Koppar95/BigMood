import org.bson.Document;

public class Session {
    public Document currentUser;
    public Session(Document currentUser){
        this.currentUser=currentUser;
    }

    public Document getCurrentUser(){
        return currentUser;
    }

    public String getCurrentUserName(){
        return currentUser.getString("Username");
    }
    public void setCurrentUser(Document user){
        currentUser=user;
    }
    public void updateCurrentUser(String userName){
        Document user=Main.userConn.getDocument("Username",userName);
        currentUser=user;
    }

}
