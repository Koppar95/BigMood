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
 * @version 1.1
 * @since 2020-03-05
 */

public class DataWindow extends VBox {
private Session currentSession;

    public DataWindow(Session currentSession) {
        this.currentSession=currentSession;
        String username = currentSession.getCurrentUserName();

        BarChart userVsAvgMood = Chart.makeMoodBarChart("You vs Avg User", username);

        HBox userVsAvgBox = new HBox();
        userVsAvgBox.getChildren().add(userVsAvgMood);
        userVsAvgBox.setAlignment(Pos.CENTER);

        PieChart userAvg = Chart.makeMoodPieChart("Your Total Submissions", username);
        userAvg.setLabelsVisible(false);

        Map<String, Integer> happyWords = MoodHashMap.getMoodWords("Happy");
        Map<String, Integer> sadWords = MoodHashMap.getMoodWords("Sad");

        VBox tables = new VBox();
        VBox happyTable = Table.makeMoodWordTable("Happy", happyWords);
        VBox sadTable = Table.makeMoodWordTable("Sad", sadWords);

        /*
        System.out.println("Happy words: ");
        MoodHashMap.printMap(happyWords);
        System.out.println("Sad words: ");
        MoodHashMap.printMap(sadWords);
         */

        tables.getChildren().addAll(happyTable,sadTable);

        HBox userAvgBox = new HBox();
        userAvgBox.getChildren().addAll(tables, userAvg);
        userAvgBox.setAlignment(Pos.CENTER);

        VBox moodCharts = new VBox();
        moodCharts.getChildren().addAll(userVsAvgBox, userAvgBox);
        moodCharts.setAlignment(Pos.CENTER);

        this.getChildren().add(moodCharts);


    }
}