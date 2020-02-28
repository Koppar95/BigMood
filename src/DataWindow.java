import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class DataWindow extends VBox {

    public DataWindow() {
        String username = LoginBox.currentUser.get("Username").toString();
        //Bar Chart test
        BarChart userVsAvgMood = Chart.makeMoodBarChart("You vs Avg User", username);

        PieChart userAvg = Chart.makeMoodPieChart("Your average mood", username);
        userAvg.setLabelsVisible(false);

        HBox userVsAvgBox = new HBox();
        userVsAvgBox.getChildren().add(userVsAvgMood);
        userVsAvgBox.setAlignment(Pos.CENTER);

        HBox userAvgBox = new HBox();
        userAvgBox.getChildren().add(userAvg);
        userAvgBox.setAlignment(Pos.CENTER);

        VBox moodCharts = new VBox();
        moodCharts.getChildren().addAll(userVsAvgBox, userAvgBox);
        moodCharts.setAlignment(Pos.CENTER);

        this.getChildren().add(moodCharts);


    }

}
