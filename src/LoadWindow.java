import insidefx.undecorator.Undecorator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

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
        ImageIcon loadIcon = new ImageIcon("images/89.gif");
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
        window.dispose();
    }
}
