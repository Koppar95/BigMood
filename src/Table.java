import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.HashMap;

public class Table extends VBox{

    HashMap users = new HashMap();

    public static VBox makeUserTable(){
        //User table

        Label label = new Label("Happy Words");
        label.setFont(new Font("Arial", 20));

        TableView table = new TableView();

        TableColumn<String, MoodWord> wordCol = new TableColumn<>("Happy Words");
        wordCol.setCellValueFactory(new PropertyValueFactory<>("word"));

        TableColumn<String, MoodWord> subCol = new TableColumn<>("Submissions");
        subCol.setCellValueFactory(new PropertyValueFactory<>("count"));

        table.getColumns().add(wordCol);
        table.getColumns().add(subCol);

        /*

        items = words.getMoods("Happy", 3);
        items.each(x ->
                table.getItems().add(x);
                )

         */


        table.getItems().add(new MoodWord("Coffee", 3));
        table.getItems().add(new MoodWord("Friends", 2));
        table.getItems().add(new MoodWord("Pigg", 2));


        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        return vbox;

/*
        final Label label = new Label("Users");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn userCol = new TableColumn("Users");
        TableColumn subCol = new TableColumn("submissions");


        table.getColumns().addAll(userCol, subCol);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);


 */
    }
    public Table(String type){

        if (type.equals("user")){
            makeUserTable();
        }

    }
}
