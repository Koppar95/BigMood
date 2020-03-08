import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Class to implement drop down side bar menu containing buttons to windows.
 * @author Rebecka Axelborn
 * @version 1.2
 */

public class Menu {
    /**
     * A boolean that is true when the menu is open,false when menu is closed.
     */
    private static boolean menuState=false;
    /**
     *A Stage that is the initial window.Used in menu to be able to hide the window during logout and login.
     */
    private Stage window;
    /**
     *BorderPane which is the main layout for the application. Used in Menu to be able to add the menu to the layout.
     */
    private BorderPane mainLayout;
    /**
     * A Session that represents the current user.
     */
    private Session currentSession;

    /**
     * Parameters that are used in the initialisation of the menu are set.
     * @param window Takes in the Stage where the menu is to be added.
     * @param mainLayout Takes in the layout (BorderPane) where the menu is to be added.
     * @param currentSession Takes in the MongoDB current user that menu is to use.
     * @since 1.2
     */
    public Menu(Stage window, BorderPane mainLayout, Session currentSession){
        this.window=window;
        this.mainLayout=mainLayout;
        this.currentSession=currentSession;
    }

    /**The menu gets initialised. A button is created that when clicked will run a FadeTransition to show or remove the menu. The
     * fade in menu contains window buttons and a logout button.
     * @1.2
     */
    public void init(){
        // MENU PART START
        //load css stylesheet
        mainLayout.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        //main window button
        Button originBtn = new Button();
        Image image = new Image(getClass().getResourceAsStream("Assets/smallMenu.png"));
        originBtn.setGraphic(new ImageView(image));
        originBtn.setAlignment(Pos.CENTER);
        originBtn.setPrefWidth(100);
        originBtn.getStyleClass().add("custom-open-menu-button-"+Configuration.color);

        HBox fileRoot = new HBox();
        fileRoot.getChildren().add(originBtn);
        mainLayout.setTop(fileRoot);

        VBox menu = new VBox();
        menu.setSpacing(2);
        //menu.setStyle("-fx-background-color: #668B4E;");
        //menu.setStyle("-fx-background-color: #a59115;");
        menu.getStyleClass().add("custom-menu-bar-"+Configuration.color);
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
        logout.getStyleClass().add("custom-menu-logoutbutton-"+Configuration.color);

        logout.setOnMouseClicked(e-> {
            if(menuState) {
                window.hide();
                LoginBox loginBox = new LoginBox(currentSession);
                loginBox.display();
                StartWindow startWindow = new StartWindow(currentSession);
                startWindow.init();
                mainLayout.setCenter(startWindow);

                window.show();
            }
        });

        // Creates and adds buttons connected to windows to menu.
        menu.getChildren().addAll(
                addMenuItem("Start", 100, e->{
                            if(menuState) {
                                StartWindow startwindow = new StartWindow(currentSession);
                                startwindow.init();
                                mainLayout.setCenter(startwindow);
                            }
                        }
                ),addMenuItem("Mood",100, e-> {
                    if(menuState) {
                        mainLayout.setCenter(new MoodWindow(currentSession));
                    }
                }),
                addMenuItem("Data", 100, e-> {
                    if(menuState) {
                        mainLayout.setCenter(new DataWindow(currentSession));
                    }
                }) ,
                addMenuItem("Settings", 100, e-> {
                    if(menuState) {
                        mainLayout.setCenter(new SettingsWindow(currentSession,window));
                    }
                })

        );

        VBox.setVgrow(logout, Priority.ALWAYS);


        originBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
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

    /** getMenuState is a method to get the state of the menu.
     * @return Returns menuState to know if menu is open or not.
     * @since 1.1
     */
    private boolean getMenuState(){
        return menuState;
    }

    /** Method to set the state of the menu.
     * @param newState boolean value to set menuState to.
     * @since 1.1
     */
    private void setMenuState(boolean newState){
        menuState=newState;
    }

    /**
     *addMenuItem is a method used to create a button to be used in the menu.
     *
     * @param label String with the text that will show on the button.
     * @param width An int representing the width of the the button to be.
     * @param active EventHandler, the event that will activate when button is clicked.
     * @return Returns a button.
     * @since 1.2
     */
    private Button addMenuItem(String label, int width, EventHandler<? super MouseEvent> active) {
        Button b = new Button(label);
        b.setPrefWidth(width);
        b.getStyleClass().add("custom-menu-button-"+Configuration.color);
        b.setOnMouseClicked(active);
        return b;
    }

}
