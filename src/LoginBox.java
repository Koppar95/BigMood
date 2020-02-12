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

public class LoginBox {

    public static void display(String title){
        //Stage initialization
        Stage window = new Stage();
        window.setMinWidth(400);
        window.setMinHeight(200);
        window.initStyle(StageStyle.TRANSPARENT);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
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

        //Buttons
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e->window.close());

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e->RegisterBox.display("Register"));
        //

        //Layout initialization and component positioning
        VBox layout = new VBox(10);
        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(loginButton,registerButton);
        buttonLayout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(userLabel,userInput,passwordLabel,passwordInput,buttonLayout);
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
}
