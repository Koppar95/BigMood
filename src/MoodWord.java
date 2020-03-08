/**
 * This handles mood words to use for the charts in the DataWindow. To my limited knowledge JavaFX-charts needs the
 * data input to be instances of a class so we simply create an object with the mood word and count of how many times it's
 * been added with a submission.
 *
 * @author Teo Becerra
 * @version 1.1
 */

public class MoodWord {
    /**
     * A String to represent a word entered by user in combination with a mood.
     */
    private String word;
    /**
     * Integer representing the number of occurrences amongst comments of a certain mood.
     */
    private Integer count;

    /**
     *  Instantiates a word for the table of mood words.
     * @param word The word to present for a certain mood.
     * @param count The number of occurrences amongst comments of a certain mood.
     * @since 1.1
     */
    public MoodWord(String word, Integer count){
        this.word = word;
        this.count = count;
    }


    /**
     * Gets this objects word.
     * @return String that is a word.
     * @since 1.1
     */
    public String getWord() {
        return word;
    }

    /**
     * Sets this objects word.
     * @param word String to set this objects word to.
     * @since 1.1
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Gets Integer count.
     * @return Integer count to set this objects count to.
     * @since 1.1
     */
    public Integer getCount() {
        return count;
    }

    /**
     * Sets this objects count.
     * @param count The integer to set this objects count to.
     * @since 1.1
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
