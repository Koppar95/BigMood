import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class creates and adds functionality to the emojis we  use to submit moods to the database. We added some basic
 * effects to enhance the experience and add a new level of immersion.
 * @author Teo
 * @version 1.1
 * @since 2020-03-05
 */

public class Emoji extends ImageView {
    public boolean isGlowing;

    private Emoji (Image image) {
        super(image);
        isGlowing = false;

        DropShadow shadow = new DropShadow();
        Glow glow = new Glow();

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

        super.setOnMouseClicked(e -> {
            isGlowing = !isGlowing;

            if (isGlowing) {
                setEffect(glow);
            } else {
                setEffect(null);
            }

        });
    }

    public void disableGlow() {
        setEffect(null);
        isGlowing = false;
    }

    public static Emoji makeHappyEmoji() {
        Image happy = new Image("Assets/happy.png");
        return new Emoji(happy);
    }

    public static Emoji makeSadEmoji() {
        Image sad = new Image("Assets/sad.png");
        return new Emoji(sad);
    }

}



