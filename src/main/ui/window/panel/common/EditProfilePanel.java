package ui.window.panel.common;

import model.*;
import ui.window.panel.admin.AdminCenterPanel;
import ui.window.panel.admin.ManagePanels;
import ui.window.panel.instructor.InstructorCenterPanel;
import ui.window.panel.student.StudentCenterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static model.UsersType.*;
import static ui.Main.MAIN_WINDOW;
import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

public class EditProfilePanel extends Panels {
    private JPanel editProfilePanel;
    private User user;
    private int labelX = 180;
    private int textX = labelX + 120;
    private boolean isAdminAction;
    private Admin admin;

    private JTextField unText;
    private JTextField pswText;
    private JTextField nameText;
    private JComboBox<String> genderBox;
    private JTextField ageText;

    private String username;
    private String password;
    private String name;
    private String gender;
    private int age;

    //    public EditProfilePanel(JPanel sourcePanel, User user) {
//        super(sourcePanel);
//        editProfilePanel = initializeDefaultPanel();
//        this.user = user;
//        initializeBackButton(editProfilePanel);
//        initializeLabels();
//        initializeFields();
//        initSaveButton();
//    }

    public EditProfilePanel(JComponent sourcePanel, User user) {
        super(sourcePanel);
        this.isAdminAction = false;
        editProfilePanel = initializeDefaultPanel();
        this.user = user;
        initializeBackButton(editProfilePanel);
        initializeLabels();
        initializeFields();
        initSaveButton();
    }

    public EditProfilePanel(JComponent sourcePanel, User user, Admin admin) {
        super(sourcePanel);
        this.isAdminAction = true;
        this.admin = admin;
        editProfilePanel = initializeDefaultPanel();
        this.user = user;
        initializeBackButton(editProfilePanel);
        initializeLabels();
        initializeFields();
        initSaveButton();
    }

    private void initializeLabels() {
        int labelY = 30;
        initializeLabel(editProfilePanel, "Username: ", labelX, labelY, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        labelY += 60;
        if (isAdminAction) {
            initializeLabel(editProfilePanel, "Password", labelX, labelY, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
            labelY += 60;
        }
        initializeLabel(editProfilePanel, "Name: ", labelX, labelY, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        labelY += 60;
        initializeLabel(editProfilePanel, "Gender: ", labelX, labelY, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        labelY += 60;
        initializeLabel(editProfilePanel, "Age: ", labelX, labelY, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
    }

    private void initializeFields() {
        int textY = 30;
        initUNText(textY);
        textY += 60;
        if (isAdminAction) {
            initPswText(textY);
            textY += 60;
        }
        initNameText(textY);
        textY += 60;
        initGenderBox(textY);
        textY += 60;
        initAgeText(textY);
    }

    private void initUNText(int textY) {
        unText = new JTextField(user.getUserName());
        unText.setSize(textDimension);
        unText.setLocation(textX, textY);
        unText.setEditable(false);
        unText.setBackground(Color.LIGHT_GRAY);
        editProfilePanel.add(unText);
    }

    private void initPswText(int textY) {
        pswText = new JTextField(user.getPassWord());
        pswText.setSize(textDimension);
        pswText.setLocation(textX, textY);
        editProfilePanel.add(pswText);
    }

    private void initNameText(int textY) {
        nameText = new JTextField(user.getName());
        nameText.setSize(textDimension);
        nameText.setLocation(textX, textY);
        if (!isAdminAction) {
            initNameTextListener();
        }
        editProfilePanel.add(nameText);
    }

    private void initNameTextListener() {
        nameText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!isChar(e.getKeyChar())) {
                    e.consume();
                }
                if (ageText.getText().length() >= 24) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private boolean isChar(char chr) {
        return (chr >= 65 && chr <= 90) || (chr >= 97 && chr <= 122 || chr == 32);
    }


    private void initGenderBox(int textY) {
        String[] genders = new String[]{"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setSize(textDimension);
        genderBox.setLocation(textX, textY);
        genderBox.setSelectedItem(user.getGender());
        editProfilePanel.add(genderBox);
    }

    private void initAgeText(int textY) {
        ageText = new JTextField(Integer.toString(user.getAge()));
        initAgeTextListener();
        ageText.setSize(textDimension);
        ageText.setLocation(textX, textY);
        editProfilePanel.add(ageText);
    }


    private void initAgeTextListener() {
        ageText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!isInt(e.getKeyChar())) {
                    e.consume();
                }
                if (ageText.getText().length() >= 2) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private boolean isInt(char chr) {
        return chr >= 47 && chr <= 57;
    }

    private void initSaveButton() {
        JButton save = new JButton("Save");
        save.setBounds(300, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
        save.addActionListener(new SaveAction());
        editProfilePanel.add(save);
    }

    private class SaveAction implements ActionListener {

        private void initUsername() {
            username = unText.getText();
        }

        private void initPsw() {
            password = user.getPassWord();
            if (isAdminAction) {
                password = pswText.getText();
            }
        }

        private void initName() {
            name = nameText.getText();
        }

        private void initAge() {
            if (ageText.getText().equals("")) {
                age = 0;
            } else {
                age = Integer.parseInt(ageText.getText());
            }
        }

        private void initGender() {
            gender = (String)genderBox.getSelectedItem();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            Users users = instructors;
//            if (user.getMyType() == ADMIN) {
//                users = admins;
//            } else if (user.getMyType() == STUDENT) {
//                users = students;
//            }
            initUsername();
            initPsw();
            initName();
            initAge();
            initGender();
            if (checkEmptyFields()) {
                editCondition();
            }
        }

        private void editCondition() {
            if (user.getMyType() == STUDENT) {
                studentRegister();
                afterSave();
            } else if (user.getMyType() == INSTRUCTOR) {
                instructorRegister();
                afterSave();
            } else {
                adminRegister();
                afterSave();
            }
        }

        private void afterSave() {
            updateUserMaps();
            editProfilePanel.setVisible(false);
            if (isAdminAction) {
                backToAdmin();
            } else if (user instanceof Student) {
                backToStudent();
            } else if (user instanceof Instructor) {
                backToInstructor();
            }
        }

        private void backToAdmin() {
            if (!admin.equals(user)) {
                MAIN_WINDOW.getMainWindow().setContentPane(new ManagePanels(
                        new AdminCenterPanel(
                                new MainPanel(), admin).getAdminCenterPanel(),
                        user.getMyType(), admin).getManagePanel());
            } else {
                Admin newAdmin = (Admin) admins.usersHashMap.get(admin.getUserName());
                MAIN_WINDOW.getMainWindow().setContentPane(new AdminCenterPanel(
                        new MainPanel(), newAdmin).getAdminCenterPanel());
            }
        }

        private void backToStudent() {
            Student newStudent = (Student) user;
            MAIN_WINDOW.getMainWindow().setContentPane(new StudentCenterPanel(
                    new MainPanel(), newStudent
            ).getStudentCenterPanel());
        }

        private void backToInstructor() {
            Instructor newInstructor = (Instructor)user;
            MAIN_WINDOW.getMainWindow().setContentPane(new InstructorCenterPanel(
                    new MainPanel(), newInstructor
            ).getInstructorCenterPanel());
        }

        private void studentRegister() {
            Student student = new Student(username, password, name, gender, age,
                    ((Student) user).getCourses(), ((Student) user).getNotes(), ((Student) user).hasNewNote());
            student.updateUserToFile();
        }

        private void instructorRegister() {
            Instructor instructor = new Instructor(username, password, name, gender, age,
                    ((Instructor) user).getCoursesTeaching());
            instructor.updateUserToFile();
        }

        private void adminRegister() {
            Admin admin = new Admin(username, password, name, gender, age);
            admin.updateUserToFile();
        }

        private boolean checkEmptyFields() {
            if (name.equals("")) {
                makeWarningDialog("name cannot be empty");
            } else if (age == 0) {
                makeWarningDialog("age cannot be 0");
            } else if (password.equals("") && isAdminAction) {
                makeWarningDialog("password cannot be empty");
            } else {
                return true;
            }
            return false;
        }
    }

    public JPanel getEditProfilePanel() {
        return editProfilePanel;
    }
}
