import javafx.application.Platform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class LoadWindow {
    private JFrame window;
    private double xPos;
    private double yPos;

    public LoadWindow(double xPos, double yPos){
        window = new JFrame();
        this.xPos = xPos;
        this.yPos = yPos;

    }

    public void init(){
        window.setTitle("Ladd");
        window.setUndecorated(true);
        window.setBackground(new Color(0,0,0,0));
        window.setLocation((int)xPos,(int)yPos);
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(30,30);
        ImageIcon loadIcon = new ImageIcon("src/Assets/89.gif");
        JLabel image = new JLabel(loadIcon);
        window.add(image);
    }

    public void startLoadThread(){
        Runnable myRunnable = new LoadAnimation(this);
        Thread thread = new Thread(myRunnable);
        thread.start();
    }

    public void show(){
        window.setVisible(true);
    }
    public void close(){
        //window.dispose();
        window.dispatchEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSING));
    }
}
