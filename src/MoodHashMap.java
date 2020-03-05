import com.mongodb.Block;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

public class MoodHashMap {

    //private HashMap<String, Integer> moodHashMap;
    static List<String> happyComments = new ArrayList<String>();
    static List<String> sadComments = new ArrayList<String>();

    static  Block<Document> toHappyCommentArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            happyComments.add(document.get("Comment").toString());
        }
    };

    static  Block<Document> toSadCommentArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            sadComments.add(document.get("Comment").toString());
        }
    };

    public static void countComments(HashMap<String, Integer> hash, String[] array){
        for(int i=0; i < array.length; i++){
            if(hash.containsKey(array[i])){
                hash.put(array[i].toLowerCase(), 1 + hash.get(array[i]));
            } else{
                hash.put(array[i], 1);
            }
        }
    }

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

        String[] filterArr = {"testing", "with", "testar", "I","is", "a", "är", "i","det", "happy", "sad"};
        List <String> filterList = Arrays.asList(filterArr);

        map = map.entrySet().stream().filter(x-> !(filterList.contains(x.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

    public static Map<String, Integer> getMoodWords(String mood){
        Main.moodConn.getComments(mood);

        List<String> moodWords= new ArrayList<String>();

        if(mood.equals("Happy")){
            String[] commentsArr = new String[happyComments.size()];
            happyComments.toArray(commentsArr);

            for(int i = 0; i< commentsArr.length;i++) {
                String[] words = commentsArr[i].split(" ");
                for(int j = 0; j < words.length; j++){
                    moodWords.add(words[j]);
                }
            }
        } else{
            String[] commentsArr = new String[sadComments.size()];
            sadComments.toArray(commentsArr);

            for(int i = 0; i< commentsArr.length;i++) {
                String[] words = commentsArr[i].split(" ");
                for(int j = 0; j < words.length; j++){
                    moodWords.add(words[j]);
                }
            }
        }

        String[] wordsArr = new String[moodWords.size()];
        moodWords.toArray(wordsArr);

        HashMap moodHash = new HashMap();

        countComments(moodHash,wordsArr);

        Map<String, Integer> sortedMoods = filterMoodWords(moodHash);
        sortedMoods= sortByComparator(sortedMoods);

        return sortedMoods;
    }

    public MoodHashMap(){

    }
}
