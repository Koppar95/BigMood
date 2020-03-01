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
    /** In Main the side bar menu is created and the buttons are connected to instances of other classes that are various
     * windows.
     * @param window Stage that is the basic window.
     */

    static MongoDB userConn = new MongoDB("UsersDB","Users");
    static MongoDB moodConn = new MongoDB("UsersDB","MoodData");

    @Override
    /**
     * @param mainLayout BorderPane, the layout for the entire window.
     * @param undecorator //TODO
     * @param scene the part of the window where the actual application is shown
     */
    public void start(Stage window) throws ParserConfigurationException, SAXException, IOException {
        Configuration.parseConfig();

        //Visa LoginBox
        LoginBox.display();
        //

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

    public static void main(String[] args) {
        launch(args);
    }

}