package ui.window.panel.common;

import file.CourseFile;
import file.UserFile;
import model.Courses;
import model.Users;
import model.UsersType;
import ui.Main;
import ui.window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.EventListener;

import static model.UsersType.*;
import static ui.Main.MAIN_WINDOW;

import static ui.Main.path;
import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

public abstract class Panels extends JComponent {
    protected UserFile studentFile = new UserFile(Main.STU_PATH);
    UserFile instructorFile = new UserFile(Main.INS_PATH);
    UserFile adminFile = new UserFile(Main.ADM_PATH);
    protected CourseFile courseFile = new CourseFile(Main.CRS_PATH);
    protected static final int MIDDLE_BUTTON_Y = 260;
    protected static final int LEFT_BUTTON_X = 100;
    protected static final int MIDDLE_BUTTON_X = 300;
    protected static final int RIGHT_BUTTON_X = 500;
    protected Users students;
    protected Users instructors;
    protected Users admins;
    protected Courses courses;
    protected JButton back = new JButton(new ImageIcon(path + "data/image/back.png"));
    protected JButton login = new JButton("Login");
    JButton register = new JButton("Register");
//    protected JLabel dot = new JLabel(new ImageIcon("data/icons/red_dot.png"));
    protected JComponent sourcePanel;
    protected Dimension textDimension = new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT);

    public Panels(JComponent sourcePanel) {
        this.sourcePanel = sourcePanel;
        initUserFiles();
        initCourseFile();
    }

    protected JPanel initializeDefaultPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    protected JPanel initializePanelSettings(JPanel panel) {
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void initUserFiles() {
        if (studentFile.readUsers() != null) {
            students = studentFile.readUsers();
        } else {
            students = new Users(STUDENT);
        }
        if (instructorFile.readUsers() != null) {
            instructors = instructorFile.readUsers();
        } else {
            instructors = new Users(INSTRUCTOR);
        }
        if (adminFile.readUsers() != null) {
            admins = adminFile.readUsers();
        } else {
            admins = new Users(ADMIN);
        }
    }

    private void initCourseFile() {
        if (courseFile.readCourses() != null) {
            courses = courseFile.readCourses();
        } else {
            courses = new Courses();
        }
    }

    protected void initializeBackButton(JComponent thisPanel) {
        back.setBounds(10, 10, 30, 30);
        back.setBorder(null);
        back.addActionListener(new BackAction(thisPanel));
        System.gc();
        thisPanel.add(back);
    }

    protected void initializeLoginButton(JPanel thisPanel) {
        login.setBounds(RIGHT_BUTTON_X, MIDDLE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        login.addActionListener(new LoginAction(thisPanel));
        thisPanel.add(login);
    }

    protected void initializeRegisterButton(JPanel thisPanel, UsersType type) {
        register.setBounds(LEFT_BUTTON_X, MIDDLE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
        register.addActionListener(new RegisterAction(thisPanel, type));
        thisPanel.add(register);
    }

    protected void initializeButtonDefaultDimension(JPanel thisPanel, String name, int x, int y,
                                                    ActionListener actionListener) {
        JButton button = new JButton(name);
        button.setBounds(x, y,BUTTON_WIDTH, BUTTON_HEIGHT);
        button.addActionListener(actionListener);
        thisPanel.add(button);
    }

    protected void initializeButton(JPanel thisPanel, String name, int x, int y,
                                    int width, int height, EventListener listener) {
        JButton button = new JButton(name);
        button.setBounds(x, y, width, height);
        if (listener instanceof ActionListener) {
            ActionListener actionListener = (ActionListener) listener;
            button.addActionListener(actionListener);
        } else if (listener instanceof MouseListener) {
            MouseListener mouseListener = (MouseListener) listener;
            button.addMouseListener(mouseListener);
        }

        thisPanel.add(button);
    }

    protected void initializeLabel(JPanel thisPanel, String name, int x, int y, int width, int height) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        thisPanel.add(label);
    }

    protected void initializeWelcomeLabel(JComponent panel, String labelContent) {
        JLabel userLabel = new JLabel(labelContent, JLabel.CENTER);
        userLabel.setBounds(0, 10, MainWindow.WINDOW_WIDTH, BUTTON_HEIGHT);
        userLabel.setFont(new Font(Font.DIALOG, Font.ITALIC, 20));
        userLabel.setForeground(Color.PINK);
        panel.add(userLabel);
    }

    protected class BackAction implements ActionListener {
        private JComponent component;

        public BackAction(JComponent component) {
            this.component = component;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            component.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(sourcePanel);
            sourcePanel.setVisible(true);
        }
    }

    protected static class RegisterAction implements ActionListener {
        private JPanel panel;
        private UsersType type;

        RegisterAction(JPanel panel, UsersType type) {
            this.panel = panel;
            this.type = type;
        }


        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(new RegisterPanel(panel, type).getRegisterPanel());
        }
    }

    protected static class LoginAction implements ActionListener {
        private JPanel panel;

        LoginAction(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(new LoginPanel(panel).getLoginPanel());
        }
    }

    protected static void makeWarningDialog(String string) {
        JOptionPane pane = new JOptionPane(string);
        JDialog dialog  = pane.createDialog(MAIN_WINDOW,"Warning");
        dialog.setVisible(true);
    }

    protected static void makeSucceedDialog(String string) {
        JOptionPane pane = new JOptionPane(string);
        JDialog dialog  = pane.createDialog(MAIN_WINDOW,"Succeed");
        dialog.setVisible(true);
    }

    protected void updateUserMaps() {
        students = studentFile.readUsers();
        admins = adminFile.readUsers();
        instructors = instructorFile.readUsers();
    }

    boolean isEmptyField(JTextField text) {
        return text.getText().equals("");
    }





}
