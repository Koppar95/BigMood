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

public class Menu {
    /** In Main the side bar menu is created and the buttons are connected to instances of other classes that are various
     * windows.
     * menuState A boolean that is true when the menu is open,false when menu is closed.
     */
    private static boolean menuState=false;

    /**
     * A button that opens and closes the menu, The Image that is shown on the originBtn.
     * HBox where the originButton is placed, HBox where the originButton is placed.
     * The VBox that works as the menu that contains all buttons(except the log in button).
     * A Vbox tho occupy the space while menu is not showing, so that the other parts of the.
     * Button that when clicked, makes you log out.
     * Button that when clicked, changes view to show DataWindow
     * @param window Stage that is the initial window. Used in menu to be able to hide the window during logout and login.
     * @param mainLayout BorderPane which is the main layout for the application. Used in Menu to be able to add the menu
     * to the layout.
     */
    public Menu(Stage window, BorderPane mainLayout){
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
                LoginBox.display();
                mainLayout.setCenter(new StartWindow());
                window.show();
            }
        });

        // Creates and adds buttons connected to windows to menu.
        menu.getChildren().addAll(
                addMenuItem("Start", 100, e->{
                            if(menuState) {
                                //LoadWindow loadWindow = new LoadWindow(window.getX() + 16, window.getY() + 16);
                                //loadWindow.startLoadThread();
                                mainLayout.setCenter(new StartWindow());
                                //loadWindow.close();
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
            /**
             * Animations for the menu that will run when originBtn is pressed.
             */
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
        b.getStyleClass().add("custom-menu-button-"+Configuration.color);
        b.setOnMouseClicked(active);
        return b;
    }

}
