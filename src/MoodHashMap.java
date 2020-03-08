import com.mongodb.Block;
import org.bson.Document;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for creating a HashMap of mood words.
 * Collects all comments submitted for a certain mood, "Happy" for example and splits comments so that each
 * word is an element in an array. It then maps them to a hash map and counts most frequent comments. It then sorts
 * the comments and filter out irrelevant words like "happy, sad, etc". After that it sorts the filtered words by
 * most frequent and returns the mood words
 * @author Teo Becerra
 * @version 1.4
 */

public class MoodHashMap {


    /**
     * ArrayList with happy comments.
     */
    static List<String> happyComments = new ArrayList<String>();
    /**
     * ArrayList with sad comments.
     */
    static List<String> sadComments = new ArrayList<String>();

    /**
     * Block-function toHappyCommentArray is used with MongoDB.getComments to get comments and sort them by mood.
     * @since 1.1
     */
    static  Block<Document> toHappyCommentArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            happyComments.add(document.get("Comment").toString());
        }
    };
    /**
     * Block-function toSadCommentArray is used with MongoDB.getComments to get comments and sort them by mood.
     * @since 1.1
     */
    static  Block<Document> toSadCommentArray = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            sadComments.add(document.get("Comment").toString());
        }
    };

    /**
     * Maps mood words from array into hash map and counts frequency
     * @param hash The hash map where the mood words are mapped into.
     * @param array The array from where the mood words are stored
     * @since 1.1
     */
    public static void mapWords(HashMap<String, Integer> hash, String[] array){
        for(int i=0; i < array.length; i++){
            if(hash.containsKey(array[i])){
                hash.put(array[i].toLowerCase(), 1 + hash.get(array[i]));
            } else{
                hash.put(array[i], 1);
            }
        }
    }

    /**
     * This function sorts the key-value pairs in hash map with a linked list. It compares the values of each mapping.
     * @param unsortMap The original hash map from where the values will be sorted
     * @since 1.2
     */
    private static Map<String, Integer> sortHashMap(Map<String, Integer> unsortMap)
    {
        List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    /**
     * A simple filter for filtering out irrelevant words. We've updated this continuously while discovering new irrelevant words.
     * @param map The hash map to filter words from
     * @since 1.3
     */
    public static Map<String, Integer> filterMoodWords(Map<String, Integer> map){

        String[] filterArr = {"testing", "with", "testar", "I","is", "a", "är", "i","det", "happy", "sad", "to", "because", "get"};
        List <String> filterList = Arrays.asList(filterArr);

        map = map.entrySet().stream().filter(x-> !(filterList.contains(x.getKey()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return map;
    }

    /**
     * The crown jewel of MoodHashMap, this function collects comments, splits them into words, filters, sorts
     * and returns all words associated with a mood-submission.
     * @param mood The mood from which to get comments from
     * @since 1.4
     */
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

        mapWords(moodHash,wordsArr);

        Map<String, Integer> sortedMoods = filterMoodWords(moodHash);
        sortedMoods= sortHashMap(sortedMoods);

        return sortedMoods;
    }

    public MoodHashMap(){

    }
}
