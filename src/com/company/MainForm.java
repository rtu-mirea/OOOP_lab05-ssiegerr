package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainForm extends JFrame {
    private User currentUser;
    private JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    public MainForm(User currentUser) {
        super("Lab5");

        this.currentUser = currentUser;

        this.setBounds(300, 300, 500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RequestListForm requestListForm = new RequestListForm();
        jTabbedPane.addTab("Profile", new ProfileForm(currentUser));
        jTabbedPane.addTab("Create request", new RequestForm(currentUser));
        jTabbedPane.addTab("List of requests", requestListForm);
        jTabbedPane.addTab("Process requests", new ProcessForm());

        Container container = this.getContentPane();
        container.add(jTabbedPane);
        this.setVisible(true);

        jTabbedPane.addChangeListener(e -> {
            if (jTabbedPane.getSelectedIndex() == 2) {
                requestListForm.updateTable();
            }
        });

        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                Main.save();
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }

}
