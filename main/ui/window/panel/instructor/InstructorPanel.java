package ui.window.panel.instructor;

import model.UsersType;
import ui.window.panel.common.Panels;

import javax.swing.*;

public class InstructorPanel extends Panels {
    private JPanel instructorPanel;

    public InstructorPanel(JPanel sourcePanel) {
        super(sourcePanel);
        instructorPanel = new JPanel() {
            @Override
            public String toString() {
                return "instructorPanel";
            }
        };
        instructorPanel = initializePanelSettings(instructorPanel);
        initializeButtons();
        initializeWelcomeLabel(instructorPanel, "Instructor Center");
    }

    private void initializeButtons() {
        initializeBackButton(instructorPanel);
        initializeLoginButton(instructorPanel);
        initializeRegisterButton(instructorPanel, UsersType.INSTRUCTOR);
    }

    public JPanel getInstructorPanel() {
        return instructorPanel;
    }


}
