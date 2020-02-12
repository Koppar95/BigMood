import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MoodWindow extends VBox {

    //Standar constuctor
    private MoodWindow(){
    }

        public static String getCurrentTime() {


            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            //get current date time with Date()
            Date date = new Date();
            return dateFormat.format(date);

        }

public static MoodWindow makeMoodWindow(){

        MoodWindow mainMood = new MoodWindow();
    //Big Mood Headline (shorten and fix with CSS)
    String family = "Helvetica";
    double size = 40;

    TextFlow textFlow = new TextFlow();
    textFlow.setLayoutX(40);
    textFlow.setLayoutY(40);
    Text text1 = new Text("How's ");
    text1.setFont(Font.font(family, size));
    text1.setFill(Color.RED);
    text1.setStroke(Color.rgb(0,0,0,0.2));
    Text text2 = new Text("Your");
    text2.setFill(Color.ORANGE);
    text2.setStroke(Color.rgb(0,0,0,0.2));
    text2.setFont(Font.font(family, FontPosture.ITALIC, size));
    Text text3 = new Text(" Mood?");
    text3.setFill(Color.GREEN);
    text3.setStroke(Color.rgb(0,0,0,0.2));
    text3.setFont(Font.font(family, FontWeight.BOLD, size));
    textFlow.getChildren().addAll(text1, text2, text3);
    textFlow.setTextAlignment(TextAlignment.CENTER);

    Emoji happyEmoji = Emoji.makeHappyEmoji();
    Emoji sadEmoji = Emoji.makeSadEmoji();

    //User comments
    TextField userComment = new TextField();
    Button submitMood = new Button("Submit Mood");
    submitMood.setOnAction(e -> {
        System.out.println("");
        if(happyEmoji.isGlowing){
            System.out.print("Mood: happy, ");
        }else if (sadEmoji.isGlowing){
                System.out.print("Mood: sad, ");
        }

        System.out.print("Comment: " + userComment.getText() + ", ");
        System.out.print("Date & Time: " + getCurrentTime());
    });

    //Boxes for smileys and comments
    HBox comments = new HBox();
    comments.getChildren().addAll(userComment);
    comments.setAlignment(Pos.CENTER);

    happyEmoji.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> sadEmoji.disableGlow());
    sadEmoji.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> happyEmoji.disableGlow());

    HBox emojis = new HBox();
    emojis.getChildren().addAll(happyEmoji,sadEmoji);
    emojis.setAlignment(Pos.CENTER);



    mainMood.getChildren().addAll(textFlow, emojis, comments, submitMood);
    mainMood.setAlignment(Pos.CENTER);

        return mainMood;
}

}
