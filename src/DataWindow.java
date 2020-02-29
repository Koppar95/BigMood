import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import java.util.*;

public class DataWindow extends VBox {

    public void addToHashMap(HashMap<String, Integer> hash, String[] array){

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

    public DataWindow() {
        String username = LoginBox.currentUser.get("Username").toString();

        BarChart userVsAvgMood = Chart.makeMoodBarChart("You vs Avg User", username);

        //Test for getting comments
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

        addToHashMap(happyHash,happyWordsArr);
        addToHashMap(sadHash, sadWordsArr);

        System.out.println("Words associated with happiness: ");
        Map<String, Integer> sortedHappy = sortByComparator(happyHash);
        printMap(sortedHappy);

        System.out.println("Words associated with sadness: ");
        Map<String, Integer> sortedSad = sortByComparator(sadHash);
        printMap(sortedSad);


        PieChart userAvg = Chart.makeMoodPieChart("Your Total Submissions", username);
        userAvg.setLabelsVisible(false);

        HBox userVsAvgBox = new HBox();
        userVsAvgBox.getChildren().add(userVsAvgMood);
        userVsAvgBox.setAlignment(Pos.CENTER);

        HBox userAvgBox = new HBox();
        userAvgBox.getChildren().add(userAvg);
        userAvgBox.setAlignment(Pos.CENTER);

        VBox moodCharts = new VBox();
        moodCharts.getChildren().addAll(userVsAvgBox, userAvgBox);
        moodCharts.setAlignment(Pos.CENTER);

        this.getChildren().add(moodCharts);


    }

}
