package ui.window.panel.instructor;

import model.Instructor;
import ui.window.panel.common.EditProfilePanel;
import ui.window.panel.common.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.Main.MAIN_WINDOW;

public class InstructorCenterPanel extends Panels {
    private JPanel instructorCenterPanel;
    protected Instructor instructor;


    public InstructorCenterPanel(JPanel sourcePanel, Instructor instructor) {
        super(sourcePanel);
        this.instructor = instructor;
        instructorCenterPanel = new JPanel() {
            @Override
            public String toString() {
                return "instructorCenterPanel";
            }
        };
        instructorCenterPanel = initializePanelSettings(instructorCenterPanel);
        initializeButtons();
        initializeLabels();
    }

    private void initializeLabels() {

        initializeWelcomeLabel(instructorCenterPanel, "Welcome Dear " + instructor.getName());
    }

    private void initializeButtons() {
        initializeBackButton(instructorCenterPanel);
        initializeButtonDefaultDimension(instructorCenterPanel, "Post Note", LEFT_BUTTON_X, MIDDLE_BUTTON_Y,
                new PostNoteAction());
        initializeButtonDefaultDimension(instructorCenterPanel,
                "Manage Teaching Courses", MIDDLE_BUTTON_X, MIDDLE_BUTTON_Y, new ManageCourses());
        initializeButtonDefaultDimension(instructorCenterPanel, "Edit Profile", RIGHT_BUTTON_X, MIDDLE_BUTTON_Y,
                e -> {
                    instructorCenterPanel.setVisible(false);
                    MAIN_WINDOW.getMainWindow().setContentPane(
                            new EditProfilePanel(instructorCenterPanel, instructor).getEditProfilePanel());
                });
    }

    //MODIFY: this
    //EFFECT: create a back button named log out on top left corner.
    @Override
    protected void initializeBackButton(JComponent thisPanel) {
        back = new JButton("Log Out");
        back.setBounds(10, 10, 80, 30);
        back.addActionListener(new BackAction(thisPanel));
        thisPanel.add(back);
    }

    class PostNoteAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            instructorCenterPanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(
                    new PostNotePanel(instructorCenterPanel, instructor).getPostNotePanel());
        }
    }

    class ManageCourses implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            instructorCenterPanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(
                    new ManageTeachingCoursesPanel(instructorCenterPanel, instructor).getManageTeachingCoursesPanel());
        }
    }

    public JPanel getInstructorCenterPanel() {
        return instructorCenterPanel;
    }
}
