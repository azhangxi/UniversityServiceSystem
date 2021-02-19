package ui.window.panel.student;

import model.Student;
import ui.window.panel.common.Panels;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;

public class AddOrDropCoursePanel extends Panels {
    private JPanel addOrDropCoursePanel;
    private Student student;

    private DefaultListModel<String> defaultListModelLeft;
    private DefaultListModel<String> defaultListModelRight;

    private JList<String> leftList;
    private JList<String> rightList;

    AddOrDropCoursePanel(JPanel sourcePanel, Student student) {
        super(sourcePanel);
        addOrDropCoursePanel = initializeDefaultPanel();
        this.student = student;
        initializeButtons();
        initializeLists();
        initializeLabels();
    }

    private void initializeButtons() {
        initializeBackButton(addOrDropCoursePanel);
        initializeButton(addOrDropCoursePanel, ">>", 370, 200, 60, 30, new MoveRightListener());
        initializeButton(addOrDropCoursePanel, "<<", 370, 300, 60, 30, new MoveLeftListener());
    }

    private void initializeLabels() {
        initializeLabel(addOrDropCoursePanel, "Available Courses", 160, 30, 200, 50);
        initializeLabel(addOrDropCoursePanel, "Your Courses", 570, 30, 200, 50);
    }

    private void initializeLists() {
        initLeftList();
        initRightList();
    }

    private void initLeftList() {
        leftList = new JList<>();
        initDataInLeftList();
        leftList.setModel(defaultListModelLeft);
        leftList.setSize(130, 400);
        leftList.setLocation(150,80);
        leftList.setBackground(LIGHT_GRAY);
        leftList.setBorder(BorderFactory.createLineBorder(BLACK));
        addOrDropCoursePanel.add(leftList);
    }

    private void initDataInLeftList() {
        defaultListModelLeft = new DefaultListModel<>();
        for (String courseName : courses.courseHashMap.keySet()) {
            defaultListModelLeft.addElement(courseName);
        }
    }

    private void initRightList() {
        rightList = new JList<>();
        refreshDataInRightList();
        rightList.setModel(defaultListModelRight);
        rightList.setSize(130, 400);
        rightList.setLocation(550, 80);

        rightList.setBackground(LIGHT_GRAY);
        rightList.setBorder(BorderFactory.createLineBorder(BLACK));
        addOrDropCoursePanel.add(rightList);
    }

    private void refreshDataInRightList() {
        defaultListModelRight = new DefaultListModel<>();
        for (String courseName : student.getCourses().courseHashMap.keySet()) {
            defaultListModelRight.addElement(courseName);
        }
    }




    private class MoveRightListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (String courseName : leftList.getSelectedValuesList()) {
                student.addOrDropCourse(courseName);
            }
            rightList.setVisible(false);
            leftList.setVisible(false);
            initRightList();
            initLeftList();
        }
    }

    private class MoveLeftListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (String courseKey : rightList.getSelectedValuesList()) {

                student.addOrDropCourse(courseKey);
            }
            refresh();
        }
    }

    private void refresh() {
        addOrDropCoursePanel.remove(leftList);
        addOrDropCoursePanel.remove(rightList);
        initRightList();
        initLeftList();
        addOrDropCoursePanel.repaint();
    }

    JPanel getAddOrDropCoursePanel() {
        return addOrDropCoursePanel;
    }
}
