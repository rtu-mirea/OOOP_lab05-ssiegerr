package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LogInForm extends JFrame {
    private final JLabel LOGIN_LABEL = new JLabel("LOGIN");
    private final JLabel PASSWORD_LABEL = new JLabel("PASSWORD");
    public JTextField loginField = new JTextField("", 1);
    private JTextField passwordField = new JTextField("", 1);
    private JButton registerButton = new JButton("REGISTER");
    private JButton loginButton = new JButton("LOGIN");
    private JLabel errorLabel = new JLabel("");

    private User currentUser;
    private ArrayList<User> users;

    public LogInForm() {
        super("LOGIN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(300, 300, 300, 150);


        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4, 2));
        container.add(LOGIN_LABEL);
        container.add(loginField);
        container.add(PASSWORD_LABEL);
        container.add(passwordField);
        container.add(registerButton);
        container.add(loginButton);
        container.add(errorLabel);

        setVisible(true);

        registerButton.addActionListener(e -> {
            boolean exists = false;
            for (User user : Main.users) {
                if (user.getLogin().equals(loginField.getText())) {
                    errorLabel.setText("This login is already taken");
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                currentUser = new User("Anon", loginField.getText(), passwordField.getText());
                Main.addUser(currentUser);
                errorLabel.setText("Now you can log in");
            }
        });

        loginButton.addActionListener(e -> {
            currentUser = Main.findUser(loginField.getText(), passwordField.getText());
            if (currentUser == null) {
                errorLabel.setText("Incorrect login or password");
            }
            else {
                this.setVisible(false);
                new MainForm(currentUser);
            }
        });
    }


}
