import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginBox {

    public static void display(String title){

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);
        window.setMinHeight(200);
        window.resizableProperty().asObject().setValue(false);

        Label userLabel = new Label();
        userLabel.setText("Username");

        TextField userInput = new TextField();
        TextField passwordInput = new TextField();
        userInput.setMaxSize(200,5);

        Label passwordLabel = new Label();
        passwordLabel.setText("Password");
        passwordInput.setMaxSize(200,5);

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e->window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(userLabel,userInput,passwordLabel,passwordInput,loginButton);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


    }
}
