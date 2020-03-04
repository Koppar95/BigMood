import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.Map;

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

        System.out.println("Happy words:");
        MoodHashMap.printMap(happyWords);
        System.out.println("Sad words");
        MoodHashMap.printMap(sadWords);

        VBox table = Table.makeUserTable();

        HBox userAvgBox = new HBox();
        userAvgBox.getChildren().addAll(table, userAvg);
        userAvgBox.setAlignment(Pos.CENTER);

        VBox moodCharts = new VBox();
        moodCharts.getChildren().addAll(userVsAvgBox, userAvgBox);
        moodCharts.setAlignment(Pos.CENTER);

        this.getChildren().add(moodCharts);


    }

}

/*
class Moods {
    private hashmap;
    constuctor(haschmap) {
        sthis.hashmap = hashmap;
    }
    public sort() {
        // cpoty

    }
    pub


}

 */