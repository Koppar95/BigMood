import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public class ProfileWindow extends VBox {

    public void ProfileWindow() {

    }

    public static ProfileWindow makeProfileWindow() {
        ProfileWindow profile = new ProfileWindow();

        String family = "Helvetica";
        double size = 40;

        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(40);
        textFlow.setLayoutY(40);
        Text text1 = new Text("Edit profile window");
        text1.setFont(Font.font(family, size));
        text1.setFill(Color.DARKGREEN);
        text1.setStroke(Color.rgb(0, 0, 0, 0.2));
        textFlow.getChildren().addAll(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        textFlow.setTextAlignment(TextAlignment.CENTER);
        profile.getChildren().addAll(textFlow);
        profile.setAlignment(Pos.CENTER);

        return profile;

    }
}