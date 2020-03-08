import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.*;
import java.io.IOException;

/**
 *  SettingsWindow is a VBox that is the window where the user can control its settings. The user can change Presentation name, password and
 *  window colour scheme.
 * @Author Rebecka Axelborn
 * @version 1.5
 */
public class SettingsWindow extends VBox {
    /**
     * The spacing inbetween the fields.
     */
    static int spacing = 8;
    /**
     * The minimum width of the lable telling the user what the field is used to update.
     */
    static int minWidth =115;
    /**
     * An Insets containing padding for in-between the boxes containing the different update functions.
     */
    static Insets boxPadding = new Insets(10, 10, 10, 10);
    /**
     * A Session representing the current user.
     */
    private Session currentSession;
    /**
     * The initial Stage window of the application.
     */
    private Stage window;

    /**
     * The layout is set and the methods for the settings fields are called and added to the VBox that is this object.
     * @param currentSession Takes in the MongoDB current user that the settings window is to update.
     * @param window Takes in the Stage that is used for the application.
     * @since 1.5
     */

    public SettingsWindow(Session currentSession, Stage window) {
        super();
        this.currentSession=currentSession;
        this.window=window;
        this.setPadding(new Insets(10, 50, 50, 50));
        this.setSpacing(spacing);

        HBox nameUpdate = changeName();
        //HBox heightUpdate = changeHeight();
        VBox passwordUpdate = changePassword();
        HBox configureSettings = configureSettings();

        this.getChildren().addAll(nameUpdate, passwordUpdate, configureSettings);   //heightUpdate,
    }

    /**
     * A method to change color scheme of the application.
     * @param color A String that has to be either "green" or "gold".
     * @since 1.5
     */
    private void colorChange(String color){
        try {
            Configuration.updateElementValue("color",color);
        } catch (TransformerException ex) {
            ex.printStackTrace();
        } catch (NullPointerException e){
            AlertBox.display("Fel", "Konfigureringsfil saknas");
            return;
        }
        try {
            Configuration.parseConfig();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        }
        MainStage mainStage = new MainStage(currentSession,window);
        mainStage.updateGUI();
    }

    /**
     * Method that shows options to the user to let the user change colour scheme.
     * @return HBox containing green and gold button that can be clicked to change colour.
     * @since 1.5
     */
    private HBox configureSettings(){
        HBox configureSettingsBox = new HBox();
        configureSettingsBox.setSpacing(spacing);
        configureSettingsBox.setPadding(boxPadding);
        configureSettingsBox.setBackground(Background.EMPTY);
        configureSettingsBox.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

        Label colorLbl = new Label("Color Scheme:");
        colorLbl.setMinWidth(minWidth);

        Button changeColorGold = new Button();
        changeColorGold.getStyleClass().add("custom-profile-edit-button-gold");
        changeColorGold.setOnMouseClicked(e->{

            colorChange("gold");

        });

        Button changeColorGreen = new Button();
        changeColorGreen.getStyleClass().add("custom-profile-edit-button-green");
        changeColorGreen.setOnMouseClicked(e->{
            colorChange("green");
        });

        configureSettingsBox.getChildren().addAll(colorLbl, changeColorGold, changeColorGreen);

        return configureSettingsBox;
    }

    /*
    private HBox changeHeight() {
        HBox changeHeightBox = new HBox();
        changeHeightBox.setSpacing(spacing);
        changeHeightBox.setPadding(boxPadding);
        changeHeightBox.setBackground(Background.EMPTY);
        changeHeightBox.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

        String userHeight = (currentSession.getCurrentUser().get("Height")).toString();

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
            Main.userConn.updateIntValue("Username", currentSession.getCurrentUser().get("Username").toString(), "Height", updateValue);
            height.clear();
            height.setPromptText(newHeight + "(cm)");
            currentSession.updateCurrentUser(currentSession.getCurrentUserName());
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
    */

    /**
     * The method that creates the change name HBox, containing a label to tell the user what to do here, a text field
     * for user input and an update button to update with what is in the text field.
     * @return HBox containing the functionality to change the name.
     * @since 1.1
     */
    private HBox changeName(){
        HBox changeNameBox = new HBox();
        changeNameBox.setSpacing(spacing);
        changeNameBox.setPadding(boxPadding);
        changeNameBox.setBackground(Background.EMPTY);
        changeNameBox.getStyleClass().add("custom-profile-edit-boxStyle-"+Configuration.color);

        String usersName= (currentSession.getCurrentUser().get("Name").toString());

        Label nameLbl = new Label("Name:");
        nameLbl.setMinWidth(minWidth);

        TextField name = new TextField();
        name.setPromptText(usersName);

        Button changeNameBtn= new Button();
        changeNameBtn.setText("Update");
        changeNameBtn.getStyleClass().add("custom-profile-edit-button-"+Configuration.color);


        changeNameBtn.setOnMouseClicked(e->{
            String updateValue = name.getText();
            Main.userConn.updateValue("Username",currentSession.getCurrentUser().get("Username").toString(),"Name",updateValue);
            name.clear();
            name.setPromptText(updateValue);
            currentSession.updateCurrentUser(currentSession.getCurrentUserName());
        });

        changeNameBox.getChildren().addAll(nameLbl,name,changeNameBtn);

        return changeNameBox;
    }

    /**
     * The method that creates the change password VBox, containing a label to tell the user what to do here, a text field
     * for user input to enter current password, two text fields for the new password and a button that will only update
     * the password if the current password is correct and the two new passwords are matching.
     * @return VBox containing the functionality to change the name.
     * @since 1.3
     */
        private VBox changePassword() {
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
                int userHashedPassword = currentSession.getCurrentUser().getInteger("Password");
                int newPasswordHash = newPasswordField.getText().hashCode();
                int matchPasswordHash = matchPasswordField.getText().hashCode();

                if (userHashedPassword == passwordInputHashed && newPasswordHash==matchPasswordHash) {
                    errorWrongPassword.setText("");
                    Main.userConn.updateIntValue("Username", currentSession.getCurrentUser().get("Username").toString(),"Password",newPasswordHash);
                    currentPasswordField.clear();
                    newPasswordField.clear();
                    matchPasswordField.clear();
                    matchPasswordField.setPromptText("Password changed");
                    System.out.println("Password changed");
                    currentSession.updateCurrentUser(currentSession.getCurrentUserName());

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