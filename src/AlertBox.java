import insidefx.undecorator.Undecorator;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.initStyle(StageStyle.TRANSPARENT);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button approveButton = new Button("OK");
        //Button cancelButton = new Button("Cancel");
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
