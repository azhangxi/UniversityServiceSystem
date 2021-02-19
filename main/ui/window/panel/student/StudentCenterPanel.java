package ui.window.panel.student;

import model.Student;
import ui.window.panel.common.ModifyPswPanel;
import ui.window.panel.common.EditProfilePanel;
import ui.window.panel.common.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.Main.MAIN_WINDOW;

public class StudentCenterPanel extends Panels {
    private JPanel studentCenterPanel;
    private Student student;
    private JLabel dot;

    public StudentCenterPanel(JPanel sourcePanel, Student student) {
        super(sourcePanel);
        this.student = student;
        studentCenterPanel = new JPanel() {
            @Override
            public String toString() {
                return "studentCenterPanel";
            }
        };
        studentCenterPanel = initializePanelSettings(studentCenterPanel);
        initializeLabels();
        initializeBackButton(studentCenterPanel);
        initializeButtons();
    }

    @Override
    public void initializeBackButton(JComponent thisPanel) {
        back = new JButton("Log Out");
        back.setBounds(10, 10, 80, 30);
        back.addActionListener(new BackAction(thisPanel));
        thisPanel.add(back);
    }

    private void initializeLabels() {
        initializeWelcomeLabel(studentCenterPanel, "Welcome Dear " + student.getName());
    }

    private void initializeButtons() {
        initializeButtonDefaultDimension(studentCenterPanel, "Modify Password", LEFT_BUTTON_X, MIDDLE_BUTTON_Y, e -> {
            studentCenterPanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(
                    new ModifyPswPanel(studentCenterPanel, student).getModifyPswPanel());
        });
        initNewsButton();
        initializeButtonDefaultDimension(studentCenterPanel,
                "Add or Drop Course", MIDDLE_BUTTON_X, MIDDLE_BUTTON_Y, e -> {
                    studentCenterPanel.setVisible(false);
                    MAIN_WINDOW.getMainWindow().setContentPane(
                            new AddOrDropCoursePanel(studentCenterPanel, student).getAddOrDropCoursePanel());
                });
        initializeButtonDefaultDimension(studentCenterPanel, "Edit Profile", RIGHT_BUTTON_X, MIDDLE_BUTTON_Y, e -> {
            studentCenterPanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(
                    new EditProfilePanel(studentCenterPanel, student).getEditProfilePanel());
        });
    }

    private void initNewsButton() {
        JButton news = new JButton("News");
        news.setBounds(710, 10, 70, 70);
        news.addActionListener(new NewsAction());
        studentCenterPanel.add(news);
        dot = new JLabel(new ImageIcon("data/image/red_dot.png"));
        news.setLayout(null);
        dot.setSize(18,12);
        dot.setLocation(45, 10);
        dot.setBorder(null);
        dot.setVisible(student.hasNewNote());
        news.add(dot);
    }

    class NewsAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            student.setHasNewNote(false);
            student.updateUserToFile();
            dot.setVisible(false);
            studentCenterPanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(
                    new NewsPanel(studentCenterPanel, student).getNewsPanel());
        }
    }

    public JPanel getStudentCenterPanel() {
        return studentCenterPanel;
    }

}
