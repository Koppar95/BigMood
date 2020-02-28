import insidefx.undecorator.Undecorator;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class Main extends Application {
    /** In Main the side bar menu is created and the buttons are connected to instances of other classes that are various
     * windows.
     * @param window Stage that is the basic window.
     */
    Stage window;

    static MongoDB userConn = new MongoDB("UsersDB","Users");
    static MongoDB moodConn = new MongoDB("UsersDB","MoodData");


    @Override
    /**
     * @param mainLayout BorderPane, the layout for the entire window.
     * @param undecorator //TODO
     * @param scene the part of the window where the actual application is shown

     */
    public void start(Stage primaryStage){

        //Visa LoginBox
        LoginBox.display();
        //

        window = primaryStage;
        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setMinWidth(600);
        window.setMinHeight(600);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(new StartWindow());

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin.css");
        Scene scene = new Scene(undecorator, 900,800);
        scene.setFill(Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();

        new Menu(window,mainLayout);


    }

    public static void main(String[] args) {
        launch(args);
    }

}