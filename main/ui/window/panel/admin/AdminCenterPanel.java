package ui.window.panel.admin;

import model.Admin;
import ui.window.panel.common.EditProfilePanel;
import ui.window.panel.common.Panels;

import javax.swing.*;

import static model.SuperAdmin.SUPER_ADMIN;
import static model.UsersType.*;
import static ui.Main.MAIN_WINDOW;

public class AdminCenterPanel extends Panels {
    private JPanel adminCenterPanel;
    private Admin admin;

    public AdminCenterPanel(JComponent sourcePanel, Admin admin) {
        super(sourcePanel);
        adminCenterPanel = new JPanel() {
            @Override
            public String toString() {
                return "adminCenterPanel";
            }
        };
        adminCenterPanel = initializePanelSettings(adminCenterPanel);
        this.admin = admin;
        initializeButtons();
        initializeLabels();
    }

    private void initializeLabels() {
        initializeWelcomeLabel(adminCenterPanel, "Welcome Dear " + admin.getName());
    }

    private void initializeButtons() {
        initializeBackButton(adminCenterPanel);
        initManageStudentButton();
        initManageInstructorButton();
        initManageAdminButton();
        initEditProfileButton();
//        initModifyPswButton();
    }

    //MODIFY: this
    //EFFECT: make a back button that switch the panel to the previous one
    @Override
    public void initializeBackButton(JComponent thisPanel) {
        back = new JButton("Log Out");
        back.setBounds(10, 10, 80, 30);
        back.addActionListener(new BackAction(thisPanel));
        thisPanel.add(back);
    }

    private void initManageStudentButton() {
        initializeButtonDefaultDimension(adminCenterPanel, "Manage Student",
                LEFT_BUTTON_X, MIDDLE_BUTTON_Y,
                e -> {
                    adminCenterPanel.setVisible(false);
                    MAIN_WINDOW.getMainWindow().setContentPane(
                            new ManagePanels(adminCenterPanel, STUDENT, admin).getManagePanel());
                });
    }

    private void initManageInstructorButton() {
        initializeButtonDefaultDimension(adminCenterPanel, "Manage Instructor",
                MIDDLE_BUTTON_X, MIDDLE_BUTTON_Y,
                e -> {
                    adminCenterPanel.setVisible(false);
                    MAIN_WINDOW.getMainWindow().setContentPane(
                            new ManagePanels(adminCenterPanel, INSTRUCTOR, admin).getManagePanel());
                });
    }

    private void initManageAdminButton() {
        if (admin.equals(SUPER_ADMIN)) {
            initializeButtonDefaultDimension(adminCenterPanel, "Manage Admin", RIGHT_BUTTON_X,
                    MIDDLE_BUTTON_Y,
                    e -> {
                        adminCenterPanel.setVisible(false);
                        MAIN_WINDOW.getMainWindow().setContentPane(
                                new ManagePanels(adminCenterPanel, ADMIN, admin).getManagePanel());
                    });
        }
    }

    private void initEditProfileButton() {
        if (!admin.equals(SUPER_ADMIN)) {
            initializeButtonDefaultDimension(adminCenterPanel, "Edit Profile",
                    RIGHT_BUTTON_X, MIDDLE_BUTTON_Y,
                    e -> {
                        adminCenterPanel.setVisible(false);
                        MAIN_WINDOW.getMainWindow().setContentPane(
                                new EditProfilePanel(adminCenterPanel, admin, admin).getEditProfilePanel());
                    });
        }
    }

//    private void initModifyPswButton() {
//        if (!admin.equals(SUPER_ADMIN)) {
//            initializeButtonDefaultDimension(adminCenterPanel, "ModifyPswPanel",
//                    MIDDLE_BUTTON_X, MIDDLE_BUTTON_Y + 70,
//                    e -> {
//                        adminCenterPanel.setVisible(false);
//                        MAIN_WINDOW.getMainWindow().setContentPane(
//                                new ModifyPswPanel(adminCenterPanel, admin).getModifyPswPanel());
//                    });
//        }
//
//    }

    public JPanel getAdminCenterPanel() {
        return adminCenterPanel;
    }
}
