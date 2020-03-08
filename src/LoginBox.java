import insidefx.undecorator.Undecorator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

/**
 * Class for creating a Login Box and checking login authentication
 * updates user in currentSession on successful login to track who is currently logged in.
 * @author Samuel Leckborn
 * @version 1.1
 */
public class LoginBox {
    /**
     * A Session containing current user.
     */
    private Session currentSession;

    /**
     * Sets current session.
     * @param currentSession Sets incoming Session parameter to this objects currentSession
     */
    public LoginBox(Session currentSession){
    this.currentSession=currentSession;
    }

    /**
     * Initializing and showing the login window.
     * @since 1.1
     */
    public void display(){

        //Stage initialization
        Stage window = new Stage();
        window.setMinWidth(400);
        window.setMinHeight(200);
        window.initStyle(StageStyle.TRANSPARENT);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Login");
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });
        window.resizableProperty().asObject().setValue(false);
        //

        //User input
        Label userLabel = new Label();
        userLabel.setText("Username");
        TextField userInput = new TextField();
        userInput.setMaxSize(200,5);
        //

        //Password input
        Label passwordLabel = new Label();
        passwordLabel.setText("Password");
        PasswordField passwordInput = new PasswordField();
        passwordInput.setMaxSize(200,5);
        //

        //Error Label
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.rgb(180,30,30));
        //

        //Buttons
        Button loginButton = new Button("Login");

        loginButton.setOnAction(e->{
            Document user = Main.userConn.getDocument("Username",userInput.getText().toLowerCase());

            int passwordInputHashed = passwordInput.getText().hashCode();
            String userInputEmail = userInput.getText().toLowerCase();

                if(loginCheck(user, passwordInputHashed, userInputEmail)){
                    window.close();
                    currentSession.setCurrentUser(user);
                }else{
                    errorLabel.setText("Wrong username or password");
                }
        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e->RegisterBox.display("Register"));

        //Layout initialization and component positioning
        VBox layout = new VBox(10);
        HBox buttonLayout = new HBox(10);

        buttonLayout.getChildren().addAll(loginButton,registerButton);
        buttonLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(userLabel,userInput,passwordLabel,passwordInput,buttonLayout,errorLabel);
        layout.setAlignment(Pos.CENTER);
        //

        //Undecorator initialization
        Undecorator undecorator = new Undecorator(window,layout);
        undecorator.getStylesheets().add("bmSkinTransparent-"+Configuration.color+".css");
        undecorator.setMinSize(500,300);

        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);

        window.setScene(scene);
        window.showAndWait();
        //
    }

    /**
     * Compares user input with database database entries.
     * @param user
     * @param passInput
     * @param userInput
     * @return true if userInput and passInput equals that users username and password in the database, ow false.
     * @since 1.1
     */
    private boolean loginCheck(Document user, int passInput, String userInput){
        if(user !=null) {
            String userEmail = user.get("Username").toString().toLowerCase();
            int userHashedPassword = user.getInteger("Password");
            if(userEmail.equals(userInput) && userHashedPassword == passInput) {
                //Login Successful
                return true;
            }
            //Login Unsuccessful
            return false;
        }
        //User is null
        return false;
    }

}
