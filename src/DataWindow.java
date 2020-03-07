import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Map;

/**
 * This is the coolest window of the app, presents very useful and cool data. Informs the user of amount of happy / sad
 * submissions of the user and compared to the average user. But most important and coolest function of the entire program
 * is the ability to do some simple basic data science and calculate which words are most frequent with a submitted mood.
 * @author Teo
 * @version 1.2
 * @since 2020-03-07
 */

public class DataWindow extends VBox {
private Session currentSession;

    public DataWindow(Session currentSession) {
        this.currentSession=currentSession;
        String username = currentSession.getCurrentUserName();

        BarChart userVsAvgMood = Chart.makeActivityChart("You vs Avg User", username);

        PieChart userAvg = Chart.makeMoodPieChart("Your Average Mood", username);
        userAvg.setLabelsVisible(false);

        PieChart totalAvg = Chart.makeMoodPieChart("Total user average","");
        userAvg.setLabelsVisible(false);

        HBox userVsAvgBox = new HBox();
        userVsAvgBox.getChildren().addAll(userAvg, totalAvg);
        userVsAvgBox.setAlignment(Pos.CENTER);


        Map<String, Integer> happyWords = MoodHashMap.getMoodWords("Happy");
        Map<String, Integer> sadWords = MoodHashMap.getMoodWords("Sad");

        VBox tables = new VBox();
        VBox happyTable = Table.makeMoodWordTable("Happy", happyWords);
        VBox sadTable = Table.makeMoodWordTable("Sad", sadWords);

        tables.getChildren().addAll(happyTable,sadTable);

        HBox tablesBox = new HBox();
        tablesBox.getChildren().addAll(tables, userVsAvgMood);
        tablesBox.setAlignment(Pos.CENTER);

        VBox moodCharts = new VBox();
        moodCharts.getChildren().addAll(userVsAvgBox, tablesBox);
        moodCharts.setAlignment(Pos.CENTER);

        this.getChildren().add(moodCharts);


    }
}