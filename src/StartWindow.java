import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import org.bson.io.BsonOutput;

public class StartWindow extends VBox {
    private StartWindow(){}

    public static StartWindow makeStartWindow(){
        StartWindow start= new StartWindow();
        String family = "Helvetica";
        double size = 40;
        //
        Button userCheck = new Button("Which user is active????");
        userCheck.setOnAction(e->System.out.println(LoginBox.currentUser.get("Email")));
        //
        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(40);
        textFlow.setLayoutY(40);
        Text text1 = new Text("Welcome ");
        text1.setFont(Font.font(family, size));
        text1.setFill(Color.DARKGREEN);
        text1.setStroke(Color.rgb(0,0,0,0.2));
        textFlow.getChildren().addAll(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        textFlow.setTextAlignment(TextAlignment.CENTER);
        start.getChildren().addAll(textFlow, userCheck);
        start.setAlignment(Pos.CENTER);

        return start;
    }
}
