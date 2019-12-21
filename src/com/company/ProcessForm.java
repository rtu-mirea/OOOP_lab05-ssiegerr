package com.company;
import javax.swing.*;
import java.awt.*;

public class ProcessForm extends JPanel {
    private JButton processButton = new JButton("Process requests");
    private JTextArea resultArea = new JTextArea("");
    public ProcessForm() {
        this.setLayout(new GridLayout(2,1));
        this.add(resultArea);
        this.add(processButton);

        processButton.addActionListener(e -> {
            resultArea.setText(Main.processRequests());
        });
    }
}