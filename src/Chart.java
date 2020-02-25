
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;


public class Chart {

    public static PieChart makeMoodPieChart(String title, String user) {
        long happySubmissions;
        long sadSubmissions;
        if(user == "") {
            happySubmissions = Main.moodConn.countMood("Happy");
            sadSubmissions = Main.moodConn.countMood("Sad");
        } else {
            happySubmissions = Main.moodConn.countUserMood("Happy", user);
            sadSubmissions = Main.moodConn.countUserMood("Sad", user);
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Happy", happySubmissions),
                        new PieChart.Data("Sad", sadSubmissions));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(title);
        return chart;
    }
}

