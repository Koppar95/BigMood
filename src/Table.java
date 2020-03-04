import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Table extends VBox{

    HashMap users = new HashMap();

    public static VBox makeMoodWordTable(String mood, Map<String, Integer> moodWords){

        Label label = new Label(mood +" Words");
        label.setFont(new Font("Arial", 20));

        TableView table = new TableView();

        TableColumn<String, MoodWord> wordCol = new TableColumn<>(mood + " Words");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));

        TableColumn<String, MoodWord> subCol = new TableColumn<>("Submissions");
        subCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        table.getColumns().add(wordCol);
        table.getColumns().add(subCol);

        int wordCount = 0;
        Iterator words = moodWords.entrySet().iterator();
        while(words.hasNext() & wordCount < 3){
            Map.Entry mapElement = (Map.Entry)words.next();
            table.getItems().add(new MoodWord(mapElement.getKey().toString(), (Integer) mapElement.getValue()));
            wordCount++;
        }

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        return vbox;

    }
}
