import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class Main extends Application {
//TESTING
    Stage window;
    boolean menuState=false;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        window = primaryStage;
        window.setTitle("Big Mood");

        //Effect for smiley hoover
        DropShadow shadow = new DropShadow();
        Glow glow = new Glow();

       /* VBox leftMenu = new VBox(15);
        Button moodButton = new Button("mood");
        Button userButton = new Button("users");
        leftMenu.getChildren().addAll(moodButton,userButton);*/


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
       // mainLayout.setLeft(leftMenu);
        mainLayout.setCenter(centerLayout);
        mainLayout.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");


        // MENU PART START
        //load cc stylesheet
        mainLayout.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //main window button
        Button originBtn = new Button();
        originBtn.setText("Menu");
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

        // creating menu bar
        Button infoBtn = new Button("Info");
        infoBtn.setPrefWidth(100);
        infoBtn.getStyleClass().add("custom-menu-button");
        Button newBtn = new Button("New");
        newBtn.setPrefWidth(100);
        newBtn.getStyleClass().add("custom-menu-button");
        Button openBtn = new Button("Open");
        openBtn.setPrefWidth(100);
        openBtn.getStyleClass().add("custom-menu-button");
        menu.getChildren().addAll(infoBtn, newBtn, openBtn);
        VBox.setVgrow(infoBtn, Priority.ALWAYS);


        originBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                if(getMenuState()){

                    FadeTransition hideMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    hideMenuTransition.setFromValue(1.0);
                    hideMenuTransition.setToValue(0.0);
                    hideMenuTransition.play();
                    setMenuState(false);
                }
                else{

                    FadeTransition showMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    showMenuTransition.setFromValue(0.0);
                    showMenuTransition.setToValue(1.0);
                    mainLayout.setLeft(menu);
                    showMenuTransition.play();
                    setMenuState(true);

                }
            }
        });

            infoBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (menuState) {
                        System.out.println("I wooooork");
                    }
                }
            });

        // MENU PART END

        primaryStage.setScene(new Scene(mainLayout, 600, 550));
        primaryStage.show();

        LoginBox.display("Login");

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
        System.out.println("test if main is running");
    }
}
