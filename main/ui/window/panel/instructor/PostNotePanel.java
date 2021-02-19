package ui.window.panel.instructor;

import model.Course;
import model.Instructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.Main.MAIN_WINDOW;
import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

public class PostNotePanel extends InstructorCenterPanel implements ActionListener {
    private JPanel postNotePanel;
    private JTextField titleField;
    private JTextArea noteField;
    private JComboBox courseSelection;
    private String theCourseKey;

    PostNotePanel(JPanel sourcePanel, Instructor instructor) {
        super(sourcePanel, instructor);
        postNotePanel = initializeDefaultPanel();
        initializeText();
        initializeSelection();
        initializeButtons();
    }

    private void initializeText() {
        titleField = new JTextField();
        titleField.setBounds(50, 50, 700, 45);
        postNotePanel.add(titleField);
        noteField = new JTextArea();
        noteField.setBounds(50,100,700, 300);
        noteField.setLineWrap(true);
        noteField.setWrapStyleWord(true);
        noteField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        postNotePanel.add(noteField);
    }

    private void initializeButtons() {
        initializeBackButton(postNotePanel);
        initializeButtonDefaultDimension(postNotePanel, "Post", 300, 500, this);
    }

    private void initializeSelection() {
        initializeLabel(postNotePanel, "Notify Course :", 220, 450, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        courseSelection = new JComboBox<>(makeOptions());
        courseSelection.setSize(textDimension);
        courseSelection.setLocation(320, 450);
        courseSelection.setSelectedItem("-Select-");
        postNotePanel.add(courseSelection);
    }

    private String[] makeOptions() {
        String[] courses = new String[instructor.getCoursesTeaching().courseHashMap.values().size() + 1];
        courses[0] = "-Select-";
        theCourseKey = courses[0];
        int i = 1;
        for (Course course  : instructor.getCoursesTeaching().courseHashMap.values()) {
            courses[i] = course.makeStringKey();
            i++;
        }
        return courses;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (checkCourseToNote()) {
            Course course =
                    instructor.getCoursesTeaching().courseHashMap.get(theCourseKey);
            instructor.postNote(titleField.getText(), noteField.getText(), course);
            makeSucceedDialog("Note posted");
            postNotePanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(sourcePanel);
            sourcePanel.setVisible(true);
        } else {
            makeWarningDialog("Please select a course");
        }

    }

    private boolean checkCourseToNote() {
        theCourseKey = (String)courseSelection.getSelectedItem();
        if (theCourseKey != null) {
            return !theCourseKey.equals("-Select-");
        } else {
            return false;
        }
    }


    JPanel getPostNotePanel() {
        return postNotePanel;
    }
}
