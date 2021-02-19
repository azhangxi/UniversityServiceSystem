package ui.window.panel.common;

import ui.Main;
import ui.window.panel.admin.AdminPanel;
import ui.window.panel.instructor.InstructorPanel;
import ui.window.panel.student.StudentPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;
import static ui.window.panel.common.Panels.*;

public class MainPanel extends JPanel {
    private Image image;
//    private JPanel mainPanel;
    private JLabel author;

    public MainPanel() {
        new JPanel();
        setLayout(null);
//        mainPanel.setBackground(Color.white);
        image = new ImageIcon(Main.path + "data/image/UBC.png").getImage();
        initializeButtons();
        initLabel();
        add(author);
        setVisible(true);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0,0, null);
    }

    private void initLabel() {
        author = new JLabel("@Author: Xi Zhang");
        author.setForeground(Color.GREEN);
        author.setSize(200, 40);
        author.setLocation(340, 540);
    }

    private void initializeButtons() {
        initializeButton("Student", LEFT_BUTTON_X);
        initializeButton("Instructor", MIDDLE_BUTTON_X);
        initializeButton("Administrator", RIGHT_BUTTON_X);
    }

    private void initializeButton(String name, int x) {
        JButton button = new JButton(name);
        button.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.setLocation(x, Panels.MIDDLE_BUTTON_Y);
        button.addActionListener(new InitializeActionListener());
        add(button);
    }

    class InitializeActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "Student":
                    setVisible(false);
                    StudentPanel studentPanel = new StudentPanel(MainPanel.this);
                    Main.MAIN_WINDOW.getMainWindow().setContentPane(studentPanel.getStudentPanel());
                    break;
                case "Instructor":
                    setVisible(false);
                    InstructorPanel instructorPanel = new InstructorPanel(MainPanel.this);
                    Main.MAIN_WINDOW.getMainWindow().setContentPane(instructorPanel.getInstructorPanel());
                    break;
                default:
                    setVisible(false);
                    AdminPanel adminPanel = new AdminPanel(MainPanel.this);
                    Main.MAIN_WINDOW.getMainWindow().setContentPane(adminPanel.getAdminPanel());
                    break;
            }

        }
    }
}
