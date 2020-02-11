import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
<<<<<<< HEAD
import javafx.scene.image.Image;
=======
import javafx.stage.StageStyle;
>>>>>>> alpha

public class Main extends Application {
//TESTING
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");
        
        //Effect for smileys
        DropShadow shadow = new DropShadow();
        Glow glow = new Glow();

        window.initStyle(StageStyle.TRANSPARENT);



        VBox leftMenu = new VBox(15);
        Button moodButton = new Button("mood");
        Button userButton = new Button("users");
        leftMenu.getChildren().addAll(moodButton,userButton);


        Image happy = new Image("/happy.png");
        Image sad = new Image("sad.png");
        ImageView happyView = new ImageView(happy);
        ImageView sadView = new ImageView(sad);

        happyView.setOnMouseEntered(e -> {
            happyView.setEffect(shadow);
        });

        happyView.setOnMouseExited(e -> {
            happyView.setEffect(null);
        });

        happyView.setOnMouseClicked(e -> {
            happyView.setEffect(glow);
        });

        sadView.setOnMouseEntered(e -> {
            sadView.setEffect(shadow);
        });

        sadView.setOnMouseExited(e -> {
            sadView.setEffect(null);
        });

        sadView.setOnMouseClicked(e -> {
            sadView.setEffect(glow);
        });


        TextField userComment = new TextField();
        Label userLabel = new Label("Comment:");

        VBox centerLayout = new VBox();

        HBox comment = new HBox();
        comment.getChildren().addAll(userLabel, userComment);
        comment.setAlignment(Pos.CENTER);

        HBox emojis = new HBox();
        emojis.getChildren().addAll(happyView,sadView);
        emojis.setAlignment(Pos.CENTER);


        centerLayout.getChildren().addAll(emojis,comment);
        centerLayout.setAlignment(Pos.CENTER);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setLeft(leftMenu);
        mainLayout.setCenter(centerLayout);
        mainLayout.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin.css");
        Scene scene = new Scene(undecorator, 600,550);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        LoginBox.display("Login");
        //Users user1 = new Users("Svensson.Karl@iCloud.com", "1234", "Karl");
        System.out.println("Hej kalle");
    }


    public static void main(String[] args) {
        launch(args);
        System.out.println("test");
    }
}
