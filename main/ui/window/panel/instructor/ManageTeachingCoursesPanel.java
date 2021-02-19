package ui.window.panel.instructor;

import model.Instructor;
import ui.window.panel.common.MainPanel;
import ui.window.panel.common.Panels;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.Color.BLACK;
import static java.awt.Color.LIGHT_GRAY;
import static ui.Main.MAIN_WINDOW;

class ManageTeachingCoursesPanel extends Panels {
    private JPanel manageTeachingCoursesPanel;
    private Instructor instructor;

    private DefaultListModel<String> defaultListModelLeft;
    private DefaultListModel<String> defaultListModelRight;

    private JList<String> leftList;
    private JList<String> rightList;

    ManageTeachingCoursesPanel(JPanel sourcePanel, Instructor instructor) {
        super(sourcePanel);
        manageTeachingCoursesPanel = initializeDefaultPanel();
        this.instructor = instructor;
        initializeButtons();
        initializeLists();
        initializeLabels();
    }

    private void initializeButtons() {
        initializeBackButton(manageTeachingCoursesPanel);
        initializeButton(manageTeachingCoursesPanel, ">>", 370, 200, 60, 30, new MoveRightListener());
        initializeButton(manageTeachingCoursesPanel, "<<", 370, 300, 60, 30, new MoveLeftListener());
    }

    private void initializeLabels() {
        initializeLabel(manageTeachingCoursesPanel, "Available Courses", 160, 30, 200, 50);
        initializeLabel(manageTeachingCoursesPanel, "Your Courses", 570, 30, 200, 50);
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
        manageTeachingCoursesPanel.add(leftList);
    }

    private void initDataInLeftList() {
        defaultListModelLeft = new DefaultListModel<>();
        for (String courseName : courses.courseHashMap.keySet()) {
            if (courses.courseHashMap.get(courseName).getInstructor() == null) {
                defaultListModelLeft.addElement(courseName);
            }
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
        manageTeachingCoursesPanel.add(rightList);
    }

    private void refreshDataInRightList() {
        defaultListModelRight = new DefaultListModel<>();
        for (String courseName : instructor.getCoursesTeaching().courseHashMap.keySet()) {
            defaultListModelRight.addElement(courseName);
        }
    }

    private void refreshPage() {
        updateUserMaps();
        Instructor newInstructor = (Instructor) instructors.usersHashMap.get(instructor.getUserName());
        manageTeachingCoursesPanel.removeAll();
        manageTeachingCoursesPanel.setVisible(false);
        MAIN_WINDOW.getMainWindow().setContentPane(new ManageTeachingCoursesPanel(
                new InstructorCenterPanel(
                        new MainPanel(), newInstructor).getInstructorCenterPanel(), newInstructor
        ).getManageTeachingCoursesPanel());
    }


    class MoveRightListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            for (String courseName : leftList.getSelectedValuesList()) {
                instructor.addNewCourseTeaching(courseName);
            }
            refreshPage();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    class MoveLeftListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            for (String courseKey : rightList.getSelectedValuesList()) {
                instructor.deleteCourseTeaching(courseKey);
            }
            refreshPage();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    JPanel getManageTeachingCoursesPanel() {
        return manageTeachingCoursesPanel;
    }
}
