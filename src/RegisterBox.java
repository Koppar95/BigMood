import insidefx.undecorator.Undecorator;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class RegisterBox {

    public static void display(String title){

        Stage window = new Stage();
        window.setMinWidth(50);
        window.setMinHeight(200);
        window.initStyle(StageStyle.TRANSPARENT);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });
        window.resizableProperty().asObject().setValue(false);
        //GridPane for register
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Instructions
        Label instructionsLabel = new Label();
        instructionsLabel.setText("Enter following to register");
        GridPane.setConstraints(instructionsLabel,1,2);

        //Username Label
        Label userLabel = new Label("Email:");
        GridPane.setConstraints(userLabel, 0,3);

        //Username Input
        TextField userInput = new TextField();
        userInput.setPromptText("Enter Email");
        userInput.setMaxSize(200,5);
        GridPane.setConstraints(userInput,1,3);

        //Password Label
        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0,4);

        //Password Input
        TextField passwordInput = new TextField();
        passwordInput.setPromptText("Enter Password");
        GridPane.setConstraints(passwordInput, 1,4);
        passwordInput.setMaxSize(200,5);

        //Name Label
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0,5);

        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your Name");
        nameInput.setMaxSize(200,5);
        GridPane.setConstraints(nameInput, 1,5);

        //Age Label
        Label ageLabel = new Label("Date of Birth:");
        GridPane.setConstraints(ageLabel, 0,6);

        //Age Input
        TextField ageInput = new TextField();
        ageInput.setPromptText("Enter your date of birth");
        ageInput.setMaxSize(200,5);
        GridPane.setConstraints(ageInput, 1,6);

        //Height Label
        Label heightLabel = new Label("Height:");
        GridPane.setConstraints(heightLabel, 0, 7);

        //Height Input
        TextField heightInput = new TextField();
        heightInput.setPromptText("Enter your height");
        heightInput.setMaxSize(200,5);
        GridPane.setConstraints(heightInput,1,7);


        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 0,8);
        registerButton.setOnAction(e-> {
                    if(registerUser(userInput, passwordInput, nameInput, ageInput, heightInput)){
                        window.close();
                    }
                    else{
                        AlertBox.display("Error", "Something went wrong");
                    }

                }
        );
;
        Scene layout = new Scene(grid, 400, 50);
        grid.getChildren().addAll(
                userLabel,userInput,passwordLabel,
                passwordInput, nameLabel, nameInput,
                ageLabel, ageInput, heightLabel,
                heightInput,registerButton, instructionsLabel
        );

        //layout.setAlignment(Pos.CENTER);

        Undecorator undecorator = new Undecorator(window,grid);
        undecorator.getStylesheets().add("bmSkin.css");
        undecorator.setMinSize(500,300);

        //layout.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");

        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);

        window.setScene(scene);
        window.showAndWait();


    }

    private static boolean registerUser(TextField email, TextField password, TextField name, TextField DoF, TextField height){
        boolean added;
        int age = Integer.parseInt(DoF.getText());
        int h = Integer.parseInt(height.getText());
        Users u1 = new Users(email.getText(), password.getText(), name.getText(), age, h);
        return u1.addToDB();
    }
}
