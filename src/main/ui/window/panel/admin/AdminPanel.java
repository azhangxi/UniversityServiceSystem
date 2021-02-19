package ui.window.panel.admin;

import ui.window.panel.common.Panels;

import javax.swing.*;

import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

public class AdminPanel extends Panels {
    private JPanel adminPanel;

    public AdminPanel(JPanel sourcePanel) {
        super(sourcePanel);
        adminPanel = new JPanel() {
            @Override
            public String toString() {
                return "adminPanel";
            }
        };
        adminPanel = initializePanelSettings(adminPanel);
        initializeButtons();
        initializeWelcomeLabel(adminPanel, "Admin Center");
    }

    private void initializeButtons() {
        initializeBackButton(adminPanel);
        initializeLoginButton(adminPanel);
        changeButtonLocation();
    }

    private void changeButtonLocation() {
        login.setBounds(MIDDLE_BUTTON_X, MIDDLE_BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT);
    }

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
