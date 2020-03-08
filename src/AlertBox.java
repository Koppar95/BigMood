import insidefx.undecorator.Undecorator;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import java.awt.*;

/**
 * AlertBox to inform user, requires an action to be closed.
 * @author Karl Svensson
 * @version 1.2
 */
public class AlertBox {
    /**
     * Creates and displays the alertBox to the user.
     * @param title The window title for the AlertBox
     * @param message The message to be displayed in the AlertBoc
     * @since 1.2
     */
    public static void display(String title, String message){
        Toolkit.getDefaultToolkit().beep();
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button approveButton = new Button("OK");
        approveButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, approveButton);
        layout.setAlignment(Pos.CENTER);

        Undecorator undecorator = new Undecorator(window,layout);
        undecorator.getStylesheets().add("bmSkinTransparent-"+Configuration.color+".css");
        undecorator.setMinSize(300,150);

        Scene scene = new Scene(undecorator);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        window.showAndWait();
    }
}
