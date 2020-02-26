public class LoadAnimation implements Runnable {
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
