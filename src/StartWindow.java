import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.chart.*;


public class StartWindow extends VBox {
    private StartWindow(){}

    public static StartWindow makeStartWindow(){
        StartWindow start= new StartWindow();
        String family = "Helvetica";
        double size = 40;

        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(40);
        textFlow.setLayoutY(40);
        Text text1 = new Text("Welcome " + LoginBox.currentUser.get("Name").toString() + "!");
        text1.setFont(Font.font(family, size));
        text1.setFill(Color.DARKGREEN);
        text1.setStroke(Color.rgb(0,0,0,0.2));
        textFlow.getChildren().addAll(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        HBox textBox = new HBox();
        textBox.getChildren().add(textFlow);
        textBox.setAlignment(Pos.TOP_CENTER);

        PieChart chart = Chart.makeMoodPieChart();

        start.getChildren().addAll(textBox, chart);

        return start;
    }
}
