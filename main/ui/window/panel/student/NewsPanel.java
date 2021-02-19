package ui.window.panel.student;

import model.Student;
import ui.Main;
import ui.window.panel.common.Panels;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

import static java.awt.Color.WHITE;
import static ui.Main.MAIN_WINDOW;

class NewsPanel extends Panels {
    private JSplitPane newsPanel;
    private Student student;
    private JScrollPane leftComponent;
    private JScrollPane rightComponent;
    private JTextArea textArea;
    private JList<String> list;

    NewsPanel(JPanel sourcePanel, Student student) {
        super(sourcePanel);
        this.student = student;
        initializeLeft();
        initializeRight();
        newsPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftComponent, rightComponent);
        newsPanel.setBackground(WHITE);
        newsPanel.setDividerLocation(0.3);
    }

    private void initializeLeft() {
        initList();
        leftComponent = new JScrollPane(list);
        leftComponent.setBackground(WHITE);
        leftComponent.setMinimumSize(new Dimension(300, 600));
    }

    private void initList() {
        String[] aaa = new String[student.getNotes().size() + 1];
        aaa[0] = "<-                                           ";
        int i = aaa.length - 1;
        for (String noteTitle: student.getNotes().keySet()) {
            aaa[i] = noteTitle;
            i--;
        }
        list = new JList<>(aaa);
        list.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        list.addListSelectionListener(new MyListSelectionListener());
        list.setSelectionBackground(Color.pink);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        // Next three lines are copied from https://blog.csdn.net/qq_40299166/article/details/84751258
        DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        list.setCellRenderer(renderer);

    }

    private void initializeRight() {
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        rightComponent = new MyJScrollPane(textArea);
    }

    private void refreshRight(String whatsNew) {
        textArea.setText("    " + whatsNew);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
        textArea.setBackground(WHITE);
        if (rightComponent != null) {
            newsPanel.remove(rightComponent);
        }
        rightComponent = new MyJScrollPane(textArea);
        newsPanel.setRightComponent(rightComponent);
    }

    private class MyJScrollPane extends JScrollPane {

        public MyJScrollPane(JComponent view) {
            super(view, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g);
            g.drawImage(new ImageIcon(Main.path + "data/image/UBC.png").getImage(), 0, 0, null);
        }
    }


    class MyListSelectionListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!list.getValueIsAdjusting()) {
                String noteName = list.getSelectedValue();
                if (noteName.trim().equals("<-")) {
                    newsPanel.setVisible(false);
                    MAIN_WINDOW.getMainWindow().setContentPane(sourcePanel);
                    sourcePanel.setVisible(true);
                } else {
                    refreshRight(student.getNotes().get(noteName));
                }
            }
        }
    }

    JSplitPane getNewsPanel() {
        return newsPanel;
    }


}
