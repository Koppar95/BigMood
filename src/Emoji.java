import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class creates and adds functionality to the emojis we use for mood submissions. We added some basic
 * effects to enhance the experience and add some of immersion.
 * @author Teo
 * @version 1.1
 */

public class Emoji extends ImageView {
    /**
     * True when emoji is glowing, false when not.
     */
    public boolean isGlowing;

    /**
     * Creates an Emoji for mood submissions, adds special effects.
     * @param image The image used for the Emoji.
     * @since 1.1
     */

    private Emoji (Image image) {
        super(image);

        isGlowing = false;

        DropShadow shadow = new DropShadow();
        Glow glow = new Glow();

        /*
         * Adds a drop shadow when the mouse pointer is on the Emoji
         */

        setOnMouseEntered(e -> {
           if(isGlowing){
               return;
           }
            setEffect(shadow);
        });

        setOnMouseExited(e -> {
            setEffect(null);
            if(isGlowing) {
                setEffect(glow);
            }
        });


        /*
         * Adds a glow effect when the Emoji is clicked on to highlight the choice.
         */
        super.setOnMouseClicked(e -> {
            isGlowing = !isGlowing;

            if (isGlowing) {
                setEffect(glow);
            } else {
                setEffect(null);
            }

        });
    }

    /**
     * Method to disable the glow effect for the function.
     * @since 1.1
     */
    public void disableGlow() {
        setEffect(null);
        isGlowing = false;
    }

    /**
     * Method that instantiates a happy Emoji.
     * @return Image with happy face
     * @since 1.1
     */
    public static Emoji makeHappyEmoji() {
        Image happy = new Image("Assets/happy.png");
        return new Emoji(happy);
    }

    /**
     * Method that instantiates a sad Emoji.
     * @return Image with sad face.
     * @since 1.1
     */
    public static Emoji makeSadEmoji() {
        Image sad = new Image("Assets/sad.png");
        return new Emoji(sad);
    }

}



