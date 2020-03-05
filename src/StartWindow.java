import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.chart.*;

/**
 *Did i create this? //Teo
 */
public class StartWindow extends VBox {
    private Session currentSession;

    public StartWindow(Session currentSession){
        this.currentSession=currentSession;
    }

    public void init(){
        String family = "Helvetica";
        double size = 40;

        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(40);
        textFlow.setLayoutY(40);
        Text text1 = new Text("Welcome " + currentSession.currentUser.get("Name").toString() + "!");
        text1.setFont(Font.font(family, size));
        text1.setFill(Color.DARKGREEN);
        text1.setStroke(Color.rgb(0,0,0,0.2));
        textFlow.getChildren().addAll(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        HBox textBox = new HBox();
        textBox.getChildren().add(textFlow);
        textBox.setAlignment(Pos.TOP_CENTER);

        LineChart weeklyAverage = Chart.makeLineChart(currentSession.currentUser.get("Username").toString());

        HBox moodCharts = new HBox();
        moodCharts.getChildren().add(weeklyAverage);
        moodCharts.setAlignment(Pos.CENTER);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(textBox);
        mainLayout.setBottom(moodCharts);

        this.getChildren().addAll(mainLayout);
    }
}
