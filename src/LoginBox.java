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

public class LoginBox {
    public static Document currentUser;

    public static void display(){

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

            LoadWindow loadWindow = new LoadWindow(window.getX()+16,window.getY()+16);
            loadWindow.startLoadThread();

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
            loadWindow.close();
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
        currentUser = Main.userConn.getDocument("Username",username);
    }

}
