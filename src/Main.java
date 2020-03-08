import javafx.application.Application;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;

/** In Main all parts necessary to run the application are put together.
 */
public class Main extends Application {

    /**
     * userConn is of type MongoDB and represents the connection to MongoDB database containing login data.
     */
    static MongoDB userConn;
    /**
     *moodConn is of type MongoDB and represents the connection to MongoDB database containing mood data.
     */
    static MongoDB moodConn;

    @Override
    /** Start is the program initialisation. The login box is first being displayed. The basic layout is set, then the
     * menu is added.
     * @param window A Stage that is the initial application window.
     */
    public void start(Stage window) throws ParserConfigurationException, SAXException, IOException {

        try{
            Configuration.parseConfig();
        }catch(FileNotFoundException e){
            Configuration.height=600;
            Configuration.width=600;
            Configuration.color="green";
        }
        //Start a thread to when connection to MongoDB, i case no internet connection the program does not crash.
        new Thread (()->{
            userConn = new MongoDB("UsersDB","Users");
            moodConn = new MongoDB("UsersDB","MoodData");
        }).start();

        //Create and show loginbox
        Session currentSession = new Session(null);
        LoginBox loginBox = new LoginBox(currentSession);
        loginBox.display();
        MainStage mainStage = new MainStage(currentSession, window);
        mainStage.init();
        //

    }

    /**
     * the javaFX is launched and start() will run.
     */
    public static void main(String[] args) {
        launch(args);
    }

}