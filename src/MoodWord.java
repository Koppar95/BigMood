/**
 * This handles mood words to use for the charts in the DataWindow. To my limited knowledge JavaFX-charts needs the
 * data input to be instances of a class so we simply create an object with the mood word and count of how many times it's
 * been added with a submission.
 *
 * @author Teo Becerra
 * @version 1.1
 * @since 2020-02-22
 */

public class MoodWord {
    private String word;
    private Integer count;

    /**
     *  Instantiates a word for the table of mood words
     * @param word The word to present for a certain mood
     * @param count The number of occurrences amongst comments of a certain mood
     */
    public MoodWord(String word, Integer count){
        this.word = word;
        this.count = count;
    }

    /**
     *  Used for access to the private variables word and count.
     */

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
