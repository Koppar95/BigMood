import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main extends Application {
    /** In Main all parts of the application are put together. In main().
     * window Stage that is the initial window.
     * userConn Connection to MongoDB database containing login data.
     * moodConn Connection to MongoDB database containing mood data.
     */

    static MongoDB userConn;
    static MongoDB moodConn;

    @Override
    /** Start is the program initialisation. The login box is first being displayed. The basic layout is set, then the
     * menu is added.
     * @param mainLayout BorderPane, the layout for the entire window.
     * @param undecorator //TODO
     * @param scene the part of the window where the actual application is shown
     */
    public void start(Stage window) throws ParserConfigurationException, SAXException, IOException {
        Configuration.parseConfig();
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