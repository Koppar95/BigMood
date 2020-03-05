
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.collections.FXCollections;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.LineChart;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Used for creating charts for the DataWindow and StartWindow
 * @author Teo Becerra
 * @version 1.1
 * @since 2020-03-05
 */


public class Chart {

    /**
     * Creates a PieCart to the user total submissions. The pie chart is split
     * into Happy and Sad submissions. At first it could collect both user submissions and total submissions
     * but is now only used for collecting user submissions.
     */
    public static PieChart makeMoodPieChart(String title, String user) {
        long happySubmissions;
        long sadSubmissions;

        if(user.equals("")) {
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

    /**
     * Creates and a Line chart of the users weekly moods (current day and six days back).
     * For simplicity a happy mood = 1, sad mood = -1 and no submissions = 0.
     */
    public static LineChart makeLineChart(String user){
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat dayFormat = new SimpleDateFormat("EEE");
        yAxis.setLabel("Mood");
        Date[] dates = MoodWindow.getLastSevenDays();

        final LineChart<String,Number> lineChart =
                new LineChart<String,Number>(xAxis,yAxis);

        lineChart.setTitle("Your Weekly Mood");
        XYChart.Series series = new XYChart.Series();
        series.setName("-1=Sad 0=no submission 1=Happy");

        for (int i = 0; i < dates.length; i++){
            String date = dateFormat.format(dates[i]);
            String mood = Main.moodConn.findUserMood(user,date);
            String day = dayFormat.format(dates[i]);

            if (mood.equals("Happy")) {
                series.getData().add(new XYChart.Data(day, 1));
            } else if(mood.equals("Sad")) {
                series.getData().add(new XYChart.Data(day, -1));
            } else {
                series.getData().add(new XYChart.Data(day, 0));
            }
        }
        lineChart.getData().add(series);
        return lineChart;
    }

    /**
     * Creates and displays the a Bar chart of the user and total user base moods
     * It counts the total happy and sad submissions for the user and the total amount of
     * submissions / users.
     */
    public static BarChart makeMoodBarChart(String title, String user) {
        long happySubmissions = Main.moodConn.countMood("Happy");
        long sadSubmissions= Main.moodConn.countMood("Sad");
        long userHappySubmissions = Main.moodConn.countUserMood("Happy", user);
        long userSadSubmissions = Main.moodConn.countUserMood("Sad", user);
        long userCount = Main.moodConn.countUsers();

        float avgHappySubmissions = happySubmissions / userCount;
        float avgSadSubmissions = sadSubmissions / userCount;

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> chart =
                new BarChart<String,Number>(xAxis,yAxis);
        chart.setTitle("You vs Avg user");
        xAxis.setLabel("");
        yAxis.setLabel("Moods");

        XYChart.Series happySeries = new XYChart.Series();
        happySeries.setName("Happy");
        happySeries.getData().add(new XYChart.Data("User Avg", avgHappySubmissions));
        happySeries.getData().add(new XYChart.Data("Your Avg", userHappySubmissions));

        XYChart.Series sadSeries = new XYChart.Series();
        sadSeries.setName("Sad");
        sadSeries.getData().add(new XYChart.Data("User Avg", avgSadSubmissions));
        sadSeries.getData().add(new XYChart.Data("Your Avg", userSadSubmissions));

        chart.getData().addAll(happySeries, sadSeries);

        return chart;
    }
}

