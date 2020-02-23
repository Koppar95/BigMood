
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class Chart {

    public static PieChart makeMoodPieChart() {
        MongoDB conn = new MongoDB("UsersDB", "MoodData");

        long happySubmissions = conn.countMood("Happy");
        long sadSubmissions = conn.countMood("Sad");

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Happy", happySubmissions),
                        new PieChart.Data("Sad", sadSubmissions));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("User Moods:");

        return chart;
    }
}

