
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class Chart {
    //static MongoDB pieConn = new MongoDB("UsersDB", "MoodData");
    public static PieChart makeMoodPieChart() {
        //MongoDB pieConn = new MongoDB("UsersDB", "MoodData");

        long happySubmissions = Main.moodConn.countMood("Happy");
        long sadSubmissions = Main.moodConn.countMood("Sad");

        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Happy", happySubmissions),
                        new PieChart.Data("Sad", sadSubmissions));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("User Moods:");

        return chart;
    }
}

