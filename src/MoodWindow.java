import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import org.bson.Document;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MoodWindow extends VBox {

        //Skapa en egen date klass??
        // Test test

        public static String getCurrentDate() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            return dateFormat.format(date);
        }


        public static String getLastSevenDays(){

            return "hej";
        }


    private static void submitMood(Emoji sad, Emoji happy, TextField userinput) {

        boolean submittedtoday = Main.moodConn.submittedToday(LoginBox.currentUser.get("Username").toString(), getCurrentDate());

        if (submittedtoday) {
            AlertBox.display("Nope!", "You've already submitted a mood today!");
        } else {
            Document moodSubmission = new Document();
            String currentUser = LoginBox.currentUser.get("Username").toString();
            String currentDate = getCurrentDate();
            moodSubmission.put("User", LoginBox.currentUser.get("Username"));

            if (happy.isGlowing) {
                moodSubmission.put("Mood", "Happy");
                happy.disableGlow();
            } else if (sad.isGlowing) {
                moodSubmission.put("Mood", "Sad");
                sad.disableGlow();
            }

            moodSubmission.put("Comment", userinput.getText());
            moodSubmission.put("Date", getCurrentDate());

            userinput.clear();
            Main.moodConn.addDoc(moodSubmission);
        }
    }

public MoodWindow(){
    super();
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
    userComment.setPromptText("Add a comment!");
    Button submitMood = new Button("Submit Mood");
    submitMood.setOnAction(e -> submitMood(sadEmoji, happyEmoji, userComment));

    /* Fixa till egen metod och metodanropp?
    if (submittedToday()){
        submitMood.getStyleClass().add("already-submitted-button");
        System.out.println("User already submitted mood today!");
    } else {}
    */

    //Boxes for smileys and comments
    HBox comments = new HBox();
    comments.getChildren().addAll(userComment);
    comments.setAlignment(Pos.CENTER);

    happyEmoji.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> sadEmoji.disableGlow());
    sadEmoji.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> happyEmoji.disableGlow());

    HBox emojis = new HBox();
    emojis.getChildren().addAll(happyEmoji,sadEmoji);
    emojis.setAlignment(Pos.CENTER);

    this.getChildren().addAll(textFlow, emojis, comments, submitMood);
    this.setAlignment(Pos.CENTER);

}

}
