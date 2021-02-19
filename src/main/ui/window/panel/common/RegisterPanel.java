package ui.window.panel.common;

import model.*;
import ui.window.panel.admin.AdminCenterPanel;
import ui.window.panel.admin.ManagePanels;

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

public class RegisterPanel extends Panels {
    private JPanel registerPanel;
    private int labelX = 180;
    private int textX = labelX + 120;
    private UsersType type;
    private boolean isAdminAccess;
    private Admin admin;

    private String username;
    private String password;
    private String sndPsw;
    private String name;
    private String gender;
    private User user;
    private int age;

    private JLabel pswWarning;
    private JLabel usernameWarning;
    private JLabel usernameExistWarning;

    private JTextField unText;
    private JPasswordField pswText;
    private JPasswordField secondPswText;
    private JTextField nameText;
    private JComboBox genderBox;
    private JTextField ageText;

    //Only for instructors:
    private JTextField codeText;
    private static final  String INVITATION_CODE = "Trust Natural Recursion";
    private static final int Y_INTERVAL = 60;

//    public RegisterPanel(JComponent sourcePanel) {
//        super(sourcePanel);
//        registerPanel = initializeDefaultPanel();
//        initializeLabels();
//        initializeFields();
//        initializeBackButton(registerPanel);
//        initializeRegisterButton(registerPanel);
//    }

    RegisterPanel(JComponent sourcePanel, UsersType type) {
        super(sourcePanel);
        this.type = type;
        isAdminAccess = false;
        registerPanel = initializeDefaultPanel();
        initializeLabels();
        initializeFields();
        initializeBackButton(registerPanel);
        initializeRegisterButton(registerPanel, type);
    }

    public RegisterPanel(JComponent sourcePanel, UsersType type,  Admin admin) {
        super(sourcePanel);
        this.type = type;
        isAdminAccess = true;
        this.admin = admin;
        registerPanel = initializeDefaultPanel();
        initializeLabels();
        initializeFields();
        initializeBackButton(registerPanel);
        initializeRegisterButton(registerPanel, type);
    }

//    private void initializePanel() {
//        registerPanel = new JPanel();
//        registerPanel.setLayout(null);
//        registerPanel.setBackground(Color.WHITE);
//        registerPanel.setVisible(true);
//    }

    public JPanel getRegisterPanel() {
        return registerPanel;
    }

    private void initializeLabels() {
        initializeWelcomeLabel(registerPanel,
                type.toString().substring(0,1) + type.toString().substring(1).toLowerCase() + " Register");
        initializeLabel(registerPanel, "Username: ", labelX, Y_INTERVAL, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initializeLabel(registerPanel, "Password: ", labelX, Y_INTERVAL * 2, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initializeLabel(registerPanel, "Repeat Password:", labelX, Y_INTERVAL * 3, BUTTON_WIDTH, BUTTON_HEIGHT);
        initializeLabel(registerPanel, "Name: ", labelX, Y_INTERVAL * 4, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initializeLabel(registerPanel, "Gender: ", labelX, Y_INTERVAL * 5, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        initializeLabel(registerPanel, "Age: ", labelX, Y_INTERVAL * 6, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        if (type == INSTRUCTOR && !isAdminAccess) {
            initializeLabel(registerPanel, "Code: ", labelX, Y_INTERVAL * 7, BUTTON_WIDTH / 2, BUTTON_HEIGHT);
        }
        initPswWarningLabel();
        initUsernameWarningLabel();
    }


    private void initUsernameWarningLabel() {
        usernameWarning = new JLabel("Username Must longer than 4 characters");
        usernameExistWarning = new JLabel("Username already exist");
        usernameExistWarning.setVisible(false);
        usernameWarning.setVisible(false);
        usernameWarning.setForeground(Color.RED);
        usernameExistWarning.setForeground(Color.RED);
        usernameWarning.setSize(200, 40);
        usernameExistWarning.setSize(200, 40);
        usernameWarning.setLocation(500, Y_INTERVAL);
        usernameExistWarning.setLocation(500, Y_INTERVAL);
        registerPanel.add(usernameWarning);
        registerPanel.add(usernameExistWarning);
    }

    private void initPswWarningLabel() {
        pswWarning = new JLabel("Two passwords don't match");
        pswWarning.setVisible(false);
        pswWarning.setForeground(Color.RED);
        pswWarning.setSize(200, 40);
        pswWarning.setLocation(500, Y_INTERVAL * 2);
        registerPanel.add(pswWarning);
    }

    private void initializeFields() {
        initUnText();
        initPswText();
        initSecondPswText();
        initNameText();
        initGenderBox();
        initAgeText();
//        if (sourcePanel.toString().equals("instructorPanel")) {
//            initCodeText();
//        }
        if (type == INSTRUCTOR && !isAdminAccess) {
            initCodeText();
        }
    }

    private void initUnText() {
        unText = new JTextField();
        unText.setSize(textDimension);
        unText.setLocation(textX, Y_INTERVAL);
        unText.addKeyListener(new UsernameListener());
        registerPanel.add(unText);
    }

    class UsernameListener implements KeyListener {
        boolean backSpacePressed;
        String username = unText.getText();

        //MODIFY: this
        //EFFECT: if the key is not backspace, add the key to the end of username,
        //        and check for username exist or username is less than 4 characters
        @Override
        public void keyTyped(KeyEvent e) {
            if (!backSpacePressed) {
                username += e.getKeyChar();
                checkUsername(e);
            }
        }

        //MODIFY: this
        //EFFECT: check if backspace is pressed, if pressed: if text in field >= 1, cut the last char of username,
        //        else username = "", then change backSpacePressed to true.
        @Override
        public void keyPressed(KeyEvent e) {
            backSpacePressed = false;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (username.length() >= 1) {
                    username = username.substring(0, username.length() - 1);
                } else {
                    username = "";
                }
                checkUsername(e);
                backSpacePressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        private void checkUsername(KeyEvent e) {
            boolean check = instructors.usersHashMap.containsKey(username);
//            if (sourcePanel.toString().equals("studentPanel")) {
//                check = students.usersHashMap.containsKey(username);
//            }
            if (type == STUDENT) {
                check = students.usersHashMap.containsKey(username);
            } else if (type == ADMIN) {
                check = admins.usersHashMap.containsKey(username);
            }
            usernameExistWarning.setVisible(check);
            if (username.length() > 16) {
                e.consume();
            }

            if (usernameExistWarning.isVisible()) {
                usernameWarning.setVisible(false);
            } else {
                usernameWarning.setVisible(username.length() < 4);
            }
        }
    }

    private void initPswText() {
        pswText = new JPasswordField();
        pswText.setSize(textDimension);
        pswText.setLocation(textX, Y_INTERVAL * 2);
        pswText.addKeyListener(new PswListener());
        registerPanel.add(pswText);
    }

    private void initSecondPswText() {
        secondPswText = new JPasswordField();
        secondPswText.setSize(textDimension);
        secondPswText.setLocation(textX, Y_INTERVAL * 3);
        secondPswText.addKeyListener(new PswListener());
        registerPanel.add(secondPswText);
    }

    class PswListener implements KeyListener {
        boolean backSpacePressed = false;
        String fstPsw;
        String sndPsw;

        private void refreshData() {
            fstPsw = new String(pswText.getPassword());
            sndPsw = "";
            if (secondPswText != null) {
                sndPsw = new String(secondPswText.getPassword());
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            refreshData();
            if (!backSpacePressed) {
                if (e.getSource() == pswText) {
                    fstPsw += e.getKeyChar();
                } else {
                    sndPsw += e.getKeyChar();
                }
                checkPasswordsMatch();
            }
        }

        //MODIFY: this
        //EFFECT: check if backspace is pressed, if pressed: if text in field >= 1, cut the last char of username,
        //        else username = "", then change backSpacePressed to true.
        @Override
        public void keyPressed(KeyEvent e) {
            refreshData();
            backSpacePressed = false;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (e.getSource() == pswText) {
                    if (!fstPsw.equals("")) {
                        fstPsw = fstPsw.substring(0, fstPsw.length() - 1);
                    }
                } else {
                    if (!sndPsw.equals("")) {
                        sndPsw = sndPsw.substring(0, sndPsw.length() - 1);
                    }
                }
                checkPasswordsMatch();
                backSpacePressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        private void checkPasswordsMatch() {
            if (fstPsw.compareTo(sndPsw) != 0) {
                pswWarning.setVisible(true);
            } else {
                pswWarning.setVisible(false);
            }
        }
    }



    private void initNameText() {
        nameText = new JTextField();
        nameText.setSize(textDimension);
        nameText.setLocation(textX, Y_INTERVAL * 4);
        if (!isAdminAccess) {
            initNameTextListener();
        }
        registerPanel.add(nameText);
    }

    private void initNameTextListener() {
        nameText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!isChar(e.getKeyChar())) {
                    e.consume();
                }
                if (ageText.getText().length() >= 16) {
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

    private void initGenderBox() {
        String[] genders = new String[]{"-Select-", "Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setSize(textDimension);
        genderBox.setLocation(textX, Y_INTERVAL * 5);
        genderBox.setSelectedItem("-Select-");
        registerPanel.add(genderBox);
    }

    private void initAgeText() {
        ageText = new JTextField(3);
        if (!isAdminAccess) {
            initAgeTextListener();
        }
        ageText.setSize(textDimension);
        ageText.setLocation(textX, Y_INTERVAL * 6);
        registerPanel.add(ageText);
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

    private void initCodeText() {
        codeText = new JTextField();
        codeText.setSize(textDimension);
        codeText.setLocation(textX, Y_INTERVAL * 7);
        registerPanel.add(codeText);
    }

    @Override
    protected void initializeRegisterButton(JPanel thisPanel, UsersType type) {
        register = new JButton("Register");
        register.setBounds(300, 500, BUTTON_WIDTH, BUTTON_HEIGHT);
        register.addActionListener(new RegisterAction());
        registerPanel.add(register);
    }

    private class RegisterAction implements ActionListener {

        Users users;

        private void initUsername() {
            username = unText.getText();
        }

        private void initPsw() {
            password = new String(pswText.getPassword());
            sndPsw = new String(secondPswText.getPassword());
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
            users = instructors;
//            if ("studentPanel".equals(sourcePanel.toString())) {
//                users = students;
//            }
            if (type == STUDENT) {
                users = students;
            } else if (type == ADMIN) {
                users = admins;
            }
            initUsername();
            initPsw();
            initName();
            initAge();
            initGender();
            if (checkEmptyFields(users)) {
                registerCondition();
            }
        }

        private void registerCondition() {
            if (type == STUDENT) {
                studentPanelOperation();
            } else if (type == ADMIN && isAdminAccess) {
                adminPanelOperation();
            } else {
                instructorPanelOperation();
            }
        }

        private void studentPanelOperation() {
            user = new Student(username, password, name, gender, age);
            userRegister();
            if (isAdminAccess) {
                makeSucceedDialog("student added");
                switchPanel();
            } else {
                makeSucceedDialog("You can now log in!");
                registerPanel.setVisible(false);
                MAIN_WINDOW.getMainWindow().setContentPane(new LoginPanel(sourcePanel).getLoginPanel());
            }

        }

        private void adminPanelOperation() {
            user = new Admin(username, password, name, gender, age);
            userRegister();
            makeSucceedDialog("admin added");
            switchPanel();
        }

        private void instructorPanelOperation() {
            user = new Instructor(username, password, name, gender, age);
            if (isAdminAccess) {
                userRegister();
                makeSucceedDialog("Instructor added");
                switchPanel();
            } else if (checkInvitationCode()) {
                userRegister();
                makeSucceedDialog("You can now log in!");
                registerPanel.setVisible(false);
                MAIN_WINDOW.getMainWindow().setContentPane(new LoginPanel(sourcePanel).getLoginPanel());
            } else {
                makeWarningDialog("Your invitation Code is incorrect");
            }
        }



        private void userRegister() {
            if (!users.addNewUser(user)) {
                makeWarningDialog("System Full");
                registerFail();
            }
        }

        private void registerFail() {
            registerPanel.setVisible(false);
            if (!isAdminAccess) {
                MAIN_WINDOW.setContentPane(new MainPanel());
            } else {
                MAIN_WINDOW.setContentPane(sourcePanel);
                sourcePanel.setVisible(true);
            }
        }

        private boolean checkEmptyFields(Users users) {
            if (username.length() < 4  || username.length() > 16) {
                makeWarningDialog("Your username is invalid");
            } else if (users.usersHashMap.containsKey(username)) {
                makeWarningDialog("Username already exist");
            } else if (!password.equals(sndPsw)) {
                makeWarningDialog("Please enter your password");
            } else if (name.equals("")) {
                makeWarningDialog("Please enter your name");
            } else if (age == 0) {
                makeWarningDialog("Please enter your age");
            } else if (!gender.equals("Male") && !gender.equals("Female") && !gender.equals("Other")) {
                makeWarningDialog("Please select your gender");
            } else {
                return true;
            }
            return false;
        }

        private boolean checkInvitationCode() {
            return codeText.getText().trim().equals(INVITATION_CODE);
        }

        private void switchPanel() {
            registerPanel.setVisible(false);
            if (isAdminAccess) {
                MAIN_WINDOW.getMainWindow().setContentPane(new ManagePanels(
                        new AdminCenterPanel(
                                new MainPanel(), admin).getAdminCenterPanel(),
                        user.getMyType(), admin).getManagePanel());
            } else {
                MAIN_WINDOW.getMainWindow().setContentPane(sourcePanel);
                sourcePanel.setVisible(true);
            }

        }
    }
}
