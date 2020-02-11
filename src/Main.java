import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
//TESTING
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");

        window.initStyle(StageStyle.TRANSPARENT);


        VBox leftMenu = new VBox(15);
        Button moodButton = new Button("mood");
        Button userButton = new Button("users");
        leftMenu.getChildren().addAll(moodButton,userButton);

        VBox centerLayout = new VBox();


        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftMenu);
        mainLayout.setCenter(centerLayout);

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin.css");
        Scene scene = new Scene(undecorator, 600,550);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        LoginBox.display("Login");
        //Users user1 = new Users("Svensson.Karl@iCloud.com", "1234", "Karl");

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("test");
    }
}
