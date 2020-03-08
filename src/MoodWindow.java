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
import java.util.Calendar;
import java.util.Date;

/**
 * This class displays the mood window which handles mood submissions.
 *
 * @author Teo Becerra
 * @version 1.3
 */

public class MoodWindow extends VBox {
    /**
     * Session containing current user.
     */
    private Session currentSession;

    /**
     * Creates and displays the mood window to the user.
     * @param currentSession A session that represents the current user
     * @since 1.1
     */

    public MoodWindow(Session currentSession){
        super();
        this.currentSession=currentSession;
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

    /*
     * These functions should probably belong to their own Class (suggestion DateandTime). They handle date and time
     * for example to restrict the user to post once a day and to display the last seven days mood in the start window.
     * At this current point (day of deadline) there's has not yet been time to fix this.
     */

    /**
     * Method to get current date and time.
     * @return Current date as a String
     * @since 1.2
     */
        public static String getCurrentDate() {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            return dateFormat.format(date);
        }

    /**
     * Method to get last seven days.
     * @return A list of Dates.
     * @since 1.2
     */
    public static Date[] getLastSevenDays() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -7);
        Date[] dates = new Date[7];

            for(int i = 0; i< 7; i++){
                c.add(Calendar.DAY_OF_YEAR, 1);
                dates[i] = c.getTime();
            }

        return dates;
        }

    /**
     * This function allows the user to submit their mood to the datebase and such is a part of the backbone for this app.
     * Without any submissions there would be no data to analyze. The function uses the Emojis to see which state is active
     * and gets the comment from the input box.
     * @param sad The sad Emoji, used to log state in database.
     * @param happy The happy Emoji, used to log state in database.
     * @param userinput The comment to be stored in the database.
     * @since 1.3
     */

    private void submitMood(Emoji sad, Emoji happy, TextField userinput) {

        boolean submittedToday = Main.moodConn.submittedToday(currentSession.getCurrentUser().get("Username").toString(), getCurrentDate());

        if (submittedToday) {
            AlertBox.display("Nope!", "You've already submitted a mood today!");
        } else {
            Document moodSubmission = new Document();
            moodSubmission.put("User", currentSession.getCurrentUser().get("Username"));

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

}
