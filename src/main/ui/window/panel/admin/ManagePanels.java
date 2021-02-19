package ui.window.panel.admin;

import model.Admin;
import model.User;
import model.Users;
import model.UsersType;
import ui.window.panel.common.EditProfilePanel;
import ui.window.panel.common.Panels;
import ui.window.panel.common.RegisterPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.Color.*;
import static model.SuperAdmin.SUPER_ADMIN;
import static model.UsersType.ADMIN;
import static ui.Main.MAIN_WINDOW;

public class ManagePanels extends Panels {
    private JLayeredPane managePanel;
    private JPanel bottomPanel;
    private JScrollPane tablePane;

    private UsersType type;
    private Users users;
    private Admin admin;

    private JTable table;

    private int selectedRow = -1;
    private String[][] data;
    private String[] tableHeaderContent = {"Username", "Password", "Name", "Gender", "Age"};

    private static final int BUTTONS_Y = 550;

    public ManagePanels(JComponent sourcePanel, UsersType type, Admin admin) {
        super(sourcePanel);
        this.type = type;
        this.admin = admin;
        if (type == UsersType.STUDENT) {
            users = students;
        } else if (type == ADMIN) {
            users = admins;
        } else {
            users = instructors;
        }
        bottomPanel = initializeDefaultPanel();
        bottomPanel.setBounds(0,0,800, 600);
        initializeButtons();
        initializeTableContent();
    }

    private void initializeJLayeredPane() {
        managePanel = new JLayeredPane();
        managePanel.add(bottomPanel, new Integer(0));
        managePanel.add(tablePane, new Integer(10));
    }

    private void initializeButtons() {
        initializeBackButton(bottomPanel);

        Dimension smallButton = new Dimension(20, 20);

        JButton add = new JButton("+");
        add.setSize(smallButton);
        add.setLocation(50, BUTTONS_Y);
        add.addActionListener(new AddUserAction());
        bottomPanel.add(add);


        JButton remove = new JButton("-");
        remove.setSize(smallButton);
        remove.setLocation(90, BUTTONS_Y);
        remove.addActionListener(new DeleteUserAction());
        bottomPanel.add(remove);


        JButton modify = new JButton("Modify");
        modify.setBounds(150, BUTTONS_Y, 70, 20);
        modify.addActionListener(new ModifyUserAction());
        bottomPanel.add(modify);
    }

    private void initializeTableContent() {
        data = new String[whatsTheSize()][5];
        int i = 0;
        for (User user : users.usersHashMap.values()) {
            if (!user.equals(SUPER_ADMIN)) {
                String[] col = new String[]{user.getUserName(), user.getPassWord(),
                        user.getName(), user.getGender(), String.valueOf(user.getAge())};
                data[i] = col;
                i++;
            }
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel(data, tableHeaderContent) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(defaultTableModel);
        initTableSettings();
    }

    private int whatsTheSize() {
        return (admin.equals(SUPER_ADMIN) && users.getType() == ADMIN
                ? users.usersHashMap.size() - 1 : users.usersHashMap.size());
    }

    private void initializeScrollPane() {
        tablePane = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        tablePane.setBounds(50, 30, 700, 510);
    }

    private void initTableSettings() {
        table.setRowHeight(18);
        table.setGridColor(BLACK);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new TableListener());
        initHead();
        initCells();
        initializeScrollPane();
        initializeJLayeredPane();
    }

    private void initHead() {
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(table.getWidth(), 35));
        tableHeader.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        tableHeader.setDefaultRenderer(new HeaderRenderer(table));
//        DefaultTableCellHeaderRenderer hr = new DefaultTableCellHeaderRenderer();
//        hr.setHorizontalAlignment(JLabel.CENTER);
//        tableHeader.setDefaultRenderer(hr);
    }

    private void initCells() {
        //Copied from https://blog.csdn.net/MusicEnchanter/article/details/78501004
        //then edited;
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                if (row % 2 == 0) {
                    setBackground(white);
                } else if (row % 2 == 1) {
                    setBackground(LIGHT_GRAY);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < 5; i++) {
            table.getColumn(tableHeaderContent[i]).setCellRenderer(tableCellRenderer);
        }
        table.setDefaultRenderer(Object.class, tableCellRenderer);
    }

    private class AddUserAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            managePanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(new RegisterPanel(
                    managePanel, users.getType(), admin).getRegisterPanel());
        }
    }

    private class DeleteUserAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedRow >= 0) {
                String username = data[table.getSelectedRow()][0];
                users.deleteUser(users.usersHashMap.get(username));
                afterDelete();
            } else {
                makeWarningDialog("Select a user to remove");
            }
        }

        private void afterDelete() {
            managePanel.setVisible(false);
            MAIN_WINDOW.getMainWindow().setContentPane(new ManagePanels(
                    sourcePanel, type, admin).getManagePanel());
        }
    }


    private class ModifyUserAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (selectedRow >= 0) {
                String username = data[table.getSelectedRow()][0];
                managePanel.setVisible(false);
                MAIN_WINDOW.getMainWindow().setContentPane(new EditProfilePanel(
                        managePanel, users.usersHashMap.get(username), admin).getEditProfilePanel());
            } else {
                makeWarningDialog("Select a user to modify");
            }
        }
    }

    private class TableListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            selectedRow = table.getSelectedRow();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    // copied from https://stackoverflow.com/questions/7493369/jtable-right-align-header
    private static class HeaderRenderer implements TableCellRenderer {

        DefaultTableCellRenderer renderer;

        HeaderRenderer(JTable table) {
            renderer = (DefaultTableCellRenderer)
                    table.getTableHeader().getDefaultRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int col) {
            return renderer.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, col);
        }
    }

//    @Override
//    protected void refresh() {
//        initializeTableContent();
//        tablePane.repaint();
//    }


    public JLayeredPane getManagePanel() {
        return managePanel;
    }
}
