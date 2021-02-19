package ui.window;

import ui.window.panel.common.MainPanel;

import javax.swing.*;
import java.awt.*;

import static ui.Main.path;


public class MainWindow extends JFrame {
    private JFrame mainWindow;
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 40;
    private Image icon;

    public MainWindow() {
        mainWindow = new JFrame("University Service System");
        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        mainWindow.setLocationRelativeTo(null);
        initIcon();
        mainWindow.setIconImage(icon);
        mainWindow.setContentPane(new MainPanel());
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setVisible(true);
        mainWindow.setResizable(false);
    }


//    public void initializeWindow() {
//        mainWindow = new JFrame("University Service System");
//        mainWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        mainWindow.setLocationRelativeTo(null);
//        initIcon();
//        mainWindow.setIconImage(icon);
//        mainWindow.setContentPane(new MainPanel().getMainPanel());
//        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mainWindow.setVisible(true);
//        mainWindow.setResizable(false);
//    }

    private void initIcon() {
        Toolkit tool = mainWindow.getToolkit();
        icon = tool.getImage(path + "data/image/Xi.png");
    }


    public JFrame getMainWindow() {
        return mainWindow;
    }

}
