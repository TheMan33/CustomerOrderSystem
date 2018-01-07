/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

/**
 *
 * @author Ethan
 */
public class ViewAllOrdersGui extends javax.swing.JPanel {

    private Database db;
    private ArrayList<Order> allOrders;
    private DefaultListModel<String> listModel;
    private JFrame frame;

    /**
     * Creates new form ViewOrderGui
     */
    public ViewAllOrdersGui(JFrame frame) {
        initComponents();
        this.frame = frame;
        refineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refineButtonClicked();
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetButtonClicked();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderClicked();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeOrderClicked();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                backButtonClicked();
            }
        });

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeButtonClicked();
            }
        });

        db = new Database();
        allOrders = db.retrieveAllOrders();
        listModel = new DefaultListModel<String>();
        jList1.setModel(listModel);
        for (int i = 0; i < allOrders.size(); i++) {
            listModel.addElement(allOrders.get(i).toString());
        }
    }

    public void refineButtonClicked() {
        if (!nameText.getText().equals("")) {
            ArrayList<Order> matchingOrders = db.retrieveOrdersByName(nameText.getText());
            listModel = new DefaultListModel<String>();
            jList1.setModel(listModel);
            if (matchingOrders.size() > 0) {
                for (int i = 0; i < matchingOrders.size(); i++) {
                    listModel.addElement(matchingOrders.get(i).toString());
                }
            }
        } else if (!barcodeText.getText().equals("")) {
            ArrayList<Order> matchingOrders = db.retrieveOrdersByBarcode(barcodeText.getText());
            listModel = new DefaultListModel<String>();
            jList1.setModel(listModel);
            if (matchingOrders.size() > 0) {
                for (int i = 0; i < matchingOrders.size(); i++) {
                    listModel.addElement(matchingOrders.get(i).toString());
                }
            }
        } else if (!dateText.getText().equals("")) {
            ArrayList<Order> matchingOrders = db.retrieveOrdersByDate(dateText.getText());
            listModel = new DefaultListModel<String>();
            jList1.setModel(listModel);
            if (matchingOrders.size() > 0) {
                for (int i = 0; i < matchingOrders.size(); i++) {
                    listModel.addElement(matchingOrders.get(i).toString());
                }
            }
        }
    }

    public void resetButtonClicked() {
        nameText.setText("");
        barcodeText.setText("");
        dateText.setText("");
        listModel = new DefaultListModel<String>();
        jList1.setModel(listModel);
        for (int i = 0; i < allOrders.size(); i++) {
            listModel.addElement(allOrders.get(i).toString());
        }
    }

    public void viewOrderClicked() {
        String toRemove = (String) jList1.getSelectedValue();
        String[] array = toRemove.split(" ");
        Order toView = db.retrieveOrdersByOrderID(Integer.parseInt(array[0]));
        ViewOrderGui viewOrder = new ViewOrderGui(toView);
        viewOrder.setVisible(true);
        viewOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void removeOrderClicked() {
        int warning = JOptionPane.showOptionDialog(this,
                "The selected order(s) will be deleted. Continue?",
                "Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                null,
                null);
        if (warning == JOptionPane.OK_OPTION) {
            ArrayList<String> toRemove = (ArrayList<String>) jList1.getSelectedValuesList();
            for (int i = 0; i < toRemove.size(); i++) {
                String[] array = toRemove.get(i).split(" ");
                db.removeOrderByID(Integer.parseInt(array[0]));
            }

            listModel.removeAllElements();
            allOrders = db.retrieveAllOrders();
            for (int i = 0; i < allOrders.size(); i++) {
                listModel.addElement(allOrders.get(i).toString());
            }
        }
    }

    public void backButtonClicked() {
        frame.remove(this);
        frame.add(new IntroGui(frame));
        frame.revalidate();
        frame.repaint();
        frame.pack();
    }

    public void closeButtonClicked() {
        System.exit(0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nameText = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        barcodeText = new javax.swing.JTextField();
        refineButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        viewButton = new javax.swing.JButton();
        removeButton = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        dateText = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        jLabel1.setText("Customer Name:");

        jLabel2.setText("Barcode:");

        refineButton.setText("Refine");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        viewButton.setText("View");

        removeButton.setText("Remove");

        jLabel4.setText("Date (dd-mm-yyyy):");

        dateText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateTextActionPerformed(evt);
            }
        });

        backButton.setText("Back");

        resetButton.setText("Reset");

        closeButton.setText("Close");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(backButton)
                        .addGap(47, 47, 47)
                        .addComponent(viewButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(removeButton)
                        .addGap(47, 47, 47)
                        .addComponent(closeButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(10, 10, 10)
                                .addComponent(barcodeText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(refineButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(resetButton)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(barcodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refineButton)
                    .addComponent(jLabel4)
                    .addComponent(dateText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(viewButton)
                    .addComponent(removeButton)
                    .addComponent(backButton)
                    .addComponent(closeButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void dateTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateTextActionPerformed

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        JList list = (JList) evt.getSource();
        if (evt.getClickCount() == 2) {
            String toRemove = (String) list.getSelectedValue();
            String[] array = toRemove.split(" ");
            Order toView = db.retrieveOrdersByOrderID(Integer.parseInt(array[0]));
            ViewOrderGui viewOrder = new ViewOrderGui(toView);
            viewOrder.setVisible(true);
            viewOrder.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }//GEN-LAST:event_jList1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JTextField barcodeText;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField dateText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField nameText;
    private javax.swing.JButton refineButton;
    private javax.swing.JButton removeButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JButton viewButton;
    // End of variables declaration//GEN-END:variables
}
