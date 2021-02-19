package ui.window.panel.student;

import model.UsersType;
import ui.window.panel.common.Panels;

import javax.swing.*;

public class StudentPanel extends Panels {
    private JPanel studentPanel;

    public StudentPanel(JPanel sourcePanel) {
        super(sourcePanel);
        studentPanel = new JPanel() {
            @Override
            public String toString() {
                return "studentPanel";
            }
        };
        studentPanel = initializePanelSettings(studentPanel);
        initializeButtons();
        initializeWelcomeLabel(studentPanel, "Student Center");
    }

    private void initializeButtons() {
        initializeBackButton(studentPanel);
        initializeLoginButton(studentPanel);
        initializeRegisterButton(studentPanel, UsersType.STUDENT);
    }

    public JPanel getStudentPanel() {
        return studentPanel;
    }

}
