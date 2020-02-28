import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.scene.chart.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class StartWindow extends VBox {

    public StartWindow(){
        String family = "Helvetica";
        double size = 40;

        TextFlow textFlow = new TextFlow();
        textFlow.setLayoutX(40);
        textFlow.setLayoutY(40);
        Text text1 = new Text("Welcome " + LoginBox.currentUser.get("Name").toString() + "!");
        text1.setFont(Font.font(family, size));
        text1.setFill(Color.DARKGREEN);
        text1.setStroke(Color.rgb(0,0,0,0.2));
        textFlow.getChildren().addAll(text1);
        textFlow.setTextAlignment(TextAlignment.CENTER);

        HBox textBox = new HBox();
        textBox.getChildren().add(textFlow);
        textBox.setAlignment(Pos.TOP_CENTER);

        LineChart weeklyAverage = Chart.makeLineChart(LoginBox.currentUser.get("Username").toString());

    /*
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date[] dates = MoodWindow.getLastSevenDays();

        for (int i=0; i < 7; i++){
            String mood = Main.moodConn.findUserMood("teo", dateFormat.format(dates[i]));
            System.out.println("Teo is : " + mood);
        }*/

        HBox moodCharts = new HBox();
        moodCharts.getChildren().add(weeklyAverage);
        moodCharts.setAlignment(Pos.CENTER);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(textBox);
        mainLayout.setBottom(moodCharts);

        this.getChildren().addAll(mainLayout);


    }
}
