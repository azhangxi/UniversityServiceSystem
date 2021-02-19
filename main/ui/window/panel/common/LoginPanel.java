package ui.window.panel.common;

import model.Admin;
import model.Instructor;
import model.Student;
import model.UsersType;
import network.ReadWeb;
import org.json.JSONException;
import ui.window.panel.admin.AdminCenterPanel;
import ui.window.panel.admin.AdminPanel;
import ui.window.panel.instructor.InstructorCenterPanel;
import ui.window.panel.student.StudentCenterPanel;
import ui.window.panel.student.StudentPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.time.LocalDate;

import static model.UsersType.*;
import static ui.Main.MAIN_WINDOW;
import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

class LoginPanel extends Panels {
    private JPanel loginPanel;
    private JTextField unText;
    private JPasswordField pswText;
    private UsersType type;

    private String username;
    private String password;

    private static final int MIDDLE_X =  300;

    LoginPanel(JComponent sourcePanel) {
        super(sourcePanel);
        switch (sourcePanel.toString()) {
            case "studentPanel":
                type = STUDENT;
                break;
            case "instructorPanel":
                type = INSTRUCTOR;
                break;
            default:
                type = ADMIN;
                break;
        }
        loginPanel = initializeDefaultPanel();
        initializeLabels();
        initializeFields();
        initializeButtons();
    }

    private void refreshData() {
        students = studentFile.readUsers();
        instructors = instructorFile.readUsers();
        admins = adminFile.readUsers();
    }

    JPanel getLoginPanel() {
        return loginPanel;
    }

    private void initializeLabels() {
        initializeLabel(loginPanel, "Username: ", 250, 160, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initializeLabel(loginPanel, "Password: ", 250, 220, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initWelcomeLabel();
    }

    private void initWelcomeLabel() {
        switch (type) {
            case ADMIN:
                initializeWelcomeLabel(loginPanel, "Administrator Login");
                break;
            case INSTRUCTOR:
                initializeWelcomeLabel(loginPanel, "Instructor Login");
                break;
            default:
                initializeWelcomeLabel(loginPanel, "Student Login");
                break;
        }

    }

    private void initializeFields() {
        unText = new JTextField();
        unText.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        unText.setLocation(330, 160);
        loginPanel.add(unText);

        pswText = new JPasswordField();
        pswText.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        pswText.setLocation(330, 220);
        loginPanel.add(pswText);
    }

    private void initializeButtons() {
        initializeBackButton(loginPanel);

        loginPanel.add(login);
        login.setBounds(MIDDLE_X, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
        login.addActionListener(new LoginAction());
    }



    class LoginAction implements ActionListener {
        //MODIFY: this
        //EFFECT: login action, get entered fields and switch type to do login actions
        @Override
        public void actionPerformed(ActionEvent e) {
            username = unText.getText();
            password = new String(pswText.getPassword());
            refreshData();
            switch (type) {
                case STUDENT:
                    studentLogin();
                    break;
                case INSTRUCTOR:
                    instructorLogin();
                    break;
                default:
                    adminLogin();
                    break;
            }
        }

        private void studentLogin() {
            if (checkStudentLogin(username, password)) {
                Student student = (Student) students.usersHashMap.get(username);
                addWeatherNote(student);
                loginPanel.setVisible(false);
                StudentCenterPanel studentCenterPanel = new StudentCenterPanel(new
                        StudentPanel(new MainPanel()).getStudentPanel(),
                        (Student) students.usersHashMap.get(username));
                MAIN_WINDOW.getMainWindow().setContentPane(studentCenterPanel.getStudentCenterPanel());
            }
        }

        private void addWeatherNote(Student student) {
            try {
                student.getNotes().put("Weather For Today\t" + LocalDate.now(),
                        "The average temperature in Vancouver is :"
                        + new ReadWeb().getCurrentTempInVancouver());
                student.setHasNewNote(true);
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }

        private void adminLogin() {
            if (checkAdminLogin(username, password)) {
                loginPanel.setVisible(false);
                AdminCenterPanel adminCenterPanel = new AdminCenterPanel(
                        new AdminPanel(new MainPanel()).getAdminPanel(),
                        (Admin) admins.usersHashMap.get(username));
                MAIN_WINDOW.getMainWindow().setContentPane(adminCenterPanel.getAdminCenterPanel());
            }
        }

        private void instructorLogin() {
            if (checkInstructorLogin(username, password)) {
                loginPanel.setVisible(false);
                InstructorCenterPanel instructorCenterPanel = new InstructorCenterPanel(
                        new MainPanel(),
                        (Instructor) instructors.usersHashMap.get(username));
                MAIN_WINDOW.getMainWindow().setContentPane(instructorCenterPanel.getInstructorCenterPanel());
            }
        }

        private boolean checkStudentLogin(String username, String password) {
            if (!students.usersHashMap.containsKey(username)) {
                makeWarningDialog("Username does not exist");
            } else if (!students.usersHashMap.get(username).getPassWord().equals(password)) {
                makeWarningDialog("Incorrect password");
            } else {
                return true;
            }
            return false;
        }

        private boolean checkAdminLogin(String username, String password) {
            if (!admins.usersHashMap.containsKey(username)) {
                makeWarningDialog("Username does not exist");
            } else if (!admins.usersHashMap.get(username).getPassWord().equals(password)) {
                makeWarningDialog("Incorrect password");
            } else {
                return true;
            }
            return false;
        }

        private boolean checkInstructorLogin(String username, String password) {
            if (!instructors.usersHashMap.containsKey(username)) {
                makeWarningDialog("Username does not exist");
            } else if (!instructors.usersHashMap.get(username).getPassWord().equals(password)) {
                makeWarningDialog("Incorrect password");
            } else {
                return true;
            }
            return false;
        }
    }




}
