/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ethan
 */
public class Database {

//    public Database() {
//        
//    }
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

            PreparedStatement st = con.prepareStatement("INSERT INTO orders(name, number, paid, ordered, orderNo, staff, comments, date) values(?, ?, ?, ?, ?, ?, ?, ?)");
            st.setString(1, order.getName());
            st.setString(2, order.getNumber());
            st.setBoolean(3, order.isPaid());
            st.setBoolean(4, order.isOrdered());
            st.setString(5, order.getOrderNo());
            st.setString(6, order.getStaff());
            st.setString(7, order.getComments());
            st.setString(8, order.getDate());

            int result = st.executeUpdate();

            st = con.prepareStatement("SELECT MAX(orderID) FROM orders");
            ResultSet rs = st.executeQuery();
            int orderID = 0;
            while(rs.next()){
                orderID = rs.getInt(1);
            }

            for (int i = 0; i < order.getOrderItems().size(); i++) {
                st = con.prepareStatement("INSERT INTO items(orderID, barcode, description, quantity) values(?, ?, ?, ?)");
                st.setInt(1, orderID);
                st.setString(2, order.getOrderItems().get(i).getBarcode());
                st.setString(3, order.getOrderItems().get(i).getDescription());
                st.setInt(4, order.getOrderItems().get(i).getQuantity());
                result = st.executeUpdate();
            }

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

    public Order retrieveOrdersByOrderID(int id) {
        Order retrieved = null;
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM items WHERE orderNo = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            ArrayList<Item> items = new ArrayList<Item>();

            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getString(id), rs.getInt(id));
                items.add(item);
            }
            
            st = con.prepareStatement("SELECT * FROM orders WHERE orderN0 = ?");
            st.setInt(1, id);
            rs = st.executeQuery();

            retrieved = new Order(rs.getString("name"), rs.getString("number"), rs.getBoolean("paid"), rs.getBoolean("ordered"), rs.getString("orderNo"), rs.getString("staff"), rs.getString("comments"), items, rs.getString("date"));
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
        return retrieved;
    }

    public void dropTable() {
        try {
            System.out.println("drop table called");
            String dbName = "OrderDB";
            String dbUser = "ws";
            String dbPass = "stationery";
            
            String dbURL = "jdbc:derby://localhost:1527/" + dbName + ";"
                    + "create=true;"
                    + "user=" + dbUser + ";"
                    + "password=" + dbPass;
            
            String driver = "org.apache.derby.jdbc.ClientDriver";
            
            
            Class.forName(driver).newInstance();
            Connection con = DriverManager.getConnection(dbURL);
            PreparedStatement st = con.prepareStatement("DROP TABLE orders");
            int rs = st.executeUpdate();
            System.out.println("Table orders dropped");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Order> retrieveAllOrders(){
        ArrayList<Order> orders = null;
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM orders");
            ResultSet rs = st.executeQuery();

            orders = new ArrayList<Order>();

            while (rs.next()) {
                Order order = new Order(rs.getString("name"), rs.getString("number"), rs.getBoolean("paid"), rs.getBoolean("ordered"), rs.getString("orderNo"), rs.getString("staff"), rs.getString("comments"), new ArrayList<Item>(), rs.getString("date"), rs.getInt("orderID"));
                orders.add(order);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orders;
    }
    
    public void removeOrderByID(int id){
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

            PreparedStatement st = con.prepareStatement("DELETE FROM orders WHERE orderID = ?");
            st.setInt(1, id);
            int rs = st.executeUpdate();
            
            st = con.prepareStatement("DELETE FROM items WHERE orderID = ?");
            st.setInt(1, id);
            rs = st.executeUpdate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
