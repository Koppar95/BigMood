import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

import java.util.*;
import java.util.stream.Collectors;

public class DataWindow extends VBox {

    public static String[] topHappyWords = new String[3];
    public static String[] topSadWords = new String[3];

    public static void countComments(HashMap<String, Integer> hash, String[] array){
        for(int i=0; i < array.length; i++){
            if(hash.containsKey(array[i])){
                hash.put(array[i], 1 + hash.get(array[i]));
            } else{
                hash.put(array[i], 1);
            }
        }
    }

    //Redo function?
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap)
    {

        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                    return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public static void printMap(Map<String, Integer> map)
    {
        for (Map.Entry<String, Integer> entry : map.entrySet())
        {
            System.out.println("Word : " + entry.getKey() + " # of submissions : "+ entry.getValue());
        }
    }

    public static Map<String, Integer> filterMoodWords(Map<String, Integer> map){

        String[] filterArr = {"testing", "with", "TESTAR", "I","is", "a", "är", "i","Det"};
        List <String> filterList = Arrays.asList(filterArr);

        map = map.entrySet().stream().filter(x-> !(filterList.contains(x.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

    public static void getMoodWords(){
        Main.moodConn.getComments("Happy");
        Main.moodConn.getComments("Sad");

        String[] sadCommentsArr = new String[MongoDB.sadComments.size()];
        MongoDB.sadComments.toArray(sadCommentsArr);

        String[] happyCommentsArr = new String[MongoDB.happyComments.size()];
        MongoDB.happyComments.toArray(happyCommentsArr);

        List<String> happyWords= new ArrayList<String>();
        List<String> sadWords = new ArrayList<String>();

        for(int i = 0; i< sadCommentsArr.length;i++) {
            String[] words = sadCommentsArr[i].split(" ");
            for(int j = 0; j < words.length; j++){
                sadWords.add(words[j]);
            }
        }

        for (int i = 0; i < happyCommentsArr.length; i++){
            String[] words = happyCommentsArr[i].split(" ");
            for(int j = 0; j < words.length; j++){
                happyWords.add(words[j]);
            }
        }

        String[] happyWordsArr = new String[happyWords.size()];
        happyWords.toArray(happyWordsArr);

        String[] sadWordsArr = new String[sadWords.size()];
        sadWords.toArray(sadWordsArr);

        HashMap happyHash = new HashMap();
        HashMap sadHash = new HashMap();

        countComments(happyHash,happyWordsArr);
        countComments(sadHash, sadWordsArr);

        Map<String, Integer> sortedHappy = filterMoodWords(happyHash);
        sortedHappy = sortByComparator(sortedHappy);

        System.out.println("Words associated with happiness: ");
        printMap(sortedHappy);

        Map<String, Integer> sortedSad = filterMoodWords(sadHash);
        sortedSad = sortByComparator(sortedSad);

        System.out.println("Words associated with sadness: ");
        printMap(sortedSad);
    }

    public DataWindow() {
        String username = LoginBox.currentUser.get("Username").toString();

        BarChart userVsAvgMood = Chart.makeMoodBarChart("You vs Avg User", username);

        /*
        String[] userArr = Main.moodConn.getUsers();

        for(int i = 0; i < userArr.length; i++){
            System.out.println("User: " + userArr[i]);
        }

        TextFlow userCount = new TextFlow();

         */

        HBox userVsAvgBox = new HBox();
        userVsAvgBox.getChildren().add(userVsAvgMood);
        userVsAvgBox.setAlignment(Pos.CENTER);

        PieChart userAvg = Chart.makeMoodPieChart("Your Total Submissions", username);
        userAvg.setLabelsVisible(false);

        getMoodWords();

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