/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

import java.util.ArrayList;

/**
 *
 * @author Ethan
 */
public class Order {
    private String name;
    private String number;
    private boolean paid;
    private boolean ordered;
    private String orderNo;
    private String staff;
    private String comments;
    private ArrayList<Item> orderItems;
    private String date;
    private int orderID;
    
    public Order(String name, String number, boolean paid, boolean ordered, String orderNo, String staff, String comments, ArrayList<Item> items, String date){
        this.name = name;
        this.number = number;
        this.paid = paid;
        this.ordered = ordered;
        this.orderNo = orderNo;
        this.staff = staff;
        this.comments = comments;
        this.orderItems = items;
        this.date = date;
    }
    
    public Order(String name, String number, boolean paid, boolean ordered, String orderNo, String staff, String comments, ArrayList<Item> items, String date, int orderID){
        this.name = name;
        this.number = number;
        this.paid = paid;
        this.ordered = ordered;
        this.orderNo = orderNo;
        this.staff = staff;
        this.comments = comments;
        this.orderItems = items;
        this.date = date;
        this.orderID = orderID;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public boolean isPaid() {
        return paid;
    }

    public boolean isOrdered() {
        return ordered;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getStaff() {
        return staff;
    }

    public String getComments() {
        return comments;
    }

    public ArrayList<Item> getOrderItems() {
        return orderItems;
    }
    
    public String getDate(){
        return date;
    }
    
    public int getOrderID(){
        return orderID;
    }

    @Override
    public String toString() {
        String paidString;
        String orderedString;
        
        if(paid){
            paidString = "Yes";
        }
        else{
            paidString = "No";
        }
        
        if(ordered){
            orderedString = "Yes";
        }
        else{
            orderedString = "No";
        }
        
        return orderID + "   " + date + "   " + name + "   " + number + "   Paid: " + paidString + "   Ordered: " + orderedString + "   " + orderNo;
    }
    
    
}
