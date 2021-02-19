package ui.window.panel.common;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static ui.window.MainWindow.BUTTON_HEIGHT;
import static ui.window.MainWindow.BUTTON_WIDTH;

public class ModifyPswPanel extends Panels implements ActionListener {
    private static final int LABEL_X = 230;
    private static final int TEXT_X = 420;

    private JPanel modifyPswPanel;
    private User user;

    private JLabel oldPswLabel;
    private JLabel newPswLabel;
    private JLabel newPsw2Label;
    private JLabel pswWarning;

    private JTextField oldPswText;
    private JTextField newPswText;
    private JTextField newPsw2Text;


    public ModifyPswPanel(JComponent sourcePanel, User user) {
        super(sourcePanel);
        modifyPswPanel = initializeDefaultPanel();
        initializeLabels();
        initializeBackButton(modifyPswPanel);
        initializeSaveButton();
        initializeText();
        this.user = user;
    }

    private void initializeLabels() {
        initOldPswLabel();
        initNewPswLabel();
        initNewPsw2Label();
        initPswWarningLabel();
    }

    private void initOldPswLabel() {
        oldPswLabel = new JLabel("Old Password:");
        oldPswLabel.setBounds(LABEL_X, 100, BUTTON_WIDTH, BUTTON_HEIGHT);
        modifyPswPanel.add(oldPswLabel);
    }

    private void initNewPswLabel() {
        newPswLabel = new JLabel("New Password:");
        newPswLabel.setBounds(LABEL_X, 200, BUTTON_WIDTH, BUTTON_HEIGHT);
        modifyPswPanel.add(newPswLabel);
    }

    private void initNewPsw2Label() {
        newPsw2Label = new JLabel("New Password Again:");
        newPsw2Label.setBounds(LABEL_X, 300, BUTTON_WIDTH, BUTTON_HEIGHT);
        modifyPswPanel.add(newPsw2Label);
    }

    private void initPswWarningLabel() {
        pswWarning = new JLabel("Two passwords don't match");
        pswWarning.setVisible(false);
        pswWarning.setForeground(Color.RED);
        pswWarning.setSize(200, 40);
        pswWarning.setLocation(TEXT_X + 10, newPsw2Label.getY() + 30);
        modifyPswPanel.add(pswWarning);
    }


    private void initializeText() {
        initOldPswText();
        initNewPswText();
        initNewPsw2Text();
    }

    private void initOldPswText() {
        oldPswText = new JPasswordField();
        oldPswText.setSize(textDimension);
        oldPswText.setLocation(TEXT_X, oldPswLabel.getY());
        modifyPswPanel.add(oldPswText);
    }

    private void initNewPswText() {
        newPswText = new JPasswordField();
        newPswText.setSize(textDimension);
        newPswText.setLocation(TEXT_X, newPswLabel.getY());
        newPswText.addKeyListener(new PasswordListener());
        modifyPswPanel.add(newPswText);
    }

    private void initNewPsw2Text() {
        newPsw2Text = new JPasswordField();
        newPsw2Text.setSize(textDimension);
        newPsw2Text.setLocation(TEXT_X, newPsw2Label.getY());
        newPsw2Text.addKeyListener(new PasswordListener());
        modifyPswPanel.add(newPsw2Text);
    }


    private void initializeSaveButton() {
        initializeButton(modifyPswPanel, "Save", 300,380,BUTTON_WIDTH, BUTTON_HEIGHT, this);
    }

    //EFFECT: if nothing is empty and no warnings on and old psw is correct and new psw is not the same as old one,
    //        save the new psw, and make a succeed dialog
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!checkEmpty() && !checkWarningOn() && checkOldPsw() && !checkSamePsw()) {
            saveUserPsw();
            makeSucceedDialog("Your password has been changed");
        }
    }

    private boolean checkEmpty() {
        if (isEmptyField(newPswText) || isEmptyField(newPsw2Text) || isEmptyField(oldPswText)) {
            makeWarningDialog("Please finish empty blocks");
            return true;
        }
        return false;
    }

    private boolean checkOldPsw() {
        String oldPswInFile = user.getPassWord();
        if (!oldPswText.getText().equals(oldPswInFile)) {
            makeWarningDialog("Old password wrong");
            return false;
        }
        return true;
    }

    private boolean checkWarningOn() {
        return pswWarning.isVisible();
    }

    private boolean checkSamePsw() {
        if (newPswText.getText().equals(oldPswText.getText())) {
            makeWarningDialog("Your new password is the same as your old one");
            return true;
        }
        return false;
    }

    private void saveUserPsw() {
        user.setPassWord(newPswText.getText());
        user.updateUserToFile();
    }

    class PasswordListener implements KeyListener {
        boolean backSpacePressed = false;
        String newPsw;
        String newPsw2;

        //MODIFY: this
        //EFFECT: get current text in text fields
        private void refreshData() {
            newPsw = newPswText.getText();
            newPsw2 = newPsw2Text.getText();
        }

        //EFFECT: if back space is not pressed, append new char to the psw, and check if two psw match
        @Override
        public void keyTyped(KeyEvent e) {
            refreshData();
            if (!backSpacePressed) {
                if (e.getSource() == newPswText) {
                    newPsw += e.getKeyChar();
                } else {
                    newPsw2 += e.getKeyChar();
                }
                checkPasswordsMatch();
            }
        }

        //MODIFY: this
        //EFFECT: set backspace pressed to false. if back space is pressed, if user is entering newPsw,
        //        get the text without last char, else get
        //        the second psw without last char, then check psw match and set backspace pressed to true.
        @Override
        public void keyPressed(KeyEvent e) {
            refreshData();
            backSpacePressed = false;
            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                if (e.getSource() == newPswText) {
                    if (!newPsw.equals("")) {
                        newPsw = newPsw.substring(0, newPsw.length() - 1);
                    }
                } else {
                    if (!newPsw2.equals("")) {
                        newPsw2 = newPsw2.substring(0, newPsw2.length() - 1);
                    }
                }
                checkPasswordsMatch();
                backSpacePressed = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}

        private void checkPasswordsMatch() {
            if (newPsw.compareTo(newPsw2) != 0) {
                pswWarning.setVisible(true);
            } else {
                pswWarning.setVisible(false);
            }
        }
    }


    public JPanel getModifyPswPanel() {
        return modifyPswPanel;
    }


}
