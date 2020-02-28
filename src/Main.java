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
     * @param menuState A boolean that is true when the menu is open,false when menu is closed.
     * @param window Stage that is the basic window.
     */
    boolean menuState = false;
    Stage window;


    /**
     *addMenuItem is a method used to create a button to be used in the menu.
     *
     * @param label String with the text that will show on the button.
     * @param width The width of the the button to be.
     * @param active The event that will activate when button is clicked.
     * @return Returns a button.
     */
    private Button addMenuItem(String label, int width, EventHandler<? super MouseEvent> active) {
        Button b = new Button(label);
        b.setPrefWidth(width);
        b.getStyleClass().add("custom-menu-button");
        b.setOnMouseClicked(active);
        return b;
    }

    static MongoDB userConn = new MongoDB("UsersDB","Users");
    static MongoDB moodConn = new MongoDB("UsersDB","MoodData");

    @Override
    /**
     * @param mainLayout BorderPane, the layout for the entire window.
     * @param undecorator //TODO
     * @param scene the part of the window where the actual application is shown
     * @param originBtn A button that opens and closes the menu.
     * @param image The Image that is shown on the originBtn.
     * @param fileRoot HBox where the originButton is placed
     * @param menu The VBox that works as the menu that contains all buttons(except the log in button)
     * @param placeholder A Vbox tho occupy the space while menu is not showing, so that the other parts of the
     *application won't move
     * @param logout Button that when clicked, makes you log out.
     * @param mood Button that when clicked, changes view to show MoodWindow.
     * @param data Button that when clicked, changes view to show DataWindow
     * @param
     *
     *
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

        // MENU PART START
        //load cc stylesheet
        mainLayout.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //main window button
        Button originBtn = new Button();
        Image image = new Image(getClass().getResourceAsStream("smallMenu.png"));
        originBtn.setGraphic(new ImageView(image));
        originBtn.setAlignment(Pos.CENTER);
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
        logout.getStyleClass().add("custom-menu-logoutbutton");

        logout.setOnMouseClicked(e-> {
            if(menuState) {
                window.hide();
                LoginBox.display();
                mainLayout.setCenter(new StartWindow());
                window.show();
            }
        });

        menu.getChildren().addAll(
                            addMenuItem("Start", 100, e->{
                                if(menuState) {
                                    LoadWindow loadWindow = new LoadWindow(window.getX() + 16, window.getY() + 16);
                                    loadWindow.startLoadThread();
                                    mainLayout.setCenter(new StartWindow());
                                    loadWindow.close();
                                }
                            }
                            ),addMenuItem("Mood",100, e-> {
                                if(menuState) {
                                 mainLayout.setCenter(new MoodWindow());
                                }
                            }),
                            addMenuItem("Data", 100, e-> {
                                if(menuState) {
                                    mainLayout.setCenter(new DataWindow());
                                }
                            }) ,
                            addMenuItem("Edit Profile", 100, e-> {
                                if(menuState) {
                                    mainLayout.setCenter(new ProfileWindow());
                                }
                            })

                                    );

        VBox.setVgrow(logout, Priority.ALWAYS);


        originBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            /**
             * Animations for the menu that will run when originBtn is pressed.
             */
            public void handle(ActionEvent event) {
                if (getMenuState()) {

                    FadeTransition hideMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    FadeTransition hideLogoutTransition = new FadeTransition(Duration.millis(500), logout);

                    hideMenuTransition.setFromValue(1.0);
                    hideMenuTransition.setToValue(0.0);

                    hideLogoutTransition.setFromValue(1.0);
                    hideLogoutTransition.setToValue(0.0);

                    hideMenuTransition.play();
                    hideLogoutTransition.play();

                    setMenuState(false);
                } else {

                    FadeTransition showMenuTransition = new FadeTransition(Duration.millis(500), menu);
                    FadeTransition showLogoutTransition = new FadeTransition(Duration.millis(500), logout);

                    showMenuTransition.setFromValue(0.0);
                    showMenuTransition.setToValue(1.0);

                    showLogoutTransition.setFromValue(0.0);
                    showLogoutTransition.setToValue(1.0);

                    mainLayout.setLeft(menu);
                    mainLayout.setBottom(logout);

                    showMenuTransition.play();
                    showLogoutTransition.play();
                    setMenuState(true);

                }
            }
        });
    }

    // getters and setters to see if menu is open or not

    /**
     * @return Returns menuState to know if menu is open or not
     */
    private boolean getMenuState(){
        return menuState;
    }

    /**
     * @param newState boolean value to set menuState to.
     */
    private void setMenuState(boolean newState){
        menuState=newState;
    }

    public static void main(String[] args) {
        launch(args);
    }

}