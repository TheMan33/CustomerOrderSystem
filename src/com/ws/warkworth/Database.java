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
            st.setString(1, order.getName().toUpperCase());
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
            while (rs.next()) {
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
    
    public ArrayList<Order> retrieveOrdersByDate(String date){
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM orders WHERE date = ?");
            st.setString(1, date);

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
    
    public ArrayList<Order> retrieveOrdersByName(String name){
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM orders WHERE name = ?");
            st.setString(1, name.toUpperCase());

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

    public ArrayList<Order> retrieveOrdersByBarcode(String barcode) {
        ArrayList<Integer> orderIDs = null;
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

            PreparedStatement st = con.prepareStatement("SELECT orderID FROM items WHERE barcode = ?");
            st.setString(1, barcode);

            ResultSet rs = st.executeQuery();

            orderIDs = new ArrayList<Integer>();

            while (rs.next()) {
//                Order order = new Order(rs.getString("name"), rs.getString("number"), rs.getBoolean("paid"), rs.getBoolean("ordered"), rs.getString("orderNo"), rs.getString("staff"), rs.getString("comments"), new ArrayList<Item>(), rs.getString("date"), rs.getInt("orderID"));
                orderIDs.add(rs.getInt("orderID"));
            }

            for (int i = 0; i < orderIDs.size(); i++) {
                st = con.prepareStatement("SELECT * FROM orders WHERE orderID = ?");
                st.setInt(1, orderIDs.get(i));

                orders = new ArrayList<Order>();
                rs = st.executeQuery();
                while (rs.next()) {
                    Order order = new Order(rs.getString("name"), rs.getString("number"), rs.getBoolean("paid"), rs.getBoolean("ordered"), rs.getString("orderNo"), rs.getString("staff"), rs.getString("comments"), new ArrayList<Item>(), rs.getString("date"), rs.getInt("orderID"));
                    orders.add(order);
                }
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM items WHERE orderID = ?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            ArrayList<Item> items = new ArrayList<Item>();

            while (rs.next()) {
                Item item = new Item(rs.getString(2), rs.getString(3), rs.getInt(4));
                items.add(item);
            }

            PreparedStatement statement = con.prepareStatement("SELECT * FROM orders WHERE orderID = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                retrieved = new Order(resultSet.getString("name"), resultSet.getString("number"), resultSet.getBoolean("paid"), resultSet.getBoolean("ordered"), resultSet.getString("orderNo"), resultSet.getString("staff"), resultSet.getString("comments"), items, resultSet.getString("date"));
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
            PreparedStatement st = con.prepareStatement("DROP TABLE items");
            int rs = st.executeUpdate();
            System.out.println("Table items dropped");
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

    public ArrayList<Order> retrieveAllOrders() {
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

            PreparedStatement st = con.prepareStatement("SELECT * FROM orders ORDER BY orderID");
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

    public void removeOrderByID(int id) {
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
