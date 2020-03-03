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
    public void start(Stage window) throws ParserConfigurationException, SAXException, IOException {
        Configuration.parseConfig();
        //Start a thread to when connection to MongoDB, i case no internet connection the program does not crash.
        new Thread (()->{
            userConn = new MongoDB("UsersDB","Users");
            moodConn = new MongoDB("UsersDB","MoodData");
        }).start();
        //Visa LoginBox
        LoginBox.display();

        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setMinWidth(600);
        window.setMinHeight(600);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(new StartWindow());

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin-"+Configuration.color+".css");
        Scene scene = new Scene(undecorator, Configuration.width,Configuration.height);
        scene.setFill(Color.TRANSPARENT);
        window.setOnCloseRequest(e->System.exit(0));
        window.setScene(scene);
        window.show();
        new Menu(window,mainLayout);

    }

    /**
     * the javaFX is launched and start() will run.
     */
    public static void main(String[] args) {
        launch(args);
    }

}