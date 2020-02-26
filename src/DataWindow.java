import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class DataWindow extends VBox {

    public static DataWindow makeDataWindow() {
       DataWindow mainData = new DataWindow();

        //Bar Chart test
        BarChart userVsAvgMood = Chart.makeMoodBarChart("You vs Avg User", LoginBox.currentUser.get("Username").toString());

        HBox moodCharts = new HBox();
        moodCharts.getChildren().add(userVsAvgMood);
        moodCharts.setAlignment(Pos.CENTER);

        mainData.getChildren().add(moodCharts);

        return mainData;

    }

}
