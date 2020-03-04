public class LoadAnimation implements Runnable {

    /**
     * Load Animation implements runnable and when it runs it initiates a loadwindow and shows it.
     * @author Samuel Leckborn
     * @version 1.0
     * @since 2020-03-03
     */
    public LoadWindow loadWindow;
    public LoadAnimation(LoadWindow loadWindow) {
        this.loadWindow=loadWindow;
    }

    @Override
    public void run() {
        loadWindow.init();
        loadWindow.show();
    }
}
