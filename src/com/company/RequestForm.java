package com.company;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RequestForm extends JPanel {
    private final String[] specs = {"Employer", "Employee"};
    private JComboBox<String> specsComboBox = new JComboBox<>(specs);
    private final JLabel paymentLabel = new JLabel("Payment");
    private final JLabel hoursInWeekLabel = new JLabel("Hours in week");
    private final JLabel jobLabel = new JLabel("Job");
    private final JLabel specsLabel = new JLabel("Employee or\nEmployer");
    private JTextField paymentField = new JTextField();
    private JTextField hoursInWeekField = new JTextField();
    private JTextField jobField = new JTextField();
    private JButton addButton = new JButton("Add");
    private JLabel errorLabel = new JLabel("");

    public RequestForm(User currentUser) {
        this.setLayout(new GridLayout(5, 2));

        this.add(specsLabel);
        this.add(specsComboBox);
        specsComboBox.setSelectedIndex(0);
        this.add(jobLabel);
        this.add(jobField);
        this.add(hoursInWeekLabel);
        this.add(hoursInWeekField);
        this.add(paymentLabel);
        this.add(paymentField);
        this.add(addButton);
        this.add(errorLabel);

        addButton.addActionListener(e -> {
            try {
                int payment = Integer.valueOf(paymentField.getText());
                int hours = Integer.valueOf(hoursInWeekField.getText());
                int type = specsComboBox.getSelectedIndex();
                Request request = new Request(new Client(currentUser.getName(),
                        currentUser.getLogin(), currentUser.getPassword()), jobField.getText(), payment, hours, type);
                if (type == 1) {
                    Main.employeeRequests.add(request);
                } else {
                    Main.employerRequests.add(request);
                }
                errorLabel.setText("Success");
            } catch (Exception ex) {
                errorLabel.setText("Error!\nCheck fields");
            }
        });
    }
}