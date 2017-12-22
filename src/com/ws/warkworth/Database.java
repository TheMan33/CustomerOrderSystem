/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.derby.drda.NetworkServerControl;

/**
 *
 * @author Ethan
 */
public class Database {

    public Database() {
        try {
            NetworkServerControl server = new NetworkServerControl();
            server.start(null);
            System.out.println("DB on");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addOrder(Order order) {
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
                PreparedStatement ps = con.prepareStatement("CREATE TABLE orders "
                        + "(name VARCHAR(30),"
                        + "number VARCHAR(12),"
                        + "paid boolean,"
                        + "ordered boolean,"
                        + "orderNo VARCHAR(30),"
                        + "orderItems VARCHAR (524)"
                        + "staff VARCHAR(30),"
                        + "comments VARCHAR(256),"
                        + "date VARCHAR(10),"
                        + "orderID int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "PRIMARY KEY(orderID))");
                int execute = ps.executeUpdate();
            }

            String items = "";
            for(int i = 0; i < order.getOrderItems().size(); i++){
                items+=order.getOrderItems().get(i).getDescription() +", " + order.getOrderItems().get(i).toString() + ".";
            }
            
            PreparedStatement st = con.prepareStatement("INSERT INTO orders(name, number, paid, ordered, orderNo, orderItems, staff, comments) values(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, order.getName());
            st.setString(2, order.getNumber());
            st.setBoolean(3, order.isPaid());
            st.setBoolean(4, order.isOrdered());
            st.setString(5, order.getOrderNo());
            st.setString(6, items);
            st.setString(7, order.getStaff());
            st.setString(8, order.getComments());
            st.setString(9, order.getDate());

            int result = st.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (InstantiationException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        System.out.println("Order added!");
        return true;
    }
    
    public Order retrieveOrdersByOrderNo(String number){
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
                PreparedStatement ps = con.prepareStatement("CREATE TABLE orders "
                        + "(name VARCHAR(30),"
                        + "number VARCHAR(12),"
                        + "paid boolean,"
                        + "ordered boolean,"
                        + "orderNo VARCHAR(30),"
                        + "staff VARCHAR(30),"
                        + "comments VARCHAR(256),"
                        + "orderID int GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "PRIMARY KEY(orderID))");
                int execute = ps.executeUpdate();
            }

            PreparedStatement st = con.prepareStatement("SELECT * FROM orders WHERE orderNo = ?");
            st.setString(1, number);

            ResultSet result = st.executeQuery();
            
            Order retrieved = new Order(rs.getString("name"), rs.getString("number"), rs.getString("paid"), rs.getString("ordered"), rs.getString("orderNo"), rs.getString("staff"), rs.getString("comments"))
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Order added!");
    }
}
