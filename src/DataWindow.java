import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Map;

/**
 * Presents interesting data about the user and the userbase. Two pie charts informs the user about their amount of happy / sad
 * submissions, a sort of general mood analysis. The same date is collected for the average user for comparison. A bar chart shows
 * the amount of user submissions and compares it with the amount for the average user. In my humble opinion, the most important
 * and coolest function of the entire program are the happy and sad word charts. They display which words are most commonly associated
 * with happy and sad submissions, a brief little step into data science!
 * @author Teo
 * @version 1.9
 */

public class DataWindow extends VBox {
private Session currentSession;

    /**
     * Creates and displays the data window to the user.
     * @param currentSession A session that represents the current user
     * @since 1.9
     */

    public DataWindow(Session currentSession) {
        this.currentSession=currentSession;
        String username = currentSession.getCurrentUserName();

        BarChart userVsAvgMood = Chart.makeActivityChart("Activity", username);

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