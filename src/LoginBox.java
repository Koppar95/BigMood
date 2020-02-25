import insidefx.undecorator.Undecorator;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import java.awt.*;

public class LoginBox {
    public static Document currentUser;

    public static void display(){
        //MongoDB conn = new MongoDB("UsersDB","Users");
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

        //Load Gif
        /*Image loadGif = new Image("/load.gif");
        ImageView loadGifView = new ImageView(loadGif);
        loadGifView.setFitHeight(20);
        loadGifView.setFitWidth(20);
        loadGifView.setVisible(false);*/
        //

        //Error Label
        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.rgb(180,30,30));
        //

        //Buttons
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e->{
            Document user = Main.userConn.getDocument("Username",userInput.getText().toLowerCase());
            if(user !=null) {
                String userEmail = user.get("Username").toString().toLowerCase();
                String userInputEmail = userInput.getText().toLowerCase();

                int passwordInputHashed = passwordInput.getText().hashCode();
                int userHashedPassword = user.getInteger("Password");

                if(userEmail.equals(userInputEmail) && userHashedPassword == passwordInputHashed){
                    //INLOGGNING GODTAGEN
                    window.close();
                    currentUser=user;
                }else{
                    errorLabel.setText("Fel användarnamn eller lösen");
                }
            }else{
                    //Användarnamn ej registrerad
                errorLabel.setText("Användare ej registrerad");
            }

            /*loadGifView.setVisible(false);*/

        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e->RegisterBox.display("Register"));
        //

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
        undecorator.getStylesheets().add("bmSkinTransparent.css");
        undecorator.setMinSize(500,300);

        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);

        window.setScene(scene);
        window.showAndWait();
        //
    }

    public static void setCurrentUser(String username){
        MongoDB conn = new MongoDB("UsersDB","Users");
        currentUser = conn.getDocument("Username",username);
    }
}
