import insidefx.undecorator.Undecorator;
import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterBox {

    public interface Delegate {
        public void registerUser();
        public void cancel();
    }

    public Delegate delegate;

    public static void display(String title){


        Stage window = new Stage();
        window.setMinWidth(50);
        window.setMinHeight(250);
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
        PasswordField passwordInput = new PasswordField();
        passwordInput.setPromptText("Enter Password");
        GridPane.setConstraints(passwordInput, 1,4);
        passwordInput.setMaxSize(200,5);

        //Password Label 2
        Label passwordLabel2= new Label("Re-Enter Password:");
        GridPane.setConstraints(passwordLabel2, 0,5);

        //Password Input 2
        PasswordField passwordInput2 = new PasswordField();
        passwordInput2.setPromptText("Re-Enter Password");
        GridPane.setConstraints(passwordInput2, 1,5);
        passwordInput.setMaxSize(200,5);

        //Name Label
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0,6);

        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Enter your Name");
        nameInput.setMaxSize(200,5);
        GridPane.setConstraints(nameInput, 1,6);

        //Age Label
        Label ageLabel = new Label("Date of Birth:");
        GridPane.setConstraints(ageLabel, 0,7);

        //Age Input
        TextField ageInput = new TextField();
        ageInput.setPromptText("Enter your date of birth");
        ageInput.setMaxSize(200,5);
        GridPane.setConstraints(ageInput, 1,7);

        //Height Label
        Label heightLabel = new Label("Height:");
        GridPane.setConstraints(heightLabel, 0, 8);

        //Height Input
        TextField heightInput = new TextField();
        heightInput.setPromptText("Enter your height");
        heightInput.setMaxSize(200,5);
        GridPane.setConstraints(heightInput,1,8);

        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1,10);

        Label warningLabel = new Label();
        GridPane.setConstraints(warningLabel, 2, 5);

        /* Check that passwords matches */
        passwordInput2.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if(!(passwordInput.getText().matches(passwordInput2.getText()))){
                    warningLabel.setText("Passwords does not match");
                    warningLabel.setTextFill(Color.rgb(180, 30, 30));
                }
                else{
                    warningLabel.setText("");
                }
            }
        });



        /* Disable registerButton if TextFields are empty */
        BooleanBinding booleanBinding = new BooleanBinding() {
            {
                super.bind(userInput.textProperty(),
                        passwordInput.textProperty(),
                        passwordInput2.textProperty(),
                        nameInput.textProperty(),
                        ageInput.textProperty(),
                        heightInput.textProperty());
            }
            @Override
            protected boolean computeValue() {
                return ((userInput.getText().isEmpty() || passwordInput.getText().isEmpty() || nameInput.getText().isEmpty() || ageInput.getText().isEmpty() || heightInput.getText().isEmpty()) || !(passwordInput.getText().matches(passwordInput2.getText())));
            }
        };

        registerButton.disableProperty().bind(booleanBinding);
        /* Register the new user when pressing button */
        registerButton.setOnAction(e-> {
            /*
            if (delegate != null) {
                delegate.registerUser();
            }
             */

            if(registerUser(userInput, passwordInput, nameInput, ageInput, heightInput)) {
                window.close();
            }
            else {
                     AlertBox.display("Error", "Something went wrong");
            }
        });

        grid.getChildren().addAll(
                userLabel,userInput,passwordLabel,
                passwordInput, passwordLabel2,passwordInput2,nameLabel, nameInput,
                ageLabel, ageInput, heightLabel,
                heightInput,registerButton, instructionsLabel, warningLabel
        );

        //layout.setAlignment(Pos.CENTER);

        Undecorator undecorator = new Undecorator(window,grid);
        undecorator.getStylesheets().add("bmSkinTransparent.css");
        undecorator.setMinSize(500,350);

        //layout.setStyle("-fx-background-color: linear-gradient(#E4EAA2, #9CD672);");

        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);

        window.setScene(scene);
        window.showAndWait();


    }

    private static boolean registerUser(TextField email, PasswordField password, TextField name, TextField DoF, TextField height){
        boolean added;
        int age = Integer.parseInt(DoF.getText());
        int h = Integer.parseInt(height.getText());
        Users u1 = new Users(email.getText(), password.getText(), name.getText(), age, h);
        return u1.addToDB();
    }
}
