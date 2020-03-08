import insidefx.undecorator.Undecorator;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Initializes the mainframe window of the application.
 * @author Samuel Leckborn
 * @version 1.0
 * @since 2020-03-04
 */
public class MainStage {
    private Undecorator undecorator;
    private Stage window;
    private Scene scene;
    private Session currentSession;

    /**
     *
     * @param currentSession Holds information of the current session.
     * @param window The JavaFx Stage to build on.
     */
    public MainStage(Session currentSession,Stage window){
        this.currentSession=currentSession;
        this.window= window;
    }

    /**
     * Initializes and show the window.
     */
    public void init(){
        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setMinWidth(600);
        window.setMinHeight(600);
        window.setResizable(false);
        BorderPane mainLayout = new BorderPane();
        StartWindow startWindow = new StartWindow(currentSession);
        startWindow.init();
        mainLayout.setCenter(startWindow);

        undecorator = new Undecorator(window,mainLayout);
        undecorator.getStylesheets().add("bmSkin-"+Configuration.color+".css");
        scene = new Scene(undecorator, Configuration.width,Configuration.height);

        scene.setFill(Color.TRANSPARENT);
        window.setOnCloseRequest(e->System.exit(0));
        window.setScene(scene);
        window.show();

        Menu menu = new Menu(window,mainLayout,currentSession);
        menu.init();
    }

    /**
     * updates the GUI of the main window.
     */
    public void updateGUI(){
        double height = window.getHeight();
        double width = window.getWidth();
        BorderPane mainLayout = new BorderPane();
        SettingsWindow settingsWindow = new SettingsWindow(currentSession,window);
        mainLayout.setCenter(settingsWindow);
        undecorator = new Undecorator(window, mainLayout);
        undecorator.setPrefSize(width,height);
        undecorator.setMaxSize(width,height);
        undecorator.getStylesheets().add("bmSkin-"+Configuration.color+".css");
        Menu menu = new Menu(window,mainLayout,currentSession);
        menu.init();

        scene = new Scene(undecorator, width,height);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
    }
}
