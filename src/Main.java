import insidefx.undecorator.Undecorator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;

import java.awt.*;

public class Main extends Application {
//TESTING
    Stage window;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");

        window.initStyle(StageStyle.TRANSPARENT);

        //Smileys :)
        Image happy = new Image("/happy.png");
        Image sad = new Image("sad.png");
        ImageView happyView = new ImageView(happy);
        ImageView sadView = new ImageView(sad);

        //Effect for smiley hoover
        DropShadow shadow = new DropShadow();
        Glow glow = new Glow();

        //Mouse interaction with smileys
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

        //User comments
        TextField userComment = new TextField();
        Label userLabel = new Label("Comment:");

        //Boxes for smileys and comments
        HBox comments = new HBox();
        //comments.getChildren().addAll(userLabel,userComment);
        comments.setAlignment(Pos.CENTER);

        HBox emojis = new HBox();
        emojis.getChildren().addAll(happyView,sadView);
        emojis.setAlignment(Pos.CENTER);


        VBox centerLayout = new VBox();

        BorderPane mainLayout = new BorderPane();

        centerLayout.getChildren().addAll(emojis,comments);
        centerLayout.setAlignment(Pos.CENTER);
        mainLayout.setCenter(centerLayout);

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
