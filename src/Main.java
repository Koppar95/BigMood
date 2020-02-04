import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");

        VBox leftMenu = new VBox(15);
        Button moodButton = new Button("mood");
        Button userButton = new Button("users");
        leftMenu.getChildren().addAll(moodButton,userButton);

        VBox centerLayout = new VBox();


        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftMenu);
        mainLayout.setCenter(centerLayout);

        primaryStage.setScene(new Scene(mainLayout, 600, 550));
        primaryStage.show();

        LoginBox.display("Login");

    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("test");
    }
}
