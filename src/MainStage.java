import insidefx.undecorator.Undecorator;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainStage {
    private Undecorator undecorator;
    private Stage window;
    private Scene scene;
    private Session currentSession;

    public MainStage(Session currentSession,Stage window){
        this.currentSession=currentSession;
        this.window= window;
    }

    public void init(){
        window.setTitle("Big Mood");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setMinWidth(600);
        window.setMinHeight(600);

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

    public void updateGUI(){
        BorderPane mainLayout = new BorderPane();
        ProfileWindow profileWindow = new ProfileWindow(currentSession,window);
        mainLayout.setCenter(profileWindow);
        undecorator = new Undecorator(window, mainLayout);
        undecorator.getStylesheets().add("bmSkin-"+Configuration.color+".css");
        scene = new Scene(undecorator, Configuration.width,Configuration.height);
        scene.setFill(Color.TRANSPARENT);
        window.setScene(scene);
        Menu menu = new Menu(window,mainLayout,currentSession);
        menu.init();
    }
}
