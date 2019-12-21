package com.company;
import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class RequestListForm extends JPanel {
    private JTable requestsTable;
    private JButton DelButton = new JButton("Delete");

    public RequestListForm() {
        CellEditorListener ChangeNotification = new CellEditorListener() {
            public void editingCanceled(ChangeEvent e) {

                //Action to be performed
            }

            public void editingStopped(ChangeEvent e) {
                //Action to be performed

            }
        };
        requestsTable = new JTable();
        requestsTable.setRowHeight(30);
        for (User user : Main.users) {
            if (user.getLogin().compareTo("admin") == 0) {
                this.add(DelButton);
                DelButton.addActionListener(e -> {

                    System.out.println(requestsTable.getModel().getValueAt(requestsTable.getSelectedRow(), 3));
                    if (requestsTable.getModel().getValueAt(requestsTable.getSelectedRow(), 3) == "Employee") {
                        Main.employeeRequests.remove(Main.FindEmployee(requestsTable.getModel().getValueAt(requestsTable.getSelectedRow(), 4).toString()));
                    } else {
                        Main.employerRequests.remove(Main.FindEmployer(requestsTable.getModel().getValueAt(requestsTable.getSelectedRow(), 4).toString()));
                    }
                    ((DefaultTableModel) requestsTable.getModel()).removeRow(requestsTable.getSelectedRow());
                });

                requestsTable.getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);
            }
        }
        this.add(new JScrollPane(requestsTable));
        requestsTable.setFillsViewportHeight(true);
    }

    public void updateTable(){
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                if (row != -1 && column != -1) {
                    if (model.getValueAt(row, 3).toString().equals("Employee")) {
                        if (column == 0) {
                            Main.employeeRequests.get(row).setJob(model.getValueAt(row, column).toString());
                        }
                        else if (column == 1) {
                            Main.employeeRequests.get(row).setPayment(Integer.parseInt(String.valueOf(model.getValueAt(row, column))));
                        }
                        else if (column == 2) {
                            Main.employeeRequests.get(row).setHoursinWeek(Integer.parseInt(String.valueOf(model.getValueAt(row, column))));
                        }
                    }
                    else {
                        if (column == 0) {
                            Main.employerRequests.get(row - Main.employeeRequests.size()).setJob(model.getValueAt(row, column).toString());
                        }
                        else if (column == 1) {
                            Main.employerRequests.get(row - Main.employeeRequests.size()).setPayment(Integer.parseInt(String.valueOf(model.getValueAt(row, column))));
                        }
                        else if (column == 2) {
                            Main.employerRequests.get(row - Main.employeeRequests.size()).setHoursinWeek(Integer.parseInt(String.valueOf(model.getValueAt(row, column))));
                        }
                    }
                }
            }
        });
        model.addColumn("Job");
        model.addColumn("Payment");
        model.addColumn("Hours in week");
        model.addColumn("Type");
        model.addColumn("Requester");
        for (Request request : Main.employeeRequests) {
            String[] row = new String[] {request.getJob(), String.valueOf(request.getPayment()), String.valueOf(request.getHoursinWeek()), "Employee", request.getRequester().getName()};
            model.addRow(row);
        }
        for (Request request : Main.employerRequests) {
            String[] row = new String[] {request.getJob(), String.valueOf(request.getPayment()), String.valueOf(request.getHoursinWeek()), "Employer", request.getRequester().getName()};
            model.addRow(row);
        }
        requestsTable.setModel(model);
        model.fireTableDataChanged();
    }
}
