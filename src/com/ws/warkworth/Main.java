/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author Ethan
 */
public class Main {

    public static void main(String[] args) {

        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            System.out.println("DB on");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to start DB. Please close the program and try again.");
        }

//        Database dbv = new Database();
//        dbv.dropTable();
        
        String dbName = "OrderDB";
        String dbUser = "ws";
        String dbPass = "stationery";

        String dbURL = "jdbc:derby://localhost:1527/" + dbName + ";"
                + "create=true;"
                + "user=" + dbUser + ";"
                + "password=" + dbPass;

        String driver = "org.apache.derby.jdbc.ClientDriver";

        try {
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(dbURL);

            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, "ORDERS", null);
            if (!rs.next()) {
                System.out.println("Adding orders table!");
                PreparedStatement ps = con.prepareStatement("CREATE TABLE orders "
                        + "(name VARCHAR(30),"
                        + "number VARCHAR(12),"
                        + "paid boolean,"
                        + "ordered boolean,"
                        + "orderNo VARCHAR(30),"
                        + "staff VARCHAR(30),"
                        + "comments VARCHAR(256),"
                        + "date VARCHAR(10),"
                        + "orderID int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "PRIMARY KEY(orderID))");
                int execute = ps.executeUpdate();
            }
        } catch (Exception e) {

        }
        
        try {
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(dbURL);
            DatabaseMetaData md = con.getMetaData();
            ResultSet rs = md.getTables(null, null, "ITEMS", null);
            if (!rs.next()) {
                System.out.println("Adding items table!");
                PreparedStatement ps = con.prepareStatement("CREATE TABLE items "
                        + "(orderID int,"
                        + "barcode VARCHAR (15),"
                        + "description VARCHAR (512),"
                        + "quantity int,"
                        + "PRIMARY KEY(orderID))");
                int execute = ps.executeUpdate();
            }
        } catch (Exception e) {

        }

        JFrame frame = new JFrame();
        frame.setSize(400, 350);
        frame.add(new IntroGui(frame));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        frame.setLocation(screenWidth / 2 - frame.getWidth() / 2, screenHeight / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
