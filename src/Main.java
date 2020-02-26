import com.mongodb.Mongo;
import insidefx.undecorator.Undecorator;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.application.Platform;
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

import java.util.function.Consumer;

public class Main extends Application {
    boolean menuState = false;
    Stage window;

    private Button addMenuItem(String label, int with, EventHandler<? super MouseEvent> active) {
        Button b = new Button(label);
        b.setPrefWidth(with);
        b.getStyleClass().add("custom-menu-button");
        b.setOnMouseClicked(active);
        return b;
    }

    static MongoDB userConn = new MongoDB("UsersDB","Users");
    static MongoDB moodConn = new MongoDB("UsersDB","MoodData");

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Visa LoginBox
        LoginBox.display();
        //

        window = primaryStage;
        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(StartWindow.makeStartWindow());

        Undecorator undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin.css");
        Scene scene = new Scene(undecorator, 900,800);
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

        //Logout window button
        Button logout = new Button("Logout");
        logout.setPrefWidth(100);
        logout.getStyleClass().add("custom-menu-button");
        logout.setOnMouseClicked(e-> {
            window.hide();
            LoginBox.display();
            mainLayout.setCenter(StartWindow.makeStartWindow());
            window.show();
        });

        Button mood = addMenuItem("Mood Tracker",100, e-> {
            mainLayout.setCenter(MoodWindow.makeMoodWindow());
        }
        );

        Button data = addMenuItem("Data", 100, e-> {
            mainLayout.setCenter(DataWindow.makeDataWindow());
        });
        menu.getChildren().addAll(mood, data,
                            addMenuItem("Start", 100, e->{
                                LoadWindow loadWindow = new LoadWindow(window.getX()+70,window.getY()+24);
                                loadWindow.startLoadThread();
                                mainLayout.setCenter(StartWindow.makeStartWindow());
                                loadWindow.close();
                            }
                            ),
                            addMenuItem("Edit Profile", 100, e-> mainLayout.setCenter(ProfileWindow.makeProfileWindow())), logout);

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
    }

}