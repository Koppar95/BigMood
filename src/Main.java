import insidefx.undecorator.Undecorator;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class Main extends Application {
    boolean menuState = false;
    Stage window;
    String currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Visa LoginBox
        LoginBox.display();
        //

        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);

        VBox mainMood = MoodWindow.makeMoodWindow();
        VBox mainStart = StartWindow.makeStartWindow();

        BorderPane mainLayout = new BorderPane();

        mainLayout.setCenter(mainStart);

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin.css");
        Scene scene = new Scene(undecorator, 600,550);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();

        // MENU PART START
        //load cc stylesheet
        mainLayout.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //main window button
        Button originBtn = new Button();
        Image image = new Image(getClass().getResourceAsStream("smallMenu.png"));
        originBtn.setGraphic(new ImageView(image));
        originBtn.setAlignment(Pos.CENTER_LEFT);
        originBtn.setPrefWidth(100);
        originBtn.getStyleClass().add("custom-open-menu-button");
        HBox fileRoot = new HBox();
        fileRoot.getChildren().add(originBtn);
        mainLayout.setTop(fileRoot);

        VBox menu = new VBox();
        menu.setSpacing(2);
        menu.setStyle("-fx-background-color: #668B4E;");
        menu.setFillWidth(true);

        VBox placeholder = new VBox();
        placeholder.setSpacing(2);
        placeholder.setStyle("-fx-background-color: transparent;");
        placeholder.setFillWidth(true);
        placeholder.setMinWidth(100);
        mainLayout.setLeft(placeholder);

        // Creating window buttons to menu!!!

        //Mood window button
        Button mood = new Button("Mood tracker");
        mood.setPrefWidth(100);
        mood.getStyleClass().add("custom-menu-button");
        mood.setOnMouseClicked(e -> {
            mainLayout.setCenter(mainMood);
        });

        //Start window button
        Button start = new Button("Start");
        start.setPrefWidth(100);
        start.getStyleClass().add("custom-menu-button");
        start.setOnMouseClicked(e-> {
            mainLayout.setCenter(mainStart);
        });

        //Logout window button
        Button logout = new Button("Logout");
        logout.setPrefWidth(100);
        logout.getStyleClass().add("custom-menu-button");
        logout.setOnMouseClicked(e-> {
            window.hide();
            LoginBox.display();
            mainLayout.setCenter(mainStart);
            window.show();
        });

        menu.getChildren().addAll(start,mood,logout);
        VBox.setVgrow(mood, Priority.ALWAYS);


        originBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if (getMenuState()) {

                    FadeTransition hideMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    hideMenuTransition.setFromValue(1.0);
                    hideMenuTransition.setToValue(0.0);
                    hideMenuTransition.play();
                    setMenuState(false);
                } else {

                    FadeTransition showMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    showMenuTransition.setFromValue(0.0);
                    showMenuTransition.setToValue(1.0);
                    mainLayout.setLeft(menu);
                    showMenuTransition.play();
                    setMenuState(true);

                }
            }
        });
        /*
        infoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (menuState) {
                    System.out.println("I wooooork");
                }
            }
        }); */

        // MENU PART END


    }

    // getters and setters to see if menu is open or not
    private boolean getMenuState(){
        return menuState;
    }
    private void setMenuState(boolean newState){
        menuState=newState;
    }




    public static void main(String[] args) {
        launch(args);
        System.out.println("test");
    }


}