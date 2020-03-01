import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.bson.Document;

import java.awt.*;

/**
 *
 */
public class ProfileWindow extends VBox {
    static String currentUserName = LoginBox.currentUser.get("Username").toString();
    static int spacing = 8;
    static int minWidth =115;
    static Insets boxPadding = new Insets(10, 10, 10, 10);

    public void ProfileWindow() {

    }

    public ProfileWindow() {
        super();
        this.setPadding(new Insets(10, 50, 50, 50));
        this.setSpacing(spacing);

        HBox nameUpdate = changeName();
        HBox heightUpdate = changeHeight();
        VBox passwordUpdate = changePassword();

        this.getChildren().addAll(nameUpdate,heightUpdate, passwordUpdate);


    }

    private static HBox changeHeight() {
        HBox changeHeightBox = new HBox();
        changeHeightBox.setSpacing(spacing);
        changeHeightBox.setPadding(boxPadding);
        changeHeightBox.setBackground(Background.EMPTY);
        changeHeightBox.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

        String userHeight = (LoginBox.currentUser.get("Height")).toString();

        Label heightlbl = new Label("Height:");
        heightlbl.setMinWidth(minWidth);

        TextField height = new TextField();
        height.setPromptText(userHeight + "(cm)");

        Button changeHeightBtn = new Button();
        changeHeightBtn.setText("Update");
        changeHeightBtn.getStyleClass().add("custom-profile-edit-button-"+Configuration.color);

        changeHeightBox.getChildren().addAll(heightlbl,height, changeHeightBtn);

        changeHeightBtn.setOnMouseClicked(e -> {
            String newHeight = height.getText();
            Integer updateValue = Integer.parseInt(newHeight);
            Main.userConn.updateIntValue("Username", currentUserName, "Height", updateValue);
            height.clear();
            height.setPromptText(newHeight + "(cm)");
            LoginBox.setCurrentUser(currentUserName);
        });

        Label wrongHeight = new Label();

        height.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if(s.matches("\\d+")){
                    wrongHeight.setText("");
                }
                else{
                    wrongHeight.setText("Enter height in cm");
                    wrongHeight.setTextFill(Color.rgb(180, 30, 30));
                }
                changeHeightBox.getChildren().add(wrongHeight);
            }
        });


        return changeHeightBox;
    }

    private static HBox changeName(){
        HBox changeNameBox = new HBox();
        changeNameBox.setSpacing(spacing);
        changeNameBox.setPadding(boxPadding);
        changeNameBox.setBackground(Background.EMPTY);
        changeNameBox.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

        String usersName= (LoginBox.currentUser.get("Name").toString());

        Label nameLbl = new Label("Name:");
        nameLbl.setMinWidth(minWidth);

        TextField name = new TextField();
        name.setPromptText(usersName);

        Button changeNameBtn= new Button();
        changeNameBtn.setText("Update");
        changeNameBtn.getStyleClass().add("custom-profile-edit-button-"+Configuration.color);


        changeNameBtn.setOnMouseClicked(e->{
            //MongoDB base = new MongoDB("UsersDB", "Users");
            String updateValue = name.getText();
            Main.userConn.updateValue("Username",currentUserName,"Name",updateValue);
            name.clear();
            name.setPromptText(updateValue);
            LoginBox.setCurrentUser(currentUserName);
        });

        changeNameBox.getChildren().addAll(nameLbl,name,changeNameBtn);

        return changeNameBox;
    }

        private static VBox changePassword() {
            VBox changePassword = new VBox();
            changePassword.setSpacing(spacing);
            changePassword.setPadding(boxPadding);


            changePassword.setBackground(Background.EMPTY);
            changePassword.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

            HBox currentPasswordBox = new HBox();
            currentPasswordBox.setSpacing(spacing);
            HBox newPasswordBox = new HBox();
            newPasswordBox.setSpacing(spacing);
            HBox matchPasswordBox = new HBox();
            matchPasswordBox.setSpacing(spacing);
            HBox noMatchBox = new HBox();
            noMatchBox.setSpacing(spacing);
            HBox wrongPasswordBox = new HBox();
            wrongPasswordBox.setSpacing(spacing);

            Label currentPassword = new Label("Current password");
            currentPassword.setMinWidth(minWidth);
            Label newPassword = new Label("New password");
            newPassword.setMinWidth(minWidth);
            Label matchPassword = new Label("Enter password again");
            matchPassword.setMinWidth(minWidth);
            Label placeholderLabel = new Label();
            placeholderLabel.setMinWidth(minWidth);
            Label placeholderLabel2 = new Label();
            placeholderLabel2.setMinWidth(minWidth);

            PasswordField currentPasswordField = new PasswordField();
            PasswordField newPasswordField = new PasswordField();
            PasswordField matchPasswordField = new PasswordField();


            Label errorWrongPassword = new Label();
            Label errorPasswordUnmatch = new Label();

            Button changePasswordBtn = new Button();
            changePasswordBtn.setText("Update");
            changePasswordBtn.getStyleClass().add("custom-profile-edit-button-"+Configuration.color);

            changePasswordBtn.setOnAction(e -> {
                int passwordInputHashed = currentPasswordField.getText().hashCode();
                int userHashedPassword = LoginBox.currentUser.getInteger("Password");
                int newPasswordHash = newPasswordField.getText().hashCode();
                int matchPasswordHash = matchPasswordField.getText().hashCode();

                if (userHashedPassword == passwordInputHashed && newPasswordHash==matchPasswordHash) {
                    errorWrongPassword.setText("");
                    Main.userConn.updateIntValue("Username",currentUserName,"Password",newPasswordHash);
                    currentPasswordField.clear();
                    newPasswordField.clear();
                    matchPasswordField.clear();
                    matchPasswordField.setPromptText("Password changed");
                    System.out.println("Password changed");

                } else if (!(userHashedPassword == passwordInputHashed)) {
                    errorWrongPassword.setText("Fel lösenord");
                    errorWrongPassword.setTextFill(Color.rgb(180, 30, 30));
                    Toolkit.getDefaultToolkit().beep();
                }
                else{
                    Toolkit.getDefaultToolkit().beep();
                }
            });

            matchPasswordField.textProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    if(newPasswordField.getText().matches(matchPasswordField.getText())){
                        errorPasswordUnmatch.setText("");
                    }
                    else{
                        errorPasswordUnmatch.setText("Does not match");
                        errorPasswordUnmatch.setTextFill(Color.rgb(180, 30, 30));
                    }
                }
            });





            currentPasswordBox.getChildren().addAll(currentPassword, currentPasswordField);
            wrongPasswordBox.getChildren().addAll(placeholderLabel2,errorWrongPassword);
            newPasswordBox.getChildren().addAll(newPassword,newPasswordField);
            noMatchBox.getChildren().addAll(placeholderLabel,errorPasswordUnmatch);
            matchPasswordBox.getChildren().addAll(matchPassword,matchPasswordField,changePasswordBtn);
            changePassword.getChildren().addAll(currentPasswordBox, wrongPasswordBox, newPasswordBox,
                                                matchPasswordBox, noMatchBox);

            return changePassword;


    }
}