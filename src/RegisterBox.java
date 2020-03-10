import insidefx.undecorator.Undecorator;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;

/**
 * Class for creating an RegisterBox
 * Uses a GridPane to ask the user to input information
 * Checks that all input is in correct format before registration to MongoDB
 * @author Karl Svensson
 * @version 1.1
 */
public class RegisterBox {
    /**
     * Creates a new TextField for user input
     * @param promptText promptText to be displayed in field.
     * @param column Which column in the GridPane.
     * @param row Which row in the GridPane
     * @return The new TextField
     * @since 1.1
     */
    private static TextField addTextField(String promptText, int column, int row){
        TextField textField = new TextField();
        textField.setPromptText(promptText);
        textField.setMaxSize(200, 5);
        GridPane.setConstraints(textField,column,row);
        return textField;
    }

    /**
     * Creates a Label
     * @param title The title of the Label to be displayed.
     * @param column Which column in the GridPane.
     * @param row which row in the GridPane.
     * @return The new label.
     * @since 1.1
     */
    private static Label addLabel(String title, int column, int row){
        Label label = new Label(title);
        GridPane.setConstraints(label,column, row);
        return label;
    }

    /**
     * Creates a new PasswordField for user input of password.
     * @param promptText promptText to be displayed in field.
     * @param column Which column in the GridPane.
     * @param row Which row in the GridPane.
     * @return The new PasswordField.
     * @since 1.1
     */
    private static PasswordField addPassField(String promptText, int column, int row){
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(promptText);
        passwordField.setMaxSize(200,5);
        GridPane.setConstraints(passwordField, column, row);
        return passwordField;
    }
    /**
     * Creates the RegisterBox screen with all TextFields and Labels.
     * Checks valid inputs.
     * @param title Title of the window.
     * @since 1.1
     */
    public static void display(String title){
        Stage window = new Stage();
        window.setMinWidth(100);
        window.setMinHeight(250);
        window.initStyle(StageStyle.TRANSPARENT);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setOnCloseRequest(e ->{
            System.exit(0);
        });
        window.resizableProperty().asObject().setValue(false);

        //GridPane for registerWindow
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Labels for input fields
        Label usernameLabel = addLabel("Username:", 0,3);
        Label nameLabel = addLabel("Name:", 0,4);
        Label dobLabel = addLabel("Date of Birth:", 0,5);
        Label passwordLabel = addLabel("Password:", 0,7);
        Label passwordLabel2 = addLabel("Re-Enter Password:", 0,8);
        Label warningLabel = addLabel("", 2,8);

        //Date of birth choiceBox
        DatePicker datePicker = new DatePicker();
        GridPane.setConstraints(datePicker, 1,5);

        //Input fields
        TextField userInput = addTextField("Enter Username", 1,3);
        TextField nameInput = addTextField("Enter your name",1,4);
        PasswordField passwordInput = addPassField("Enter Password", 1,7);
        PasswordField passwordInput2 = addPassField("Re-Enter Password", 1,8);

        Button cancelButton = new Button("Cancel");
        GridPane.setConstraints(cancelButton, 2,10);
        Button registerButton = new Button("Register");
        GridPane.setConstraints(registerButton, 1,10);

        /* Disable registerButton if TextFields are empty or if TextField contains wrong information */
        BooleanBinding booleanBinding = new BooleanBinding(){
            {
                super.bind(userInput.textProperty(),
                        passwordInput.textProperty(),
                        passwordInput2.textProperty(),
                        nameInput.textProperty());
            }
            @Override
            protected boolean computeValue(){
                return ((userInput.getText().isEmpty() || passwordInput.getText().isEmpty() || nameInput.getText().isEmpty() || datePicker.getValue() == null || !(passwordInput.getText().matches(passwordInput2.getText()))));
            }
        };
        registerButton.disableProperty().bind(booleanBinding);

        /* Check that passwords matches */
        passwordInput2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(passwordInput.getText().matches(passwordInput2.getText())){
                    warningLabel.setText("");
                }
                else{
                    warningLabel.setText("Passwords does not match");
                    warningLabel.setTextFill(Color.rgb(180, 30, 30));
                }
            }
        });

        /* Register the new user when pressing button */
        registerButton.setOnAction(e-> {
            if(registerUser(userInput, passwordInput, nameInput, datePicker.getValue())) {
                window.close();
            }
            else {
                     AlertBox.display("Error", "Something went wrong");
            }
        });

        cancelButton.setOnAction(e-> window.close());

        grid.getChildren().addAll(
                usernameLabel,userInput, nameInput, datePicker,passwordLabel,
                passwordInput, passwordLabel2,passwordInput2,nameLabel,
                dobLabel,registerButton, warningLabel, cancelButton
        );

        window.setOnCloseRequest(e -> window.close());

        Undecorator undecorator = new Undecorator(window,grid);
        undecorator.getStylesheets().add("bmSkinTransparent-"+Configuration.color+".css");
        undecorator.setMinSize(520,350);
        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.showAndWait();
    }

    /**
     * Creates a user object with values from input data.
     * @param username Username from input.
     * @param password Password from input.
     * @param name name from input.
     * @param Dob Date of Birth from input.
     * @return True if user was added to DB, false is Error.
     * @since 1.1
     */
    private static boolean registerUser(TextField username, PasswordField password, TextField name, LocalDate Dob){
        String em = username.getText().toLowerCase();
        User u1 = new User(em, password.getText(), name.getText(), Dob);
        return u1.addToDB();
    }
}
